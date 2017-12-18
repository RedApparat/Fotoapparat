package io.fotoapparat.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.SeekBar
import io.fotoapparat.Fotoapparat
import io.fotoapparat.characteristic.LensPosition
import io.fotoapparat.configuration.CameraConfiguration
import io.fotoapparat.configuration.UpdateConfiguration
import io.fotoapparat.log.logcat
import io.fotoapparat.parameter.Resolution
import io.fotoapparat.result.transformer.scaled
import io.fotoapparat.selector.*
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File


class MainActivity : AppCompatActivity() {

    private val permissionsDelegate = PermissionsDelegate(this)

    private var permissionsGranted: Boolean = false
    private var activeCamera: Camera = Camera.Back

    private lateinit var fotoapparat: Fotoapparat


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        permissionsGranted = permissionsDelegate.hasCameraPermission()

        if (permissionsGranted) {
            cameraView.visibility = View.VISIBLE
        } else {
            permissionsDelegate.requestCameraPermission()
        }

        fotoapparat = Fotoapparat(
                context = this,
                view = cameraView,
                logger = logcat(),
                lensPosition = activeCamera.lensPosition,
                cameraConfiguration = activeCamera.configuration,
                cameraErrorCallback = { it -> Log.e(LOGGING_TAG, "Camera error: ", it) }
        )

        cameraView onClick takePicture()
        zoomSeekBar onProgressChanged updateZoom()
        switchCamera onClick changeCamera()
        torchSwitch onCheckedChanged toggleFlash()
    }


    private fun updateZoom(): (SeekBar, Int) -> Unit = { seekBar: SeekBar, progress: Int ->
        fotoapparat.setZoom(progress / seekBar.max.toFloat())
    }

    private fun takePicture(): () -> Unit = {
        val photoResult = fotoapparat
                .autoFocus()
                .takePicture()

        photoResult
                .saveToFile(File(
                        getExternalFilesDir("photos"),
                        "photo.jpg"
                ))

        photoResult
                .toBitmap(scaled(scaleFactor = 0.25f))
                .whenAvailable { (bitmap, rotationDegrees) ->
                    val imageView = findViewById<ImageView>(R.id.result)

                    imageView.setImageBitmap(bitmap)
                    imageView.rotation = (-rotationDegrees).toFloat()
                }
    }

    private fun changeCamera(): () -> Unit = {
        activeCamera = when (activeCamera) {
            Camera.Front -> Camera.Back
            Camera.Back -> Camera.Front
        }

        fotoapparat.switchTo(
                lensPosition = activeCamera.lensPosition,
                cameraConfiguration = activeCamera.configuration
        )

        zoomSeekBar.progress = 0
        torchSwitch.isChecked = false

        Log.i(LOGGING_TAG, "New camera: ${activeCamera::javaClass.name}")
    }

    private fun toggleFlash(): (CompoundButton, Boolean) -> Unit = { _, isChecked ->
        fotoapparat.updateConfiguration(
                UpdateConfiguration(
                        flashMode = if (isChecked) {
                            firstAvailable(
                                    torch(),
                                    off()
                            )
                        } else {
                            off()
                        }
                )
        )

        Log.i(LOGGING_TAG, "Flash is now ${if (isChecked) "on" else "off"}")
    }

    override fun onStart() {
        super.onStart()
        if (permissionsGranted) {
            fotoapparat.start()
        }
    }

    override fun onStop() {
        super.onStop()
        if (permissionsGranted) {
            fotoapparat.stop()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (permissionsDelegate.resultGranted(requestCode, permissions, grantResults)) {
            permissionsGranted = true
            fotoapparat.start()
            cameraView.visibility = View.VISIBLE
        }
    }
}

private const val LOGGING_TAG = "Fotoapparat Example"

private sealed class Camera(
        val lensPosition: Collection<LensPosition>.() -> LensPosition?,
        val configuration: CameraConfiguration
) {

    object Back : Camera(
            lensPosition = back(),
            configuration = CameraConfiguration(
                    previewResolution = single(Resolution(
                            1920,
                            1080
                    )),
                    previewFpsRange = highestFps(),
                    flashMode = off(),
                    focusMode = continuousFocusPicture()
            )
    )

    object Front : Camera(
            lensPosition = front(),
            configuration = CameraConfiguration(
                    previewFpsRange = highestFps(),
                    flashMode = off(),
                    focusMode = fixed()
            )
    )
}

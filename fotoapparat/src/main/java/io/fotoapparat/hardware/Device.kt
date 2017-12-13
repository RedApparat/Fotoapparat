package io.fotoapparat.hardware

import android.hardware.Camera
import io.fotoapparat.characteristic.LensPosition
import io.fotoapparat.characteristic.getCharacteristics
import io.fotoapparat.configuration.CameraConfiguration
import io.fotoapparat.configuration.Configuration
import io.fotoapparat.configuration.default
import io.fotoapparat.hardware.display.Display
import io.fotoapparat.log.Logger
import io.fotoapparat.parameter.ScaleType
import io.fotoapparat.parameter.camera.CameraParameters
import io.fotoapparat.parameter.camera.provide.getCameraParameters
import io.fotoapparat.preview.Frame
import io.fotoapparat.view.CameraRenderer


/**
 * Phone.
 */
internal open class Device(
        private val logger: Logger,
        private val display: Display,
        open val scaleType: ScaleType,
        open val cameraRenderer: CameraRenderer,
        numberOfCameras: Int = Camera.getNumberOfCameras(),
        initialConfiguration: CameraConfiguration,
        initialLensPositionSelector: Collection<LensPosition>.() -> LensPosition?
) {

    private val cameras = (0 until numberOfCameras).map { cameraId ->
        CameraDevice(
                logger = logger,
                characteristics = getCharacteristics(cameraId)
        )
    }

    private var savedConfiguration: CameraConfiguration = default()
    private var lensPositionSelector: Collection<LensPosition>.() -> LensPosition? = initialLensPositionSelector
    private var selectedCameraDevice: CameraDevice? = null

    init {
        updateLensPositionSelector(initialLensPositionSelector)
        savedConfiguration = initialConfiguration
    }

    /**
     * Selects a camera. Will do nothing if camera cannot be selected.
     */
    open fun selectCamera() {
        logger.recordMethod()

        selectedCameraDevice = selectCamera(
                availableCameras = cameras,
                lensPositionSelector = lensPositionSelector
        )
    }

    /**
     * Returns the selected camera. Returns `null` if no camera is selected.
     */
    open fun getSelectedCamera(): CameraDevice? {
        return selectedCameraDevice
    }

    open fun clearSelectedCamera() {
        selectedCameraDevice = null
    }

    /**
     * @return rotation of the screen in degrees.
     */
    open fun getScreenRotation(): Int {
        return display.getRotation()
    }

    /**
     * Updates the desired from the user camera lens position.
     */
    open fun updateLensPositionSelector(newLensPosition: Collection<LensPosition>.() -> LensPosition?) {
        logger.recordMethod()

        lensPositionSelector = newLensPosition
    }

    /**
     * Updates the desired from the user selectors.
     */
    open fun updateConfiguration(newConfiguration: Configuration) {
        logger.recordMethod()

        savedConfiguration = updateConfiguration(
                savedConfiguration = savedConfiguration,
                newConfiguration = newConfiguration
        )
    }

    /**
     * @return The desired from the user selectors.
     */
    open fun getConfiguration(): CameraConfiguration {
        return savedConfiguration
    }

    open fun getCameraParameters(cameraDevice: CameraDevice): CameraParameters {
        return getCameraParameters(
                cameraConfiguration = savedConfiguration,
                capabilities = cameraDevice.getCapabilities()
        )
    }

    open fun getFrameProcessor(): (Frame) -> Unit {
        return savedConfiguration.frameProcessor
    }

    /**
     * @return The desired from the user camera lens position.
     */
    fun getLensPositionSelector(): Collection<LensPosition>.() -> LensPosition? {
        return lensPositionSelector
    }

}

/**
 * Updates the device's configuration.
 */
internal fun updateConfiguration(
        savedConfiguration: CameraConfiguration,
        newConfiguration: Configuration
) = CameraConfiguration(
        flashMode = newConfiguration.flashMode ?: savedConfiguration.flashMode,
        focusMode = newConfiguration.focusMode ?: savedConfiguration.focusMode,
        frameProcessor = newConfiguration.frameProcessor ?: savedConfiguration.frameProcessor,
        previewFpsRange = newConfiguration.previewFpsRange ?: savedConfiguration.previewFpsRange,
        sensorSensitivity = newConfiguration.sensorSensitivity ?: savedConfiguration.sensorSensitivity,
        pictureResolution = newConfiguration.pictureResolution ?: savedConfiguration.pictureResolution,
        previewResolution = newConfiguration.previewResolution ?: savedConfiguration.previewResolution
)

/**
 * Selects a camera from the set of available ones.
 */
internal fun selectCamera(
        availableCameras: List<CameraDevice>,
        lensPositionSelector: Collection<LensPosition>.() -> LensPosition?
): CameraDevice? {

    val lensPositions = availableCameras.map { it.characteristics.lensPosition }.toSet()
    val desiredPosition = lensPositionSelector.invoke(lensPositions)

    return availableCameras.find { it.characteristics.lensPosition == desiredPosition }
}

/**
 * @return The currently selected camera safely.
 */
internal fun Device.getSelectedCameraSafely(): CameraDevice {
    return getSelectedCamera() ?: throw IllegalStateException("Camera has not started!")
}
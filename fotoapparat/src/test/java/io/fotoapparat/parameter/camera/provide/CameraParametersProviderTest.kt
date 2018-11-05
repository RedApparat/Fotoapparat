package io.fotoapparat.parameter.camera.provide

import io.fotoapparat.capability.Capabilities
import io.fotoapparat.configuration.CameraConfiguration
import io.fotoapparat.exception.camera.InvalidConfigurationException
import io.fotoapparat.exception.camera.UnsupportedConfigurationException
import io.fotoapparat.parameter.*
import io.fotoapparat.parameter.camera.CameraParameters
import io.fotoapparat.selector.nothing
import io.fotoapparat.selector.single
import org.junit.Test
import kotlin.test.assertEquals

internal class CameraParametersProviderTest {

    val resolution = Resolution(10, 10)
    val fpsRange = FpsRange(20000, 20000)
    val jpegQuality = 80
    val exposureCompensation = 5
    val iso = 100

    val capabilities = Capabilities(
            zoom = Zoom.FixedZoom,
            flashModes = setOf(Flash.AutoRedEye),
            focusModes = setOf(FocusMode.Fixed),
            canSmoothZoom = false,
            maxFocusAreas = 1,
            maxMeteringAreas = 1,
            previewFpsRanges = setOf(fpsRange),
            antiBandingModes = setOf(AntiBandingMode.None),
            jpegQualityRange = IntRange(0, 100),
            exposureCompensationRange = IntRange(-20, 20),
            pictureResolutions = setOf(resolution),
            previewResolutions = setOf(resolution),
            sensorSensitivities = setOf(iso)
    )

    val definedConfiguration = CameraConfiguration(
            flashMode = single(Flash.AutoRedEye),
            focusMode = single(FocusMode.Fixed),
            jpegQuality = single(jpegQuality),
            exposureCompensation = single(exposureCompensation),
            frameProcessor = {},
            antiBandingMode = single(AntiBandingMode.None),
            previewFpsRange = single(fpsRange),
            sensorSensitivity = single(iso),
            pictureResolution = single(resolution),
            previewResolution = single(resolution)
    )

    @Test
    fun `Get all valid camera parameters`() {
        // Given

        // When
        val cameraParameters = getCameraParameters(
                capabilities = capabilities,
                cameraConfiguration = definedConfiguration
        )

        // Then
        assertEquals(
                expected = CameraParameters(
                        flashMode = Flash.AutoRedEye,
                        focusMode = FocusMode.Fixed,
                        jpegQuality = jpegQuality,
                        exposureCompensation = exposureCompensation,
                        previewFpsRange = fpsRange,
                        antiBandingMode = AntiBandingMode.None,
                        pictureResolution = resolution,
                        previewResolution = resolution,
                        sensorSensitivity = iso
                ),
                actual = cameraParameters
        )
    }

    @Test(expected = UnsupportedConfigurationException::class)
    fun `Select no flash mode`() {
        // Given
        val definedConfiguration = definedConfiguration.copy(
                flashMode = nothing()
        )

        // When
        getCameraParameters(
                capabilities = capabilities,
                cameraConfiguration = definedConfiguration
        )

        // Then
        // throw exception
    }

    @Test(expected = UnsupportedConfigurationException::class)
    fun `Select no focus mode`() {
        // Given
        val definedConfiguration = definedConfiguration.copy(
                focusMode = nothing()
        )

        // When
        getCameraParameters(
                capabilities = capabilities,
                cameraConfiguration = definedConfiguration
        )

        // Then
        // throw exception
    }

    @Test(expected = UnsupportedConfigurationException::class)
    fun `Select no preview fps range`() {
        // Given
        val definedConfiguration = definedConfiguration.copy(
                previewFpsRange = nothing()
        )

        // When
        getCameraParameters(
                capabilities = capabilities,
                cameraConfiguration = definedConfiguration
        )

        // Then
        // throw exception
    }

    @Test(expected = UnsupportedConfigurationException::class)
    fun `Select no picture resolution`() {
        // Given
        val definedConfiguration = definedConfiguration.copy(
                pictureResolution = nothing()
        )

        // When
        getCameraParameters(
                capabilities = capabilities,
                cameraConfiguration = definedConfiguration
        )
    }

    @Test(expected = UnsupportedConfigurationException::class)
    fun `Select no preview resolution`() {
        // Given
        val definedConfiguration = definedConfiguration.copy(
                previewResolution = nothing()
        )

        // When
        getCameraParameters(
                capabilities = capabilities,
                cameraConfiguration = definedConfiguration
        )

        // Then
        // throw exception
    }

    @Test(expected = UnsupportedConfigurationException::class)
    fun `Select no jpeg quality`() {
        // Given
        val definedConfiguration = definedConfiguration.copy(
                jpegQuality = nothing()
        )

        // When
        getCameraParameters(
                capabilities = capabilities,
                cameraConfiguration = definedConfiguration
        )

        // Then
        // throw exception
    }

    @Test(expected = UnsupportedConfigurationException::class)
    fun `Select no exposure compensation`() {
        // Given
        val definedConfiguration = definedConfiguration.copy(
                exposureCompensation = nothing()
        )

        // When
        getCameraParameters(
                capabilities = capabilities,
                cameraConfiguration = definedConfiguration
        )

        // Then
        // throw exception
    }

    @Test(expected = UnsupportedConfigurationException::class)
    fun `Select no antibanding mode`() {
        // Given
        val definedConfiguration = definedConfiguration.copy(
                antiBandingMode = nothing()
        )

        // When
        getCameraParameters(
                capabilities = capabilities,
                cameraConfiguration = definedConfiguration
        )

        // Then
        // throw exception
    }

    @Test
    fun `Selecting a sensor sensitivity is optional`() {
        // Given
        val definedConfiguration = definedConfiguration.copy(
                sensorSensitivity = nothing()
        )

        // When
        val cameraParameters = getCameraParameters(
                capabilities = capabilities,
                cameraConfiguration = definedConfiguration
        )

        // Then
        assertEquals(
                expected = null,
                actual = cameraParameters.sensorSensitivity
        )
    }

    @Test(expected = InvalidConfigurationException::class)
    fun `Select flash mode which is not supported`() {
        // Given
        val capabilities = capabilities.copy(
                flashModes = setOf(Flash.Off)
        )
        val definedConfiguration = definedConfiguration.copy(
                flashMode = { Flash.AutoRedEye }
        )

        // When
        getCameraParameters(
                capabilities = capabilities,
                cameraConfiguration = definedConfiguration
        )

        // Then
        // throw exception
    }

    @Test(expected = InvalidConfigurationException::class)
    fun `Select focus mode which is not supported`() {
        // Given
        val capabilities = capabilities.copy(
                focusModes = setOf(FocusMode.Fixed)
        )
        val definedConfiguration = definedConfiguration.copy(
                focusMode = { FocusMode.ContinuousFocusPicture }
        )

        // When
        getCameraParameters(
                capabilities = capabilities,
                cameraConfiguration = definedConfiguration
        )

        // Then
        // throw exception
    }

    @Test(expected = InvalidConfigurationException::class)
    fun `Select preview fps range which is not supported`() {
        // Given
        val capabilities = capabilities.copy(
                previewFpsRanges = setOf(FpsRange(10000, 10000))
        )
        val definedConfiguration = definedConfiguration.copy(
                previewFpsRange = { FpsRange(20000, 20000) }
        )

        // When
        getCameraParameters(
                capabilities = capabilities,
                cameraConfiguration = definedConfiguration
        )

        // Then
        // throw exception
    }

    @Test(expected = InvalidConfigurationException::class)
    fun `Select picture resolution which is not supported`() {
        // Given
        val capabilities = capabilities.copy(
                pictureResolutions = setOf(Resolution(10, 10))
        )
        val definedConfiguration = definedConfiguration.copy(
                pictureResolution = { Resolution(20, 20) }
        )

        // When
        getCameraParameters(
                capabilities = capabilities,
                cameraConfiguration = definedConfiguration
        )
    }

    @Test(expected = InvalidConfigurationException::class)
    fun `Select preview resolution which is not supported`() {
        // Given
        val capabilities = capabilities.copy(
                previewResolutions = setOf(Resolution(10, 10))
        )
        val definedConfiguration = definedConfiguration.copy(
                previewResolution = { Resolution(20, 20) }
        )

        // When
        getCameraParameters(
                capabilities = capabilities,
                cameraConfiguration = definedConfiguration
        )

        // Then
        // throw exception
    }

    @Test(expected = InvalidConfigurationException::class)
    fun `Select jpeg quality which is not supported`() {
        // Given
        val capabilities = capabilities.copy(
                jpegQualityRange = IntRange(0, 100)
        )
        val definedConfiguration = definedConfiguration.copy(
                jpegQuality = { 200 }
        )

        // When
        getCameraParameters(
                capabilities = capabilities,
                cameraConfiguration = definedConfiguration
        )

        // Then
        // throw exception
    }

    @Test(expected = InvalidConfigurationException::class)
    fun `Select exposure compensation which is not supported`() {
        // Given
        val capabilities = capabilities.copy(
                exposureCompensationRange = IntRange(-20, 20)
        )
        val definedConfiguration = definedConfiguration.copy(
                exposureCompensation = { 100 }
        )

        // When
        getCameraParameters(
                capabilities = capabilities,
                cameraConfiguration = definedConfiguration
        )

        // Then
        // throw exception
    }

    @Test(expected = InvalidConfigurationException::class)
    fun `Select anti banding mode which is not supported`() {
        // Given
        val capabilities = capabilities.copy(
                antiBandingModes = setOf(AntiBandingMode.None)
        )
        val definedConfiguration = definedConfiguration.copy(
                antiBandingMode = { AntiBandingMode.HZ60 }
        )

        // When
        getCameraParameters(
                capabilities = capabilities,
                cameraConfiguration = definedConfiguration
        )

        // Then
        // throw exception
    }
}

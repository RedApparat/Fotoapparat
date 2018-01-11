package io.fotoapparat.test

import io.fotoapparat.capability.Capabilities
import io.fotoapparat.configuration.CameraConfiguration
import io.fotoapparat.parameter.*
import io.fotoapparat.parameter.camera.CameraParameters
import io.fotoapparat.preview.Frame
import io.fotoapparat.selector.single
import io.fotoapparat.util.FrameProcessor

/**
 * Test object for [Resolution].
 */
val testResolution = Resolution(10, 10)

/**
 * Test object for [FpsRange].
 */
val testFpsRange = FpsRange(20000, 20000)

/**
 * Test object for camera sensitivity values.
 */
val testIso = 100

/**
 * Test object for camera Jpeq quality.
 */
val jpegQuality = 80

/**
 * Test object for [CameraConfiguration].
 */
internal val testConfiguration = CameraConfiguration(
        flashMode = single(Flash.AutoRedEye),
        focusMode = single(FocusMode.Fixed),
        frameProcessor = {},
        previewFpsRange = single(testFpsRange),
        sensorSensitivity = single(testIso),
        pictureResolution = single(testResolution),
        previewResolution = single(testResolution)
)

/**
 * Test object for [Capabilities].
 */
val testCapabilities = Capabilities(
        canZoom = false,
        flashModes = setOf(Flash.AutoRedEye),
        focusModes = setOf(FocusMode.Fixed),
        canSmoothZoom = false,
        maxFocusAreas = 100,
        maxMeteringAreas = 100,
        jpegQualityRange = IntRange(0, 100),
        antiBandingModes = setOf(AntiBandingMode.None),
        previewFpsRanges = setOf(testFpsRange),
        pictureResolutions = setOf(testResolution),
        previewResolutions = setOf(testResolution),
        sensorSensitivities = setOf(testIso)
)

/**
 * Test object for [CameraParameters].
 */
val testCameraParameters = CameraParameters(
        flashMode = Flash.AutoRedEye,
        focusMode = FocusMode.Fixed,
        jpegQuality = jpegQuality,
        antiBandingMode = AntiBandingMode.None,
        previewFpsRange = testFpsRange,
        sensorSensitivity = testIso,
        pictureResolution = testResolution,
        previewResolution = testResolution
)

/**
 * Test frame processor.
 */
val testFrameProcessor: FrameProcessor = { }
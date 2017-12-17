# Fotoapparat

![Build status](https://travis-ci.org/Fotoapparat/Fotoapparat.svg?branch=master)


![ ](sample/src/main/res/mipmap-xxxhdpi/ic_launcher.png)

Camera API in Android is hard. Having 2 different API for new and old Camera does not make things any easier. But fret not, that is your lucky day! After several years of working with Camera we came up with Fotoapparat.

What it provides:
- Camera API which does not allow you to shoot yourself in the foot.
- Simple yet powerful parameters customization.
- Standalone custom `CameraView` which can be integrated into any `Activity`.
- Fixes and workarounds for device-specific problems.
- Last, but not least, non 0% test coverage. 


Taking picture becomes as simple as:

```kotlin
val fotoapparat = Fotoapparat(
    context = this,
    view = cameraView
)
 
fotoapparat.start()
    
fotoapparat
    .takePicture()
    .saveToFile(someFile)
```

## How it works

### Step One

Add `CameraView` to your layout

```xml
<io.fotoapparat.view.CameraView
    android:id="@+id/camera_view"
    android:layout_width="match_parent"
    android:layout_height="match_parent"/>
```

### Step Two

Configure `Fotoapparat` instance.
Are you using Java only? See our (wiki for the java-friendly configuration)[https://github.com/Fotoapparat/Fotoapparat/wiki/Configuration-Java].

```kotlin
val configuration = CameraConfiguration(    
    pictureResolution = highestResolution(), // (optional) we want to have the highest possible photo resolution
    previewResolution = highestResolution(), // (optional) we want to have the highest possible preview resolution
    previewFpsRange = highestFps(),          // (optional) we want to have the best frame rate
    focusMode = firstAvailable(              // (optional) use the first focus mode which is supported by device
            continuousFocusPicture(),
            autoFocus(),                       // if continuous focus is not available on device, auto focus will be used
            fixed()                            // if even auto focus is not available - fixed focus mode will be used
    ),
    flashMode = firstAvailable(              // (optional) similar to how it is done for focus mode, this time for flash
            autoRedEye(),
            autoFlash(),
            torch(),
            off()
    ),
    sensorSensitivity = lowestSensorSensitivity(), // (optional) we want to have the lowest sensor sensitivity (ISO)
    frameProcessor = { frame -> }            // (optional) receives each frame from preview stream
)
 
Fotoapparat(
            context = this,
            view = cameraView,                   // view which will draw the camera preview
            scaleType = ScaleType.CenterCrop,    // (optional) we want the preview to fill the view
            lensPosition = back(),               // (optional) we want back camera
            cameraConfiguration = configuration, // (optional) define an advanced configuration
            logger = loggers(                    // (optional) we want to log camera events in 2 places at once
                     logcat(),                   // ... in logcat
                     fileLogger(this)            // ... and to file
            ),
            cameraErrorCallback = { error -> }   // (optional) log fatal errors
    )
```

### Step Three

Call `start()` and `stop()`. No rocket science here.

```kotlin
override fun onStart() {
    super.onStart()
    fotoapparat.start()
}
 
override fun onStop() {
    super.onStop()
    fotoapparat.stop()
}
```

### Take picture

Finally we are ready to take picture. You have various options.

```kotlin
val photoResult = fotoapparat.takePicture()
 
// Asynchronously saves photo to file
photoResult.saveToFile(someFile)
 
// Asynchronously converts photo to bitmap and returns result on main thread
photoResult
    .toBitmap()
    .whenAvailable { bitmapPhoto ->
            val imageView = (ImageView) findViewById(R.id.result)
 
            imageView.setImageBitmap(bitmapPhoto.bitmap)
            imageView.setRotation(-bitmapPhoto.rotationDegrees)
    }
    
// Of course you can also get a photo in a blocking way. Do not do it on main thread though.
val result = photoResult.toBitmap().await()
 
// Convert asynchronous events to RxJava 1.x/2.x types. 
// See /fotoapparat-adapters/ module 
photoResult
        .toBitmap()
        .toSingle()
        .subscribe { bitmapPhoto -> 
            
        }
```

## Update parameters

It is also possible to update some parameters after `Fotoapparat` was already started.

```kotlin
fotoapparat.updateConfiguration(
        UpdateConfiguration(
                flashMode = if (isChecked) torch() else off()
                // ...
                // all the parameters available in CameraConfiguration 
        )
)
```

Or alternatively you may provide updates on an existing full configuration. 

```kotlin
val configuration = CameraConfiguration(
    // A full configuration
    // ...
)
 
fotoapparat.updateConfiguration(
    configuration.copy(
            flashMode = if (isChecked) torch() else off()
            // all the parameters available in CameraConfiguration 
    )
)
```

## Switch cameras

In order to switch between cameras, `Fotoapparat.switchTo()` can be used with the new desired `lensPosition` and its `cameraConfiguration`.

```kotlin
fotoapparat.switchTo(
    lensPosition = front(),
    cameraConfiguration = newConfigurationForFrontCamera
)
```

## Set up

Add dependency to your `build.gradle`

```groovy
repositories {
  maven { url 'https://jitpack.io' }
}
 
compile 'io.fotoapparat.fotoapparat:library:2.x.x.x' // TODO
```

Camera permission will be automatically added to your `AndroidManifest.xml`. Do not forget to request this permission on Marshmallow and higher.

## Face detection

Optionally, you can check out our other library which adds face detection capabilities - [FaceDetector](https://github.com/Fotoapparat/FaceDetector).

## Credits

We want to say thanks to [Mark Murphy](https://github.com/commonsguy) for the awesome job he did with [CWAC-Camera](https://github.com/commonsguy/cwac-camera). We were using his library for a couple of years and now we feel that Fotoapparat is a next step in the right direction.

We also want to say many thanks to [Leander Lenzing](http://leanderlenzing.com/) for the amazing icon. Don't forget to follow his work in [dribbble](https://dribbble.com/leanderlenzing).

## License

```
Copyright 2017 Fotoapparat

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

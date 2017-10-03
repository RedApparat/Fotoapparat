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

```java
Fotoapparat fotoapparat = Fotoapparat
    .with(context)  
    .into(cameraView)
    .build();

fotoapparat.start();
    
fotoapparat
    .takePicture()
    .saveToFile(someFile);
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

Configure `Fotoapparat` instance

```java
Fotoapparat
    .with(context)  
    .into(cameraView)           // view which will draw the camera preview
    .previewScaleType(ScaleType.CENTER_CROP)  // we want the preview to fill the view  
    .photoSize(biggestSize())   // we want to have the biggest photo possible
    .lensPosition(back())       // we want back camera
    .focusMode(firstAvailable(  // (optional) use the first focus mode which is supported by device
            continuousFocus(),
            autoFocus(),        // in case if continuous focus is not available on device, auto focus will be used
            fixed()             // if even auto focus is not available - fixed focus mode will be used
    ))
    .flash(firstAvailable(      // (optional) similar to how it is done for focus mode, this time for flash
            autoRedEye(),
            autoFlash(),
            torch()
    ))
    .frameProcessor(myFrameProcessor)   // (optional) receives each frame from preview stream
    .logger(loggers(            // (optional) we want to log camera events in 2 places at once
            logcat(),           // ... in logcat
            fileLogger(this)    // ... and to file
    ))
    .build();
```

### Step Three

Call `start()` and `stop()`. No rocket science here.

```java
@Override
protected void onStart() {
    super.onStart();
    fotoapparat.start();
}

@Override
protected void onStop() {
    super.onStop();
    fotoapparat.stop();
}
```

### Take picture

Finally we are ready to take picture. You have various options.

```java
PhotoResult photoResult = fotoapparat.takePicture();
 
// Asynchronously saves photo to file
photoResult.saveToFile(someFile);
 
// Asynchronously converts photo to bitmap and returns result on main thread
photoResult
    .toBitmap()
    .whenAvailable(new PendingResult.Callback<BitmapPhoto>() {
        @Override
        public void onResult(BitmapPhoto result) {
            ImageView imageView = (ImageView) findViewById(R.id.result);

            imageView.setImageBitmap(result.bitmap);
            imageView.setRotation(-result.rotationDegrees);
        }
    });
    
// Of course you can also get a photo in a blocking way. Do not do it on main thread though.
BitmapPhoto result = photoResult.toBitmap().await();
 
// Convert asynchronous events to RxJava 1.x/2.x types. See /fotoapparat-adapters/ module
photoResult
        .toBitmap()
        .adapt(SingleAdapter.<BitmapPhoto>toSingle())
        .subscribe(bitmapPhoto -> {
            
        });
```

## Update parameters

It is also possible to update some parameters after `Fotoapparat` was already started.

```java
fotoapparat.updateParameters(
        UpdateRequest.builder()
                .flash(
                    isTurnedOn 
                        ? torch() 
                        : off()
                )
                .build()
)
```

## Set up

Add dependency to your `build.gradle`

```groovy
repositories {
  maven { url 'https://jitpack.io' }
}
 
compile 'io.fotoapparat.fotoapparat:library:1.4.1'
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

# Adapters

Fotoapparat ships with a default callback mechanism for executing object acquisition tasks, such as photos or capabilities. 
The child modules contained herein are additional adapters for other popular execution mechanisms.

To use, supply an instance of your desired adapter when performing a task of a PendingResult.

```java
fotoapparat.takePicture()
	.toBitmap()
	.adapt(ObservableAdapter.<BitmapPhoto>toObservable())
	.subscribe(new Action1<BitmapPhoto>() {
		@Override
		public void call(BitmapPhoto bitmapPhoto) {
			// Do something with the photo
		}
	});
```

## Set up

Add the Fotoapparat and the relevant adapter dependency to your `build.gradle`

```groovy
repositories {
  maven { url 'https://jitpack.io' }
}

compile 'io.fotoapparat.fotoapparat:library:1.0.2'
compile 'io.fotoapparat.fotoapparat:adapter-rxjava:1.0.2'   // for RxJava 1
compile 'io.fotoapparat.fotoapparat:adapter-rxjava2:1.0.2'  // for RxJava 2
```

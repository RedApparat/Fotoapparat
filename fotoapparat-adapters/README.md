# Adapters

Fotoapparat ships with a default callback mechanism for executing object acquisition tasks, such as photos or capabilities. 
The child modules contained herein are additional adapters for other popular execution mechanisms.

To use, supply an instance of your desired adapter when performing a task of a PendingResult.

Kotlin:
```java
fotoapparat.takePicture()
	.toBitmap()
	.toObservable()
	.subscribe { bitmapPhoto ->
			// Do something with the photo
		}
```


Java:
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



Supported types:

 * `Observable<T>` : RxJava 1/2
 * `Flowable<T>` : RxJava 2
 * `Single<T>` : RxJava 1/2
 * `Completable` : RxJava 1/2
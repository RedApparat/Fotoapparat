package io.fotoapparat.parameter

/**
 * The scale type of the preview relatively to the view.
 */
sealed class ScaleType {

    /**
     * The preview will be scaled so as its one dimensions will be equal and the other one equal or
     * larger than the corresponding dimension of the view
     */
    object CenterCrop : ScaleType()

    /**
     * The preview will be scaled so as its one dimensions will be equal and the other one equal or
     * smaller than the corresponding dimension of the view
     */
    object CenterInside : ScaleType()

}
package io.fotoapparat.parameter;

/**
 * The scale type of the preview relatively to the view.
 */
public enum ScaleType {

    /**
     * The preview will be scaled so as its one dimensions will be equal and the other one equal or
     * larger than the corresponding dimension of the view
     */
    CENTER_CROP,

    /**
     * The preview will be scaled so as its one dimensions will be equal and the other one equal or
     * smaller than the corresponding dimension of the view
     */
    CENTER_INSIDE

}

package io.fotoapparat.parameter;

/**
 * The style of the preview.
 */
public enum ScaleType {

    /**
     * The preview will be scaled so as it's one dimensions will be equal and the other equal or
     * larger than the corresponding dimension of the view
     */
    CENTER_CROP,

    /**
     * The preview will be scaled so as it's one dimensions will be equal and the other equal or
     * smaller than the corresponding dimension of the view
     */
    CENTER_INSIDE

}

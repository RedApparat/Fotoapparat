package io.fotoapparat.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.FrameLayout
import io.fotoapparat.hardware.metering.FocalRequest
import io.fotoapparat.hardware.metering.PointF
import io.fotoapparat.parameter.Resolution

/**
 * A view which is metering the focus & exposure of the camera to specific areas, if possible.
 *
 * If the camera doesn't support focus metering on specific area this will only display a visual feedback.
 */
class FocusView
@JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), FocalPointSelector {

    private val visualFeedbackCircle = FeedbackCircleView(context, attrs, defStyleAttr)
    private var focusMeteringListener: ((FocalRequest) -> Unit)? = null

    init {
        clipToPadding = false
        clipChildren = false
        addView(visualFeedbackCircle)
    }

    override fun setFocalPointListener(listener: (FocalRequest) -> Unit) {
        focusMeteringListener = listener
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        tapDetector.onTouchEvent(event)
        return true
    }

    private val gestureDetectorListener = object : GestureDetector.SimpleOnGestureListener() {
        override fun onSingleTapUp(e: MotionEvent): Boolean {
            return focusMeteringListener
                    ?.let {
                        it(FocalRequest(
                                point = PointF(
                                        x = e.x,
                                        y = e.y),
                                previewResolution = Resolution(
                                        width = width,
                                        height = height)))
                        visualFeedbackCircle.showAt(
                                x = e.x - visualFeedbackCircle.width / 2,
                                y = e.y - visualFeedbackCircle.height / 2)
                        performClick()

                        true
                    }
                    ?: super.onSingleTapUp(e)
        }
    }

    private val tapDetector = GestureDetector(context, gestureDetectorListener)
}

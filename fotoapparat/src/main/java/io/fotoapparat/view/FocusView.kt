package io.fotoapparat.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
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
        if (event.action == MotionEvent.ACTION_DOWN) {
            focusMeteringListener?.let {
                it(FocalRequest(
                        point = PointF(
                                x = event.x,
                                y = event.y
                        ),
                        previewResolution = Resolution(
                                width = width,
                                height = height
                        )
                ))
                visualFeedbackCircle.showAt(
                        x = event.x - visualFeedbackCircle.width / 2,
                        y = event.y - visualFeedbackCircle.height / 2
                )
                performClick()
                return true
            }
        }
        return super.onTouchEvent(event)
    }

}

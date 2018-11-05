package io.fotoapparat.view

import android.animation.AnimatorInflater.loadAnimator
import android.animation.AnimatorSet
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.annotation.AnimatorRes
import io.fotoapparat.R

/**
 * A circle which gives feedback to the user about a click.
 */
internal class FeedbackCircleView
@JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val inner = ImageView(context, attrs, defStyleAttr).apply {
        setImageResource(R.drawable.focus_inner)
        layoutParams = context.resources.run {
            ViewGroup.LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT
            )
        }
        alpha = 0f
    }

    private val outer = ImageView(context, attrs, defStyleAttr).apply {
        setImageResource(R.drawable.focus_outer)
        layoutParams = context.resources.run {
            ViewGroup.LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT
            )
        }
        alpha = 0f
    }

    private val animatorSet = AnimatorSet().apply {
        playTogether(
                newAnimator(R.animator.focus_outer, outer),
                newAnimator(R.animator.focus_inner, inner)
        )
    }

    init {
        layoutParams = context.resources.run {
            ViewGroup.LayoutParams(
                    getDimensionPixelSize(R.dimen.focus_diameter),
                    getDimensionPixelSize(R.dimen.focus_diameter)
            )
        }
        clipToPadding = false
        clipChildren = false

        addView(outer)
        addView(inner)
    }


    /**
     * Displays the view at the given coordinates.
     */
    internal fun showAt(x: Float, y: Float) {
        translationX = x
        translationY = y

        animatorSet.cancel()
        animatorSet.start()
    }


    private fun newAnimator(@AnimatorRes id: Int, target: View) = loadAnimator(context, id).apply {
        setTarget(target)
    }
}

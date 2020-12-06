package so.codex.hawk.custom.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.util.AttributeSet
import kotlin.math.abs
import kotlin.math.pow

/**
 * Custom view that implements the clipping of the nested resource according
 * to the formula to obtain the squircle form. Works if the width is equal to the height.
 *
 * Formula:
 * |x/radius|^[n] + |y/radius|^[n] = 1
 *
 * @param context This parameter is supplied by the system when creating a view.
 *                It must be provided from the init block.
 * @param attrs This parameter is supplied by the system when creating a view.
 *              It must be provided from the init block. The parameter contains
 *              many attributes set in the xml view.
 */
class SquircleImageView(context: Context, attrs: AttributeSet) :
    androidx.appcompat.widget.AppCompatImageView(context, attrs) {
    /**
     * @property n Power. Used in squircle formula.
     */
    private val n = 4

    /**
     * @property path An instance of the path along which the resource will be trimmed.
     */
    private lateinit var path: Path

    /**
     * Method for rendering view to canvas.
     *
     * @param canvas The Canvas class holds the "draw" calls.
     * @see Canvas
     */
    override fun onDraw(canvas: Canvas?) {
        canvas?.clipPath(path)
        super.onDraw(canvas)
    }

    /**
     * This is called during layout when the size of this view has changed. If
     * you were just added to the view hierarchy, you're called with the old
     * values of 0.
     *
     * @param w Current width of this view.
     *
     * @param h Current height of this view.
     *
     * @param oldw Old width of this view.
     *
     * @param oldh Old height of this view.
     */
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        path = createSquirclePath(w)
    }

    /**
     * Clipping path creation method.
     *
     * @return clipping [Path].
     */
    private fun createSquirclePath(width: Int): Path {
        val path = Path()
        val radius = (width / 2).toFloat()
        path.moveTo(-radius, 0f)
        var x = -radius
        while (x <= radius) {
            path.lineTo(x + radius, calculationY(radius, x) + radius)
            x += 0.5f
        }
        x = radius
        while (x >= -radius) {
            path.lineTo(x + radius, -calculationY(radius, x) + radius)
            x -= 0.5f
        }
        return path
    }

    /**
     * Method for calculating Y from the passed X value and radius.
     *
     * Formula:
     * |x/radius|^n + |y/radius|^n = 1
     *
     * @param radius view.
     *
     * @param x coordinate.
     *
     * @return y coordinate.
     */
    private fun calculationY(radius: Float, x: Float): Float {
        val yN = 1 - (abs(x / radius).pow(n))
        val base = yN * ((radius).pow(n)).toDouble()
        val y = base.pow(1.0 / n.toDouble())
        return y.toFloat()
    }
}

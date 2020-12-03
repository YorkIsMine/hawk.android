package so.codex.hawk.custom.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.util.AttributeSet
import kotlin.math.abs
import kotlin.math.ln
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
    private var path: Path? = null

    /**
     * @property isOnMeasureWorked a flag that responds to the resizing of the view.
     */
    private var isOnMeasureWorked = false

    /**
     * Method for rendering view to canvas.
     *
     * @param canvas The Canvas class holds the "draw" calls.
     * @see Canvas
     */
    override fun onDraw(canvas: Canvas?) {
        if (path == null || isOnMeasureWorked) {
            isOnMeasureWorked = false
            path = createSquirclePath()
        }
        canvas?.clipPath(path!!)
        super.onDraw(canvas)
    }

    /**
     * Measure the view and its content to determine the measured width and the measured height.
     *
     * @param widthMeasureSpec horizontal space requirements as imposed by the parent.
     *
     * @param heightMeasureSpec vertical space requirements as imposed by the parent.
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        isOnMeasureWorked = true
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    /**
     * Clipping path creation method.
     *
     * @return clipping [Path].
     */
    private fun createSquirclePath(): Path {
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
        val y = abs(Math.E.pow(ln(base) / n))
        return y.toFloat()
    }
}

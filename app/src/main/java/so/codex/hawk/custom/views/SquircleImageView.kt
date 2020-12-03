package so.codex.hawk.custom.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.util.AttributeSet
import kotlin.math.abs
import kotlin.math.ln
import kotlin.math.pow

/**
 * TODO(add documentation)
 */
class SquircleImageView(context: Context, attrs: AttributeSet) :
    androidx.appcompat.widget.AppCompatImageView(context, attrs) {
    /**
     *
     */
    private val n = 4

    /**
     *
     */
    private var path: Path? = null

    /**
     *
     */
    private var isOnMeasureWorked = false

    /**
     *
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
     *
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        isOnMeasureWorked = true
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    /**
     *
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
     * formula:
     * |x/radius|^n + |y/radius|^n = 1
     */
    private fun calculationY(radius: Float, x: Float): Float {
        val yN = 1 - (abs(x / radius).pow(n))
        val base = yN * ((radius).pow(n)).toDouble()
        val y = abs(Math.E.pow(ln(base) / n))
        return y.toFloat()
    }

}
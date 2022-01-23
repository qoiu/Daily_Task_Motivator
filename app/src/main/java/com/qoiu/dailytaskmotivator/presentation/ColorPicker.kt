package com.qoiu.dailytaskmotivator.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import kotlin.math.min


class ColorPicker @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var relative = 150f

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        val min = min(w, h)
        relative = min / 7f
        super.onSizeChanged(w, h, oldw, oldh)
    }

    private var activeCell = 9

    private val paint = Paint()

    init {
        initColorGrid()
    }

    var defaultColor =  "#ffffffff"
    set(color) {
        for(i in colorGrid.keys){
            if(colorGrid[i] == color)this.activeCell = i
        }
        field = color
    }
    fun getActiveColor(): String = colorGrid[activeCell] ?: defaultColor


    private lateinit var colorGrid: MutableMap<Int, String>
    private fun initColorGrid() {
        colorGrid = mutableMapOf()
        val modifier = 200
        for (x in 1..7) {
            val step = (255-modifier) /7
            val hard = modifier + step * x
            val intense = 25*x + 80
            colorGrid[rowId(x, 1)] = getColor(0, 0, intense)
            colorGrid[rowId(x, 2)] = getColor(modifier, modifier, hard)
            colorGrid[rowId(x, 3)] = getColor(modifier, hard, hard)
            colorGrid[rowId(x, 4)] = getColor(modifier, hard, modifier)
            colorGrid[rowId(x, 5)] = getColor(0, intense, 0)
            colorGrid[rowId(x, 6)] = getColor(hard, hard, modifier)
            colorGrid[rowId(x, 7)] = getColor(hard, modifier, modifier)
            colorGrid[rowId(x, 8)] = getColor(intense, 0, 0)
            colorGrid[rowId(x, 9)] = getColor(hard, modifier, hard)
            colorGrid[rowId(x, 10)] = getColor(37*x, 37*x, 37*x)
        }
    }

    private fun rowId(x: Int, y: Int): Int = x * 10 + y

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        for (x in 1..7) {
            for (y in 1..10) {
                colorGrid[rowId(x, y)]?.let {
                    if (activeCell == rowId(x, y)) {
                        paint.color = Color.BLACK
                        canvas?.drawCircle(
                            x * relative - relative / 2,
                            y * relative - relative / 2,
                            relative / 3 + 5,
                            paint
                        )
                    }
                    paint.color = Color.parseColor(it)
                    canvas?.drawCircle(
                        x * relative - relative / 2,
                        y * relative - relative / 2,
                        relative / 3,
                        paint
                    )
                }
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event != null) {
            if (event.x < relative * 7 && event.y < relative * 10)
                activeCell = getCell(event.x, event.y)
            this.invalidate()
        }
        return super.onTouchEvent(event)
    }

    private fun getCell(x: Float, y: Float) =
        rowId((x / relative + 1).toInt(), (y / relative + 1).toInt())


    override fun isClickable(): Boolean {
        return true
    }

    private fun getColor(r: Int, g: Int, b: Int): String =
        "#ff${getHex(r)}${getHex(g)}${getHex(b)}"

    private fun getHex(i: Int): String {
        if (i > 255) return "ff"
        if (i < 50) return "32"
        val hex = Integer.toHexString(i)
        return if (hex.length < 2) {
            "0$hex"
        } else {
            hex
        }
    }
}

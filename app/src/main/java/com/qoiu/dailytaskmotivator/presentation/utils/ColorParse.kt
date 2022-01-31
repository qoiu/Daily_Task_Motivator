package com.qoiu.dailytaskmotivator.presentation.utils

import com.qoiu.dailytaskmotivator.Execute
import java.lang.IllegalArgumentException

/**
 * Convert color: String to Int
 *@throws IllegalArgumentException color should start from "#" and be like "#FFFFFFFF"
 **/
class ColorParse( private val color: String
) : Execute<Int> {

    init {
        if (color[0] != '#' || color.length != 9)
            throw IllegalArgumentException("Unknown color")
    }

    fun getFontColor(
        defaultBright: String = "#FFDDDDDD",
        defaultDark: String = "#FF777777",
    ): Int {
        val r = color.substring(3, 5)
        val g = color.substring(5, 7)
        val b = color.substring(7, 9)
        val total = r.toInt(16) + g.toInt(16) + b.toInt(16)
        val color =
            if (total >= 355)
                defaultDark
            else
                defaultBright
        return ColorParse(color).execute()
    }

    override fun execute(): Int {
        val color = color.substring(1).toLong(16)
        return color.toInt()
    }
}
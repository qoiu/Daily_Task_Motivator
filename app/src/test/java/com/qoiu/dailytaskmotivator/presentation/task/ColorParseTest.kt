package com.qoiu.dailytaskmotivator.presentation.task

import com.qoiu.dailytaskmotivator.presentation.utils.ColorParse
import org.junit.Assert.*
import org.junit.Test

class ColorParseTest{

    @Test
    fun simpleTest(){
        assertEquals(ColorParse("#FF333333").execute(), ColorParse("#ffffffff").getFontColor())
        assertEquals(-1, ColorParse("#ffff0000").getFontColor())
    }

    @Test(expected = IllegalArgumentException::class)
    fun wrongFormat(){
        ColorParse("#ffff00f").getFontColor()
    }

    @Test(expected = IllegalArgumentException::class)
    fun wrongSymbol(){
        ColorParse("#ffff00rr").getFontColor()
    }


}
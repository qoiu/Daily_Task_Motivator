package com.qoiu.dailytaskmotivator.presentation.utils

import org.junit.Assert.assertEquals
import org.junit.Test

class ColorParseTest{

    @Test
    fun simpleTest(){
        assertEquals(ColorParse("#FF777777").execute(), ColorParse("#ffffffff").getFontColor())
        assertEquals(ColorParse("#FFDDDDDD").execute(), ColorParse("#ffff0000").getFontColor())
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
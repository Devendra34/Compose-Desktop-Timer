package com.example.timer.util

import androidx.compose.ui.graphics.Color

fun Color.toAwtColor(): java.awt.Color {
    return java.awt.Color(red, green, blue)
}
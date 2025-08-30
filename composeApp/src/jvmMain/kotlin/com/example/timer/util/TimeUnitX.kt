package com.example.timer.util

import java.util.concurrent.TimeUnit

fun TimeUnit.toLabel(): String {
    return when (this) {
        TimeUnit.HOURS -> "HH"
        TimeUnit.MINUTES -> "MM"
        TimeUnit.SECONDS -> "SS"
        TimeUnit.MILLISECONDS -> "MS"
        else -> ""
    }
}
package com.example.timer

import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

fun main() = application {

    val state = rememberWindowState(
        size = DpSize.Unspecified
    )
    Window(
        state = state,
        onCloseRequest = ::exitApplication,
        undecorated = true,
        alwaysOnTop = true,
        title = "Timer",
    ) {

        val density = LocalDensity.current
        var previousHeight by remember { mutableStateOf(Dp.Unspecified) }
        var maxHeight by remember { mutableStateOf(Dp.Unspecified) }
        val viewModel by remember { mutableStateOf(TimerViewModel()) }

        WindowDraggableArea {
            TimerApp(
                modifier = Modifier.onSizeChanged({
                    val newHeight = with(density) {
                        it.height.toDp()
                    }
                    if (maxHeight == Dp.Unspecified) {
                        maxHeight = newHeight
                    }
                    if (previousHeight != newHeight) {
                        previousHeight = newHeight
                    }
                }),
                viewModel = viewModel,
                window = window,
            )
        }
        LaunchedEffect(previousHeight) {
            if (!viewModel.isStarted) return@LaunchedEffect
            if (previousHeight != Dp.Unspecified) {
                state.size = state.size.copy(height = previousHeight)
            }
        }
        LaunchedEffect(viewModel.isStarted) {
            if (!viewModel.isStarted) {
                state.size = state.size.copy(height = maxHeight)
            }
        }
    }
}
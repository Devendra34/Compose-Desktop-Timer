package com.example.timer.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Pause
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.Stop
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.runtime.Composable
import com.example.timer.TimerViewModel

@Composable
fun StopButton(viewModel: TimerViewModel) {
    AnimatedVisibility(
        visible = viewModel.isStarted
    ) {
        OutlinedIconButton(
            onClick = viewModel::stop,
        ) {
            Icon(Icons.Rounded.Stop, null)
        }
    }
}

@Composable
fun PlayPauseButton(viewModel: TimerViewModel) {
    OutlinedIconButton(
        onClick = viewModel::onPlayPauseClick
    ) {
        val icon = when {
            !viewModel.isStarted -> Icons.Rounded.PlayArrow
            viewModel.isStarted && !viewModel.isRunning -> Icons.Rounded.PlayArrow
            else -> Icons.Rounded.Pause
        }
        Icon(imageVector = icon, contentDescription = null)
    }
}
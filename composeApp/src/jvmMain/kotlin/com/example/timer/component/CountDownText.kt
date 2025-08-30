package com.example.timer.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.timer.TimerViewModel

@Composable
fun CountDownText(
    modifier: Modifier = Modifier,
    viewModel: TimerViewModel,
) {
    val remainingTime by viewModel.remainingTime.collectAsStateWithLifecycle("")
    Text(
        modifier = modifier,
        text = remainingTime
    )
}
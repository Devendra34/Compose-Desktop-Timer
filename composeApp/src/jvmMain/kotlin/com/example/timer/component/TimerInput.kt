package com.example.timer.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.unit.dp
import com.example.timer.TimerViewModel
import java.util.concurrent.TimeUnit

@Composable
fun TimerInput(
    modifier: Modifier = Modifier.Companion, viewModel: TimerViewModel
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {

        val focusRequester1 = remember { FocusRequester() }
        val focusRequester2 = remember { FocusRequester() }
        val focusRequester3 = remember { FocusRequester() }

        TimerFieldInput(
            modifier = Modifier.weight(1f),
            timeUnit = TimeUnit.HOURS,
            selfFocus = focusRequester1,
            nextFocus = focusRequester2,
            viewModel = viewModel
        )

        TimerFieldInput(
            modifier = Modifier.weight(1f),
            timeUnit = TimeUnit.MINUTES,
            limit = 59,
            selfFocus = focusRequester2,
            nextFocus = focusRequester3,
            prevFocus = focusRequester1,
            viewModel = viewModel
        )
        TimerFieldInput(
            modifier = Modifier.weight(1f),
            timeUnit = TimeUnit.SECONDS,
            limit = 59,
            selfFocus = focusRequester3,
            prevFocus = focusRequester2,
            viewModel = viewModel
        )
    }
}
package com.example.timer.component

import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.isShiftPressed
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import com.example.timer.TimerViewModel
import com.example.timer.util.toLabel
import java.util.concurrent.TimeUnit

@Composable
fun TimerFieldInput(
    modifier: Modifier = Modifier.Companion,
    timeUnit: TimeUnit,
    limit: Int? = null,
    selfFocus: FocusRequester,
    nextFocus: FocusRequester? = null,
    prevFocus: FocusRequester? = null,
    viewModel: TimerViewModel
) {

    var textFieldValue by remember {
        val initialValue = when (timeUnit) {
            TimeUnit.HOURS -> String.format("%2d", viewModel.hrs)
            TimeUnit.MINUTES -> String.format("%2d", viewModel.mins)
            TimeUnit.SECONDS -> String.format("%2d", viewModel.seconds)
            else -> "00"
        }
        mutableStateOf(TextFieldValue(initialValue))
    }
    var hasFocus by remember { mutableStateOf(false) }
    TextField(
        label = {
            Text(timeUnit.toLabel())
        },
        modifier = modifier
            .clickable(onClick = {
                textFieldValue = textFieldValue.copy(selection = TextRange(0, textFieldValue.text.length))
            })
            .focusRequester(selfFocus)
            .onKeyEvent {
                return@onKeyEvent if (it.key == Key.Tab) {
                    if (it.isShiftPressed) {
                        prevFocus?.requestFocus() ?: false
                    } else {
                        nextFocus?.requestFocus() ?: false
                    }
                } else {
                    false
                }
            }.onFocusChanged {
                hasFocus = it.hasFocus
                if (!it.hasFocus) {
                    val text = String.format("%02d", textFieldValue.text.toIntOrNull() ?: 0)
                    textFieldValue = TextFieldValue(text)
                }
            },
        value = textFieldValue,
        onValueChange = {
            val text = it.text.trim()
            val value = text.toIntOrNull() ?: 0.takeIf { text.isEmpty() }
            if (value != null && (value <= (limit ?: Int.MAX_VALUE))) {
                textFieldValue = it.copy(text = text)
                viewModel.onTimeChange(value, timeUnit)
            }
        },
    )
    LaunchedEffect(hasFocus) {
        if (!hasFocus) return@LaunchedEffect
        if (textFieldValue.selection.length != textFieldValue.text.length) {
            textFieldValue = textFieldValue.copy(selection = TextRange(0, textFieldValue.text.length))
        }
    }
}
package com.example.timer

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.timer.component.*
import com.example.timer.ui.TimerTheme
import com.example.timer.util.toAwtColor
import org.jetbrains.compose.ui.tooling.preview.Preview
import java.awt.Window
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
@Composable
@Preview
fun TimerApp(
    window: Window,
    modifier: Modifier = Modifier,
    viewModel: TimerViewModel,
) {
    var selectedTheme by remember { mutableIntStateOf(SYSTEM_THEME) }
    TimerTheme(
        darkTheme = when (selectedTheme) {
            SYSTEM_THEME -> isSystemInDarkTheme()
            DARK_THEME -> true
            else -> false
        }
    ) {

        Column(
            modifier = modifier
                .background(MaterialTheme.colorScheme.surface)
                .defaultMinSize(200.dp)
                .safeContentPadding(),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                PlayPauseButton(viewModel)
                StopButton(viewModel)
                CountDownText(viewModel = viewModel)
            }

            AnimatedVisibility(
                visible = !viewModel.isStarted
            ) {
                Column {
                    TimerInput(
                        modifier = Modifier.width(200.dp),
                        viewModel = viewModel,
                    )
                    ThemeSwitch(
                        selectedTheme = selectedTheme,
                    ) {
                        selectedTheme = it
                    }
                }
            }
        }

        val bgColor = MaterialTheme.colorScheme.background
        LaunchedEffect(bgColor) {
            window.background = bgColor.toAwtColor()
        }
    }
}

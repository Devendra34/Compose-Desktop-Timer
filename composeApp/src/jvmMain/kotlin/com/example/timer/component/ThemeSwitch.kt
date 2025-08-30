package com.example.timer.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun ThemeSwitch(
    selectedTheme: Int,
    changeTheme: (value: Int) -> Unit,
) {
    val style = MaterialTheme.typography.bodySmall
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        OutlinedButton(
            onClick = { changeTheme(SYSTEM_THEME) },
            colors = if (selectedTheme == SYSTEM_THEME) ButtonDefaults.buttonColors()
            else ButtonDefaults.outlinedButtonColors(),
            contentPadding = PaddingValues(8.dp),
        ) {
            Text(text = "System", style = style)
        }
        OutlinedButton(
            onClick = { changeTheme(LIGHT_THEME) },
            colors = if (selectedTheme == LIGHT_THEME) ButtonDefaults.buttonColors()
            else ButtonDefaults.outlinedButtonColors(),
            contentPadding = PaddingValues(8.dp),
        ) {
            Text("Light", style = style)
        }
        OutlinedButton(
            onClick = { changeTheme(DARK_THEME) },
            colors = if (selectedTheme == DARK_THEME) ButtonDefaults.buttonColors()
            else ButtonDefaults.outlinedButtonColors(),
            contentPadding = PaddingValues(8.dp),
        ) {
            Text("Dark", style = style)
        }
    }
}

const val SYSTEM_THEME = 0
const val LIGHT_THEME = 1
const val DARK_THEME = 2

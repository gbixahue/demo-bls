package com.tangem.demo_bls.ui.widgets.chipGroup

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * Created by Anton Zhilenkov on 05/03/2022.
 */
@Composable
fun <T> Chip(
  modifier: Modifier = Modifier,
  onSelectionChanged: (T) -> Unit = {},
  name: String = "Chip",
  isSelected: Boolean = true,
  value: T,
  content: (@Composable () -> Unit)? = null
) {
  val selectedColor = if (isSelected) {
    MaterialTheme.colors.primarySurface.copy(alpha = 0.5f)
  } else {
    MaterialTheme.colors.primarySurface.copy(alpha = 0.05f)
  }
  val textSelectedColor = if (isSelected) {
    Color.White
  } else {
    Color.Black
  }

  Surface(
    border = BorderStroke(0.5.dp, MaterialTheme.colors.primary),
    shape = CircleShape,
    color = selectedColor,
  ) {
    Row(
      modifier = Modifier
        .toggleable(
          value = isSelected,
          onValueChange = { onSelectionChanged(value) },
        ),
    ) {
      Box(modifier = Modifier.padding(horizontal = 8.dp)) {
        if (content == null) {
          Text(
            text = name,
            color = textSelectedColor,
            modifier = Modifier.padding(4.dp)
          )
        } else {
          content()
        }
      }
    }
  }
}
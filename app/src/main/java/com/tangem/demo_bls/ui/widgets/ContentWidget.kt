package com.tangem.demo_bls.ui.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * Created by Anton Zhilenkov on 05/03/2022.
 */
@Composable
fun ContentWidget(
  name: String,
  content: @Composable () -> Unit
) {
  Box {
    Card(
      modifier = Modifier.padding(16.dp, 16.dp, 16.dp, 8.dp),
      elevation = 6.dp,
      content = content
    )
    Box(
      modifier = Modifier
        .offset(x = 32.dp, y = 6.dp)
        .background(Color.White),
    ) {
      Text(
        name,
        modifier = Modifier.padding(horizontal = 4.dp),
        color = Color.Gray
      )
    }
  }
}
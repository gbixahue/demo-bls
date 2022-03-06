package com.tangem.demo_bls.ui.widgets.actions

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.tangem.demo_bls.ValueCallback
import com.tangem.demo_bls.readJsonFileToString
import com.tangem.demo_bls.sdk
import com.tangem.demo_bls.ui.widgets.ContentWidget

/**
 * Created by Anton Zhilenkov on 05/03/2022.
 */
@Composable
fun ConfigureCardWidget() {
  val context = LocalContext.current

  ContentWidget(name = "Configure the card") {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
    ) {
      Button(
        onClick = {
          personalize(context) {
            //            context.toast(it)
          }
        },
        modifier = Modifier.fillMaxWidth()
      ) { Text(text = "Personalize") }
      Spacer(modifier = Modifier.width(8.dp))
      Button(
        onClick = ::depersonalize,
        modifier = Modifier.fillMaxWidth()
      ) { Text(text = "Depersonalize") }
    }
  }
}

private fun personalize(context: Context, resultHandler: ValueCallback<String>) {
  val text = context.assets.readJsonFileToString("personalization")
  sdk.startSessionWithJsonRequest(text, null, null) {
    resultHandler(it)
  }
}

private fun depersonalize() {
  sdk.depersonalize { }
}
package com.tangem.demo_bls.ui.widgets.actions

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tangem.common.CompletionResult
import com.tangem.common.extensions.toHexString
import com.tangem.demo_bls.Utils
import com.tangem.demo_bls.cardSdk.SignHashTask
import com.tangem.demo_bls.copyToClipboard
import com.tangem.demo_bls.sdk
import com.tangem.demo_bls.toast
import com.tangem.demo_bls.ui.widgets.ContentWidget

/**
 * Created by Anton Zhilenkov on 05/03/2022.
 */
@Preview
@Composable
fun SignHashWidget() {
  var hashForSign by rememberSaveable { mutableStateOf("") }
  var signResult by rememberSaveable { mutableStateOf("") }
  val context = LocalContext.current

  ContentWidget(name = "Sign hash") {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(all = 16.dp)
    ) {
      TextField(
        label = { Text("Input hash to sign") },
        value = hashForSign,
        modifier = Modifier.fillMaxWidth(),
        onValueChange = { hashForSign = it },
      )
      Button(
        onClick = {
          if (hashForSign.isEmpty()) {
            context.toast("Hash is empty")
            return@Button
          }
          sign(hashForSign) { signResult = it }
        },
        modifier = Modifier.fillMaxWidth()
      ) { Text(text = "Sign") }
      if (signResult.isNotEmpty()) {
        Text(
          "Check for sign result:",
          color = Color.Gray,
        )
        Text(signResult)
        Button(
          onClick = {
            context.copyToClipboard(signResult)
            context.toast("Copied to clipboard ")
          },
          modifier = Modifier.align(Alignment.End),
        ) { Text("Tap to copy") }
      }
    }
  }
}

class SignHashViewModel: ViewModel() {
  private val _hashForSign = MutableLiveData<String>("")
  private val _signResult = MutableLiveData<String>("")

  val hashForSign: LiveData<String> = _hashForSign
  val signResult: LiveData<String> = _signResult

  fun onHashForSignChanged(newValue: String) {
    _hashForSign.value = newValue
  }

  fun onSignResultChanged(newValue: String) {
    _signResult.value = newValue
  }

}

private fun prepareHashesToSign(count: Int): Array<ByteArray> {
  val listOfData = MutableList(count) { Utils.randomString(32) }
  val listOfHashes = listOfData.map { it.toByteArray() }
  return listOfHashes.toTypedArray()
}

private fun sign(hashString: String, resultHandler: (String) -> Unit) {
  val hash = if (hashString.isEmpty()) prepareHashesToSign(1)[0] else hashString.toByteArray()
  val signTask = SignHashTask(hash)

  sdk.startSessionWithRunnable(signTask) {
    when (it) {
      is CompletionResult.Success -> resultHandler(it.data.signature.toHexString())
      is CompletionResult.Failure -> {
        if (it.error.code == 50002) {
          resultHandler("Cancelled")
        } else {
          resultHandler("Error: ${it.error.code}")
        }
      }
    }
  }
}
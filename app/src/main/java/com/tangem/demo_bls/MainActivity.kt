package com.tangem.demo_bls

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tangem.TangemSdk
import com.tangem.demo_bls.ui.widgets.actions.ConfigureCardWidget
import com.tangem.demo_bls.ui.widgets.actions.SignHashViewModel
import com.tangem.demo_bls.ui.widgets.actions.SignHashWidget
import com.tangem.demo_bls.ui.widgets.actions.WalletsWidget
import com.tangem.tangem_sdk_new.extensions.init

class MainActivity: ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent { DemoApp() }
    sdk = TangemSdk.init(this)
  }
}

lateinit var sdk: TangemSdk

@Preview(showBackground = true)
@Composable
fun DemoApp() {
  Surface(
    modifier = Modifier.fillMaxSize(),
    color = MaterialTheme.colors.background
  ) {
    Scaffold(
      topBar = { TopAppBar(title = { Text("Demo BLS") }) },
    ) {
      Spacer(modifier = Modifier.height(32.dp))
      Column(
        modifier = Modifier.fillMaxWidth()
      ) {
        ConfigureCardWidget()
        WalletsWidget()
        SignHashWidget()
      }
    }
  }
}
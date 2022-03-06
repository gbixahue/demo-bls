package com.tangem.demo_bls.ui.widgets.actions

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tangem.common.card.EllipticCurve
import com.tangem.demo_bls.cardSdk.PurgeWalletTask
import com.tangem.demo_bls.sdk
import com.tangem.demo_bls.ui.widgets.ContentWidget
import com.tangem.demo_bls.ui.widgets.chipGroup.ChipGroupSingleSelection
import com.tangem.operations.wallet.CreateWalletTask

/**
 * Created by Anton Zhilenkov on 05/03/2022.
 */
@Preview
@Composable
fun WalletsWidget() {
  val curves = listOf(
    EllipticCurve.Bls12381_G2,
    EllipticCurve.Bls12381_G2_AUG,
    EllipticCurve.Bls12381_G2_POP,
    EllipticCurve.Secp256k1,
    EllipticCurve.Secp256r1,
    EllipticCurve.Ed25519,
  )
  var selectedCurve by rememberSaveable { mutableStateOf(EllipticCurve.Bls12381_G2) }

  ContentWidget(name = "Wallets operations") {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
    ) {
      Spacer(modifier = Modifier.height(8.dp))
      Box {
        ChipGroupSingleSelection(
          curves = curves,
          selectedCurve = selectedCurve,
          onSelectedChanged = { selectedCurve = it },
        )
      }
      Spacer(modifier = Modifier.height(4.dp))
      Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = { create(selectedCurve) },
      ) { Text(text = "Create (curve: ${selectedCurve.name})") }
      Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = ::purge,
      ) { Text(text = "Purge") }
    }
  }
}

private fun create(curve: EllipticCurve) {
  sdk.startSessionWithRunnable(CreateWalletTask(curve)) {}
}

private fun purge() {
  sdk.startSessionWithRunnable(PurgeWalletTask()) {}
}


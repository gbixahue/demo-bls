package com.tangem.demo_bls.ui.widgets.chipGroup

/**
 * Created by Anton Zhilenkov on 05/03/2022.
 */
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.tangem.common.card.EllipticCurve

@Preview(showBackground = true)
@Composable
fun ChipGroupMultiSelection(
  modifier: Modifier = Modifier,
  curves: List<EllipticCurve> = EllipticCurve.values().toList(),
  selectedCurves: List<EllipticCurve> = listOf(EllipticCurve.Secp256k1),
  onSelectedChanged: (EllipticCurve) -> Unit = {},
) {
  Column(modifier = modifier) {
    LazyRow {
      items(curves.size) { item ->
        val curve = curves[item]
        Chip(
          name = curve.name,
          isSelected = selectedCurves.contains(curve),
          value = curve,
          onSelectionChanged = {
            onSelectedChanged(it)
          },
        )
      }
    }
  }
}

@Preview(showBackground = true)
@Composable
fun ChipGroupSingleSelection(
  modifier: Modifier = Modifier,
  curves: List<EllipticCurve> = EllipticCurve.values().toList(),
  selectedCurve: EllipticCurve = EllipticCurve.Secp256k1,
  itemPadding: Dp = 2.dp,
  onSelectedChanged: (EllipticCurve) -> Unit = {},
) {
  Column(modifier = modifier) {
    LazyRow {
      items(curves.size) { item ->
        val curve = curves[item]
        val paddingStart = if (item == 0) 0.dp else itemPadding
        val paddingEnd = if (item == curves.size - 1) 0.dp else itemPadding

        Box(
          modifier = Modifier.padding(paddingStart, 0.dp, paddingEnd, 0.dp)
        ) {
          Chip(
            name = curve.name,
            isSelected = selectedCurve == curve,
            value = curve,
            onSelectionChanged = {
              onSelectedChanged(it)
            },
          )
        }
      }
    }
  }
}
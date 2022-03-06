package com.tangem.demo_bls

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.res.AssetManager
import android.os.Handler
import android.os.Looper
import android.widget.Toast

/**
 * Created by Anton Zhilenkov on 05/03/2022.
 */
fun Context.copyToClipboard(value: Any, label: String = "") {
  val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager ?: return

  val clip: ClipData = ClipData.newPlainText(label, value.toString())
  clipboard.setPrimaryClip(clip)
}

fun Context.getFromClipboard(default: CharSequence? = null): CharSequence? {
  val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager
    ?: return default
  val clipData = clipboard.primaryClip ?: return default
  if (clipData.itemCount == 0) return default

  return clipData.getItemAt(0).text
}

val handler = Handler(Looper.getMainLooper())

fun Context.toast(message: String) {
  handler.post { Toast.makeText(this, message, Toast.LENGTH_SHORT).show() }
}

fun AssetManager.readJsonFileToString(fileName: String): String =
  this.open("$fileName.json").bufferedReader().readText()
package com.tangem.demo_bls

import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.SaverScope
import com.tangem.common.json.MoshiJsonConverter

/**
 * Created by Anton Zhilenkov on 05/03/2022.
 */

typealias ValueCallback<T> = (T) -> Unit

class Utils {

  companion object {

    fun randomInt(from: Int, to: Int): Int {
      return kotlin.random.Random.nextInt(from, to)
    }

    fun randomString(length: Int): String {
      val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
      val randomString = (1..length)
        .map { randomInt(0, charPool.size) }
        .map(charPool::get)
        .joinToString("")
      return randomString
    }
  }
}

open class JsonSaver<T>: Saver<T, String> {
  protected val converter = MoshiJsonConverter.INSTANCE

  override fun SaverScope.save(value: T): String? {
    return converter.toJson(value)
  }

  override fun restore(value: String): T? {
    return converter.fromJson<Any?>(value) as? T
  }
}
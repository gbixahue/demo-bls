package com.tangem.demo_bls.cardSdk

import com.tangem.common.CompletionResult
import com.tangem.common.SuccessResponse
import com.tangem.common.core.CardSession
import com.tangem.common.core.CardSessionRunnable
import com.tangem.common.core.CompletionCallback
import com.tangem.common.core.TangemSdkError
import com.tangem.common.extensions.guard
import com.tangem.operations.sign.SignHashCommand
import com.tangem.operations.sign.SignHashResponse
import com.tangem.operations.wallet.PurgeWalletCommand

/**
 * Created by Anton Zhilenkov on 05/03/2022.
 */
abstract class BaseWalletTask<T>: CardSessionRunnable<T> {
  override fun run(session: CardSession, callback: CompletionCallback<T>) {
    ReadSingleWalletPublicKeyCommand().run(session) {
      when (it) {
        is CompletionResult.Success -> {
          runWith(session, it.data, callback)
        }
        is CompletionResult.Failure -> callback(CompletionResult.Failure(it.error))
      }
    }
  }

  abstract fun runWith(session: CardSession, publicKey: ByteArray, callback: CompletionCallback<T>)
}

class ReadSingleWalletPublicKeyCommand: CardSessionRunnable<ByteArray> {

  override fun run(session: CardSession, callback: CompletionCallback<ByteArray>) {
    val card = session.environment.card.guard {
      callback(CompletionResult.Failure(TangemSdkError.MissingPreflightRead()))
      return
    }
    if (card.wallets.isEmpty()) {
      callback(CompletionResult.Failure(TangemSdkError.WalletNotFound()))
      return
    }
    val wallet = card.wallets[0]
    callback(CompletionResult.Success(wallet.publicKey))
  }
}

class PurgeWalletTask: BaseWalletTask<SuccessResponse>() {
  override fun runWith(session: CardSession, publicKey: ByteArray, callback: CompletionCallback<SuccessResponse>) {
    PurgeWalletCommand(publicKey).run(session, callback)
  }
}

class SignHashTask(
  private val hash: ByteArray
): BaseWalletTask<SignHashResponse>() {

  override fun runWith(session: CardSession, publicKey: ByteArray, callback: CompletionCallback<SignHashResponse>) {
    SignHashCommand(hash, publicKey, null).run(session, callback)
  }
}
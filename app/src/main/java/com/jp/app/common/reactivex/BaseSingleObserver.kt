package com.jp.app.common.reactivex

import com.jp.data.utils.ErrorManager
import io.reactivex.annotations.NonNull
import io.reactivex.observers.DisposableSingleObserver
import retrofit2.Response
import java.util.*

abstract class BaseSingleObserver<T> : DisposableSingleObserver<T>(), ErrorManager.ErrorManagerCallback {

    override fun onError(@NonNull e: Throwable) {
        val errorManager = ErrorManager(this)
        errorManager.handleError(e)
    }

    override fun onErrorManager(response: Response<*>?, error: String?, code: Int?) {
        error?.let {
            if (it.toLowerCase(Locale.ROOT).contains("http") && code == 404) {
                onError(null, null, -1)
            } else {
                onError(response, error, code)
            }
        } ?: run {
            onError(response, error, code)
        }
    }

    protected abstract fun onError(response: Response<*>?, error: String?, code: Int?)
}
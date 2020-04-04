package com.jp.app.helper

import com.jp.app.common.reactivex.BaseCompletableObserver
import com.jp.app.common.reactivex.BaseMaybeObserver
import com.jp.app.common.reactivex.BaseSingleObserver
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response


private val onStartStub: () -> Unit = {}
private val onNextStub: (Any) -> Unit = {}
private val onErrorStub: (response: Response<*>?, error: String?, code: Int?) -> Unit = { _, _, _ -> }
private val onCompleteStub: () -> Unit = {}

fun <T : Any> Single<T>.subscribeSingle(
    onStart: () -> Unit = onStartStub,
    onError: (response: Response<*>?, error: String?, code: Int?) -> Unit = onErrorStub,
    onSuccess: (T) -> Unit = onNextStub
): Disposable {
    return subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(object : BaseSingleObserver<T>() {
            override fun onError(response: Response<*>?, error: String?, code: Int?) {
                onError(response, error, code)
            }

            override fun onSuccess(t: T) {
                onSuccess(t)
            }

            override fun onStart() {
                onStart()
            }
        })
}

fun <T : Any> Maybe<T>.subscribeMaybe(
    onStart: () -> Unit = onStartStub,
    onError: (response: Response<*>?, error: String?, code: Int?) -> Unit = onErrorStub,
    onSuccess: (T) -> Unit = onNextStub,
    onComplete: () -> Unit = onCompleteStub
): Disposable {
    return subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(object : BaseMaybeObserver<T>() {
            override fun onError(response: Response<*>?, error: String?, code: Int?) {
                onError(response, error, code)
            }

            override fun onSuccess(t: T) {
                onSuccess(t)
            }

            override fun onStart() {
                onStart()
            }

            override fun onComplete() {
                onComplete()
            }
        })
}

fun Completable.subscribeCompletable(
    onStart: () -> Unit = onStartStub,
    onError: (response: Response<*>?, error: String?, code: Int?) -> Unit = onErrorStub,
    onComplete: () -> Unit = onCompleteStub
): Disposable {
    return subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(object : BaseCompletableObserver() {

            override fun onError(response: Response<*>?, error: String?, code: Int?) {
                onError(response, error, code)
            }

            override fun onStart() {
                onStart()
            }

            override fun onComplete() {
                onComplete()
            }
        })
}
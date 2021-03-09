package com.jp.app.common.viewModel

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel : ViewModel(), IBaseViewModel {

    private var mCompositeDisposable: CompositeDisposable? = null

    /**
     * on view is destroy clear all the pending calls
     */
    override fun onDestroy() {
        mCompositeDisposable?.dispose()
        removeAllDisposables()
    }


    // =============== Manage Disposable ===========================================================

    fun getCompositeDisposable(): CompositeDisposable? {
        mCompositeDisposable?.isDisposed?.let { isDisposed ->
            if (isDisposed)
                mCompositeDisposable = CompositeDisposable()

        }
        return mCompositeDisposable as CompositeDisposable
    }

    fun addDisposable(disposable: Disposable?) {
        if (disposable != null) {
            mCompositeDisposable?.add(disposable)
        }
    }

    fun removeDisposable(disposable: Disposable?) {
        if (disposable != null) {
            if (!disposable.isDisposed) {
                disposable.dispose()
            }
            if (mCompositeDisposable != null) {
                mCompositeDisposable?.remove(disposable)
            }
        }
    }

    private fun removeAllDisposables() {
        mCompositeDisposable?.clear()
    }

    fun hasDisposables(): Boolean {
        mCompositeDisposable?.size()?.let { size ->
            return size > 0
        }?: run {
            return false
        }

    }

}
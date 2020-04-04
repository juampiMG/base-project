package com.jp.app.common.viewModel

import android.content.DialogInterface
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jp.app.model.AlertDialogModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel : ViewModel(), IBaseViewModel {

    private val mIsLoading = MutableLiveData<Boolean>()

    private val mAlertDialogModelTwoButtons = MutableLiveData<AlertDialogModel>()
    private val mAlertDialogModelOneButton = MutableLiveData<AlertDialogModel>()
    private val mAlertDialogError = MutableLiveData<Int>()
    private val mAlertDialogErrorString = MutableLiveData<String?>()
    private val mDisplayServerErrorToast = MutableLiveData<Boolean>()
    private val mDisplayCheckOkToast = MutableLiveData<Int>()
    private var mCompositeDisposable: CompositeDisposable? = null


    // =============== IBaseViewModel ==============================================================

    /**
     * on view is destroy clear all the pending calls
     */
    override fun onDestroy() {
        if (mCompositeDisposable != null) mCompositeDisposable?.dispose()
    }

    override fun showAlertDialogTwoButtons(): MutableLiveData<AlertDialogModel> {
        return mAlertDialogModelTwoButtons
    }

    override fun showAlertDialogOneButton(): MutableLiveData<AlertDialogModel> {
        return mAlertDialogModelOneButton
    }

    override fun showErrorMessageDialog(): MutableLiveData<Int> {
        return mAlertDialogError
    }


    override fun showErrorMessageDialogString(): MutableLiveData<String?> {
        return mAlertDialogErrorString
    }

    override fun showDisplayServerErrorToast(): MutableLiveData<Boolean> {
        return mDisplayServerErrorToast
    }

    override fun showCheckOkAlertToast(): MutableLiveData<Int> {
        return mDisplayCheckOkToast
    }

    override fun showIsLoading(): MutableLiveData<Boolean> {
        return mIsLoading
    }

    fun isLoading(visibility: Boolean) {
        mIsLoading.value = visibility
    }

    fun setAlertDialogModelTwoButtons(
        title: Int,
        message: Int,
        leftButtonTitle: Int,
        leftButtonListener: DialogInterface.OnClickListener?,
        rightButtonTitle: Int,
        rightButtonListener: DialogInterface.OnClickListener?
    ) {
        val alert = AlertDialogModel(
            title,
            message,
            ArrayList(),
            leftButtonTitle,
            leftButtonListener,
            rightButtonTitle,
            rightButtonListener
        )
        mAlertDialogModelTwoButtons.value = alert
    }


    fun setAlertDialogModelTwoButtons(
        title: Int,
        message: Int,
        messageArg: List<Pair<Int, String>>,
        leftButtonTitle: Int,
        leftButtonListener: DialogInterface.OnClickListener?,
        rightButtonTitle: Int,
        rightButtonListener: DialogInterface.OnClickListener?
    ) {
        val alert = AlertDialogModel(
            title,
            message,
            messageArg,
            leftButtonTitle,
            leftButtonListener,
            rightButtonTitle,
            rightButtonListener
        )
        mAlertDialogModelTwoButtons.value = alert
    }

    fun setAlertDialogModelOneButton(
        title: Int,
        message: Int,
        messageArg: List<Pair<Int, String?>>,
        rightButtonTitle: Int,
        rightButtonListener: DialogInterface.OnClickListener?
    ) {
        val alert = AlertDialogModel(title, message, messageArg, 0, null, rightButtonTitle, rightButtonListener)
        mAlertDialogModelOneButton.value = alert
    }

    fun setAlertDialogModelOneButton(
        title: Int,
        message: Int,
        rightButtonTitle: Int,
        rightButtonListener: DialogInterface.OnClickListener?
    ) {
        val alert = AlertDialogModel(title, message, ArrayList(), 0, null, rightButtonTitle, rightButtonListener)
        mAlertDialogModelOneButton.value = alert
    }

    fun setAlertDialogError(error: Int) {
        mAlertDialogError.value = error
    }

    fun setAlertDialogError(error: String?) {
        mAlertDialogErrorString.value = error
    }

    fun setDisplayServerErrorToast() {
        mDisplayServerErrorToast.value = true
        mDisplayServerErrorToast.postValue(null)
    }

    fun setDisplayCheckOkToast(alertResource: Int) {
        mDisplayCheckOkToast.value = alertResource
        mDisplayCheckOkToast.postValue(null)
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
        if (disposable != null && mCompositeDisposable != null) {
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

    fun removeAllDisposables() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable?.clear()
        }
    }

    fun hasDisposables(): Boolean {
        mCompositeDisposable?.size()?.let { size ->
            return size > 0
        }?: run {
            return false
        }

    }

}

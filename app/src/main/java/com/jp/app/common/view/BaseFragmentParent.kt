package com.jp.app.common.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.jp.app.common.viewModel.IBaseViewModel
import com.jp.app.model.AlertDialogModel


/**
 * Baser Fragment Parent with childs
 */
abstract class BaseFragmentParent<TViewModel : IBaseViewModel,TCallback: IBaseFragmentCallback> : BaseFragment<TViewModel, TCallback>(), IBaseFragmentChildCallback {

    protected var currentFragment: Fragment? = null


    override fun isLoading(loading: Boolean) {
        mCallback.isLoading(loading)
    }

    override fun alertDialogTwoButtons(alertDialogModel: AlertDialogModel) {
        mCallback.alertDialogTwoButtons(alertDialogModel)
    }

    override fun alertDialogOneButton(alertDialogModel: AlertDialogModel) {
        mCallback.alertDialogOneButton(alertDialogModel)
    }

    override fun errorMessageDialog(descriptionError: Int) {
        mCallback.errorMessageDialog(descriptionError)
    }

    override fun errorMessageDialog(descriptionError: String?) {
        mCallback.errorMessageDialog(descriptionError)
    }

    override fun errorMessageToast(descriptionError: Throwable) {
        mCallback.errorMessageToast(descriptionError)

    }

    override fun errorServerMessageToast() {
        mCallback.errorServerMessageToast()
    }

    abstract fun setOptionLoaded()

}

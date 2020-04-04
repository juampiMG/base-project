package com.jp.app.common.view

import com.jp.app.model.AlertDialogModel


interface IBaseFragmentCallback {

    fun isLoading(loading: Boolean)
    fun alertDialogTwoButtons(alertDialogModel: AlertDialogModel)
    fun alertDialogOneButton(alertDialogModel: AlertDialogModel)
    fun errorMessageDialog(descriptionError: Int)
    fun errorMessageDialog(descriptionError: String?)
    fun errorMessageToast(descriptionError: Throwable)
    fun errorServerMessageToast()
}

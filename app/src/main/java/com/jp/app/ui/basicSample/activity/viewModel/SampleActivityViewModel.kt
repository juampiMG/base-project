package com.jp.app.ui.basicSample.activity.viewModel

import androidx.lifecycle.MutableLiveData
import com.jp.app.common.viewModel.BaseActivityViewModel
import com.jp.app.helper.subscribeSingle
import com.jp.domain.interactor.IGetSampleUseCase
import javax.inject.Inject


class SampleActivityViewModel
@Inject
constructor() : BaseActivityViewModel(), ISampleActivityViewModel {

    @Inject
    lateinit var mGetSampleUseCase: IGetSampleUseCase

    private var showToast = MutableLiveData<Boolean>()

    override fun loadServerGame() {
        addDisposable(
                mGetSampleUseCase.execute().subscribeSingle(
                        onStart = {
                            isLoading(true)
                        },
                        onSuccess = {
                            isLoading(false)
                            showToast.value = true
                        },
                        onError = { _, _, _ ->
                            isLoading(false)
                        }
                )
        )
    }

    override fun showToast(): MutableLiveData<Boolean> {
        return showToast
    }

}
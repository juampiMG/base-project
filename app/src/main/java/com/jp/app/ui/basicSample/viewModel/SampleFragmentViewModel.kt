package com.jp.app.ui.basicSample.viewModel

import androidx.lifecycle.MutableLiveData
import com.jp.app.common.viewModel.BaseFragmentViewModel
import com.jp.app.helper.subscribeSingle
import com.jp.domain.interactor.IGetSampleUseCase
import javax.inject.Inject

class SampleFragmentViewModel
@Inject
constructor() : BaseFragmentViewModel(), ISampleFragmentViewModel {

    private var mLoadGame = MutableLiveData<Boolean>()

    @Inject
    lateinit var mGetSampleUseCase: IGetSampleUseCase

    override fun loadData() {
        addDisposable(
                mGetSampleUseCase.execute().subscribeSingle(
                        onStart = {
                            isLoading(true)
                        },
                        onSuccess = {
                            isLoading(false)
                            mLoadGame.value = true
                        },
                        onError = { _, _, _ ->
                            isLoading(false)
                        }
                )
        )
    }

    override fun loadGame():MutableLiveData<Boolean>{
       return mLoadGame
    }

}

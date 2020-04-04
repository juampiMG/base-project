package com.jp.domain.interactor

import com.jp.domain.model.SampleDomain
import io.reactivex.Single

interface IGetSampleUseCase {

    fun execute(): Single<SampleDomain>
}
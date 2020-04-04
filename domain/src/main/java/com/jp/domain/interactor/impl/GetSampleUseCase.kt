package com.jp.domain.interactor.impl

import com.jp.domain.interactor.IGetSampleUseCase
import com.jp.domain.model.SampleDomain
import com.jp.domain.repository.ISampleRepository
import io.reactivex.Single
import javax.inject.Inject

class GetSampleUseCase
@Inject
constructor() : IGetSampleUseCase {

    @Inject
    internal lateinit var mSampleRepository: ISampleRepository

    override fun execute(): Single<SampleDomain> {
        return mSampleRepository.getSamples()
    }
}
package com.jp.data.repository

import com.jp.data.entity.mapper.SampleEntityMapper
import com.jp.data.network.gateway.IAppGateway
import com.jp.domain.model.SampleDomain
import com.jp.domain.repository.ISampleRepository
import io.reactivex.Single
import javax.inject.Inject

class SampleRepository
@Inject
constructor(private val mGateway: IAppGateway) : ISampleRepository {
    @Inject
    internal lateinit var mSampleEntityMapper: SampleEntityMapper

    override fun getSamples(): Single<SampleDomain> {
        return mGateway.getSamples().flatMap { resultEntities -> Single.just(mSampleEntityMapper.transform(resultEntities)) }
    }
}
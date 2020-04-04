package com.jp.data.network.gateway

import com.jp.data.entity.sample.SampleEntity
import io.reactivex.Single


interface IAppGateway {
    fun getSamples(): Single<SampleEntity>
}
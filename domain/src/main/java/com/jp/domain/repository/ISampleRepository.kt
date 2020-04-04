package com.jp.domain.repository

import com.jp.domain.model.SampleDomain
import io.reactivex.Single

interface ISampleRepository {
     fun getSamples(): Single<SampleDomain>
}
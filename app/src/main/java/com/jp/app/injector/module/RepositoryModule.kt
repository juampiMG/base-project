package com.jp.app.injector.module

import com.jp.data.repository.SampleRepository
import com.jp.domain.repository.ISampleRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class RepositoryModule {
    @Binds
    @Singleton
    internal abstract fun sampleRepository(repository: SampleRepository): ISampleRepository
}

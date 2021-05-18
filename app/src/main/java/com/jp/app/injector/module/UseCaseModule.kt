package com.jp.app.injector.module

import com.jp.domain.interactor.IGetSampleUseCase
import com.jp.domain.interactor.impl.GetSampleUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class UseCaseModule {
    @Binds
    @Singleton
    internal abstract fun getSampleUseCase(useCase: GetSampleUseCase): IGetSampleUseCase
}

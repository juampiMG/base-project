package com.jp.app.injector.module

import com.jp.domain.interactor.IGetSampleUseCase
import com.jp.domain.interactor.impl.GetSampleUseCase
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class UseCaseModule {
    @Binds
    @Singleton
    internal abstract fun getSampleUseCase(useCase: GetSampleUseCase): IGetSampleUseCase
}

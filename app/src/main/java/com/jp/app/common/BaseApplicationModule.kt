package com.jp.app.common

import android.app.Application
import android.content.Context
import com.jp.app.SampleApplication
import com.jp.app.injector.module.*
import dagger.Binds
import dagger.Module
import javax.inject.Singleton


@Module(
        includes = [
            RepositoryModule::class,
            UseCaseModule::class,
            NetworkModule::class,
            PreferencesModule::class
        ]
)
abstract class BaseApplicationModule {
    @Binds
    @Singleton
    internal abstract fun application(application: SampleApplication): Application

    @Binds
    @Singleton
    internal abstract fun applicationContext(application: Application): Context

}
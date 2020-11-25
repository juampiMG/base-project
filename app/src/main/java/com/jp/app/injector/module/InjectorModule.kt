package com.jp.app.injector.module

import com.jp.app.injector.scope.PerActivity
import com.jp.app.ui.basicSample.SampleActivity
import com.jp.app.ui.basicSample.SampleActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class InjectorModule {
    @PerActivity
    @ContributesAndroidInjector(modules = [SampleActivityModule::class])
    internal abstract fun sampleActivity(): SampleActivity
}

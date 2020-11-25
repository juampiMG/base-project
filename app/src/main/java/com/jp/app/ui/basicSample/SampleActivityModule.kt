package com.jp.app.ui.basicSample

import androidx.fragment.app.FragmentActivity
import com.jp.app.common.BaseActivityModule
import com.jp.app.injector.scope.PerActivity
import com.jp.app.injector.scope.PerFragment
import com.jp.app.ui.basicSample.view.BasicSampleFragment
import com.jp.app.ui.basicSample.view.BasicSampleFragmentModule
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector


/**
 * Module of activity with a fragment [SampleActivity]
 */
@Module(includes = [BaseActivityModule::class])
abstract class SampleActivityModule {
    @Binds
    @PerActivity
    internal abstract fun activity(activity: SampleActivity): FragmentActivity


    @Binds
    @PerActivity
    internal abstract fun parentFragmentCallback(activity: SampleActivity): BasicSampleFragment.FragmentCallback

    @PerFragment
    @ContributesAndroidInjector(modules = [BasicSampleFragmentModule::class])
    internal abstract fun parentFragmentInjector(): BasicSampleFragment


}

package com.jp.app.ui.sample

import androidx.fragment.app.FragmentActivity
import com.jp.app.common.BaseActivityModule
import com.jp.app.injector.scope.PerActivity
import com.jp.app.injector.scope.PerFragment
import com.jp.app.ui.sample.view.SampleFragment
import com.jp.app.ui.sample.view.SampleFragmentModule
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector


/**
 * Module of activity with a fragment [SampleActivity] which has  children
 */
@Module(includes = [BaseActivityModule::class])
abstract class SampleActivityModule {
    @Binds
    @PerActivity
    internal abstract fun activity(activity: SampleActivity): FragmentActivity


    /**
     * Parent Fragment
     */
    @Binds
    @PerActivity
    internal abstract fun parentFragmentCallback(activity: SampleActivity): SampleFragment.FragmentCallback

    @PerFragment
    @ContributesAndroidInjector(modules = [SampleFragmentModule::class])
    internal abstract fun parentFragmentInjector(): SampleFragment


}

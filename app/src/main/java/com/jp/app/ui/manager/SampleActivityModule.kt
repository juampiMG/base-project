package com.jp.app.ui.manager

import androidx.fragment.app.FragmentActivity
import com.jp.app.common.BaseActivityModule
import com.jp.app.injector.scope.PerActivity
import com.jp.app.injector.scope.PerFragment
import com.jp.app.ui.manager.SampleActivity
import com.jp.app.ui.manager.view.ManagerFragment
import com.jp.app.ui.manager.view.ManagerFragmentModule
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
    internal abstract fun parentFragmentCallback(activity: SampleActivity): ManagerFragment.FragmentCallback

    @PerFragment
    @ContributesAndroidInjector(modules = [ManagerFragmentModule::class])
    internal abstract fun parentFragmentInjector(): ManagerFragment


}

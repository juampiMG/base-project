package com.jp.app.ui.manager.view

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jp.app.common.BaseFragmentModule
import com.jp.app.common.ViewModelProviderFactory
import com.jp.app.injector.scope.PerChildFragment
import com.jp.app.injector.scope.PerFragment
import com.jp.app.ui.motionSample.view.MotionFragment
import com.jp.app.ui.motionSample.view.MotionFragmentModule
import com.jp.app.ui.manager.viewModel.IManagerViewModel
import com.jp.app.ui.manager.viewModel.ManagerViewModel
import com.jp.app.ui.menu.view.MenuFragment
import com.jp.app.ui.menu.view.MenuFragmentModule
import com.jp.app.ui.basicSample.view.BasicSampleFragment
import com.jp.app.ui.basicSample.view.BasicSampleFragmentModule
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import javax.inject.Named

/**
 * Parent of Fragment Children
 *
 * The parent declaration must include @Inject constructor()
 * Ex:
 * class ParentFragment @Inject constructor(): BaseFragment<ParentViewModel, ParentFragment.FragmentCallback>(),
 * ChildAFragment.FragmentCallback,
 * ChildBFragment.FragmentCallback {...}
 *
 */
@Module(includes = [BaseFragmentModule::class])
abstract class ManagerFragmentModule {

    @Binds
    @Named(BaseFragmentModule.FRAGMENT)
    @PerFragment
    internal abstract fun fragment(fragment: ManagerFragment): Fragment

    @Module
    companion object {
        /**
         * Provides the proper ViewModel to the current Child Fragment
         */
        @JvmStatic
        @Provides
        fun provideViewModel(fragment: ManagerFragment): IManagerViewModel {
            return ViewModelProvider(fragment, ViewModelProviderFactory(ManagerViewModel()))
                    .get(ManagerViewModel::class.java)
        }
    }
    /**
     * Children of [ManagerFragment]
     */

    @Binds
    @PerFragment
    internal abstract fun menuFragmentCallback(fragment: ManagerFragment): MenuFragment.FragmentCallback

    @PerChildFragment
    @ContributesAndroidInjector(modules = [MenuFragmentModule::class])
    internal abstract fun menuFragmentInjector(): MenuFragment


    @Binds
    @PerFragment
    internal abstract fun sampleFragmentCallback(fragment: ManagerFragment): BasicSampleFragment.FragmentCallback

    @PerChildFragment
    @ContributesAndroidInjector(modules = [BasicSampleFragmentModule::class])
    internal abstract fun sampleFragmentInjector(): BasicSampleFragment


    @Binds
    @PerFragment
    internal abstract fun actAPIFragmentCallback(fragment: ManagerFragment): MotionFragment.FragmentCallback

    @PerChildFragment
    @ContributesAndroidInjector(modules = [MotionFragmentModule::class])
    internal abstract fun actAPIFragmentInjector(): MotionFragment

}
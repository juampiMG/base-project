package com.jp.app.ui.basicSample

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.jp.app.common.BaseActivityModule
import com.jp.app.common.BaseFragmentModule
import com.jp.app.common.viewModel.ViewModelProviderFactory
import com.jp.app.injector.scope.PerActivity
import com.jp.app.injector.scope.PerFragment
import com.jp.app.ui.basicSample.activity.view.SampleActivity
import com.jp.app.ui.basicSample.activity.viewModel.ISampleActivityViewModel
import com.jp.app.ui.basicSample.activity.viewModel.SampleActivityViewModel
import com.jp.app.ui.basicSample.view.SampleFragment
import com.jp.app.ui.basicSample.viewModel.ISampleFragmentViewModel
import com.jp.app.ui.basicSample.viewModel.SampleFragmentViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import javax.inject.Named


/**
 * [SampleActivity] Module Activity Dagger Configuration
 */
@Module(includes = [BaseActivityModule::class])
abstract class SampleActivityModule {

    /**
     * Bind the current Activity
     */
    @Binds
    @PerActivity
    internal abstract fun activity(activity: SampleActivity): FragmentActivity

    /**
     * Bind the current Activity View Model
     */
    @Binds
    @PerActivity
    internal abstract fun sampleActivityViewModel(viewModel: SampleActivityViewModel): ISampleActivityViewModel

    /**
     * Bind the Fragment Callback
     */
    @Binds
    @PerActivity
    internal abstract fun parentFragmentCallback(activity: SampleActivity): SampleFragment.FragmentCallback

    /**
     * Inject Fragment
     */
    @PerFragment
    @ContributesAndroidInjector(modules = [SampleFragmentModule::class])
    internal abstract fun parentFragmentInjector(): SampleFragment

}


/**
 * [SampleFragment] Module Dagger Configuration
 */
@Module(includes = [BaseFragmentModule::class])
abstract class SampleFragmentModule {
    @Binds
    @Named(BaseFragmentModule.FRAGMENT)
    @PerFragment
    internal abstract fun fragment(fragment: SampleFragment): Fragment

    @Module
    companion object {
        /**
         * Provides the proper ViewModel to the current Fragment
         */
        @JvmStatic
        @Provides
        fun provideViewModel(fragment: SampleFragment): ISampleFragmentViewModel {
            return ViewModelProvider(fragment, ViewModelProviderFactory(SampleFragmentViewModel()))
                    .get(SampleFragmentViewModel::class.java)
        }
    }
}

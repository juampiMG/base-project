package com.jp.app.ui.basicSample

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jp.app.common.BaseFragmentModule
import com.jp.app.common.viewModel.ViewModelProviderFactory
import com.jp.app.ui.basicSample.activity.view.SampleActivity
import com.jp.app.ui.basicSample.activity.viewModel.ISampleActivityViewModel
import com.jp.app.ui.basicSample.activity.viewModel.SampleActivityViewModel
import com.jp.app.ui.basicSample.view.SampleFragment
import com.jp.app.ui.basicSample.viewModel.ISampleFragmentViewModel
import com.jp.app.ui.basicSample.viewModel.SampleFragmentViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.FragmentComponent
import javax.inject.Named


/**
 * [SampleActivity] Module Activity Dagger Configuration
 */
@InstallIn(ActivityComponent::class)
@Module
abstract class SampleActivityModule {

    /**
     * Bind the current Activity View Model
     */
    @Binds
    internal abstract fun sampleActivityViewModel(viewModel: SampleActivityViewModel): ISampleActivityViewModel

    /**
     * Bind the Fragment Callback
     */
    @Binds
    internal abstract fun parentFragmentCallback(activity: SampleActivity): SampleFragment.FragmentCallback

}


/**
 * [SampleFragment] Module Dagger Configuration
 */
@InstallIn(FragmentComponent::class)
@Module
abstract class SampleFragmentModule {


    @Binds
    @Named(BaseFragmentModule.FRAGMENT)
    internal abstract fun fragment(fragment: SampleFragment): Fragment

    companion object {
        /**
         * Provides the proper ViewModel to the current Fragment
         */
        @Provides
        fun provideViewModel(fragment: SampleFragment): ISampleFragmentViewModel {
            return ViewModelProvider(fragment, ViewModelProviderFactory(SampleFragmentViewModel()))
                .get(SampleFragmentViewModel::class.java)
        }
    }
}

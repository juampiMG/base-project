package com.jp.app.ui.sample.view

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.jp.app.common.BaseFragmentModule
import com.jp.app.common.ViewModelProviderFactory
import com.jp.app.injector.scope.PerFragment
import com.jp.app.ui.sample.viewModel.ISampleViewModel
import com.jp.app.ui.sample.viewModel.SampleViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Named


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
        fun provideViewModel(fragment: SampleFragment): ISampleViewModel {
            return ViewModelProviders.of(fragment, ViewModelProviderFactory(SampleViewModel())).get(SampleViewModel::class.java)
        }
    }
}


package com.jp.app.ui.basicSample.view

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jp.app.common.BaseFragmentModule
import com.jp.app.common.ViewModelProviderFactory
import com.jp.app.injector.scope.PerFragment
import com.jp.app.ui.basicSample.viewModel.BasicSampleViewModel
import com.jp.app.ui.basicSample.viewModel.IBasicSampleViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Named


@Module(includes = [BaseFragmentModule::class])
abstract class BasicSampleFragmentModule {
    @Binds
    @Named(BaseFragmentModule.FRAGMENT)
    @PerFragment
    internal abstract fun fragment(fragment: BasicSampleFragment): Fragment

    @Module
    companion object {
        /**
         * Provides the proper ViewModel to the current Child Fragment
         */
        @JvmStatic
        @Provides
        fun provideViewModel(fragment: BasicSampleFragment): IBasicSampleViewModel {
            return ViewModelProvider(fragment, ViewModelProviderFactory(BasicSampleViewModel()))
                    .get(BasicSampleViewModel::class.java)
        }
    }
}


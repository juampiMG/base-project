package com.jp.app.ui.basicSample.view

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jp.app.common.BaseFragmentChildModule
import com.jp.app.common.BaseFragmentChildModule.FRAGMENT_CHILD
import com.jp.app.common.ViewModelProviderFactory
import com.jp.app.common.view.IBaseFragmentCallback
import com.jp.app.injector.scope.PerChildFragment
import com.jp.app.ui.basicSample.viewModel.IBasicSampleViewModel
import com.jp.app.ui.basicSample.viewModel.BasicSampleViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Named


@Module(includes = [BaseFragmentChildModule::class])
abstract class BasicSampleFragmentModule {
    @Binds
    @Named(FRAGMENT_CHILD)
    @PerChildFragment
    internal abstract fun fragment(fragment: BasicSampleFragment): Fragment

    @Module
    companion object {

        /**
         * Provides the IBaseFragmentCallback to the current Child Fragment
         */
        @JvmStatic
        @Provides
        fun provideCallback(fragment: BasicSampleFragment): IBaseFragmentCallback {
            return fragment
        }

        /**
         * Provides the proper ViewModel to the current Child Fragment
         */
        @JvmStatic
        @Provides
        fun provideViewModel(fragment: BasicSampleFragment): IBasicSampleViewModel {
            return ViewModelProvider(fragment, ViewModelProviderFactory(BasicSampleViewModel())).get(BasicSampleViewModel::class.java)
        }
    }
}


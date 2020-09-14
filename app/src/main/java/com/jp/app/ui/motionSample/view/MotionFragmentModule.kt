package com.jp.app.ui.motionSample.view

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jp.app.common.BaseFragmentChildModule
import com.jp.app.common.BaseFragmentChildModule.FRAGMENT_CHILD
import com.jp.app.common.ViewModelProviderFactory
import com.jp.app.common.view.IBaseFragmentCallback
import com.jp.app.injector.scope.PerChildFragment
import com.jp.app.ui.motionSample.viewModel.MotionViewModel
import com.jp.app.ui.motionSample.viewModel.IMotionViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module(includes = [BaseFragmentChildModule::class])
abstract class MotionFragmentModule {

    @Binds
    @Named(FRAGMENT_CHILD)
    @PerChildFragment
    internal abstract fun fragment(fragment: MotionFragment): Fragment

    @Module
    companion object {

        /**
         * Provides the IBaseFragmentCallback to the current Child Fragment
         */
        @JvmStatic
        @Provides
        fun provideCallback(fragment: MotionFragment): IBaseFragmentCallback {
            return fragment
        }

        /**
         * Provides the proper ViewModel to the current Child Fragment
         */
        @JvmStatic
        @Provides
        fun provideViewModel(fragment: MotionFragment): IMotionViewModel {
            return ViewModelProvider(fragment, ViewModelProviderFactory(MotionViewModel())).get(MotionViewModel::class.java)
        }
    }

}
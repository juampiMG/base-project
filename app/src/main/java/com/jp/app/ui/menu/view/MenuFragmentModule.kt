package com.jp.app.ui.menu.view

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jp.app.common.BaseFragmentChildModule
import com.jp.app.common.BaseFragmentChildModule.FRAGMENT_CHILD
import com.jp.app.common.ViewModelProviderFactory
import com.jp.app.common.view.IBaseFragmentCallback
import com.jp.app.injector.scope.PerChildFragment
import com.jp.app.ui.menu.viewModel.IMenuViewModel
import com.jp.app.ui.menu.viewModel.MenuViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module(includes = [BaseFragmentChildModule::class])
abstract class MenuFragmentModule {

    @Binds
    @Named(FRAGMENT_CHILD)
    @PerChildFragment
    internal abstract fun fragment(fragment: MenuFragment): Fragment

    @Module
    companion object {

        /**
         * Provides the IBaseFragmentCallback to the current Child Fragment
         */
        @JvmStatic
        @Provides
        fun provideCallback(fragment: MenuFragment): IBaseFragmentCallback {
            return fragment
        }

        /**
         * Provides the proper ViewModel to the current Child Fragment
         */
        @JvmStatic
        @Provides
        fun provideViewModel(fragment: MenuFragment): IMenuViewModel {
            return ViewModelProvider(fragment, ViewModelProviderFactory(MenuViewModel())).get(MenuViewModel::class.java)
        }
    }
}
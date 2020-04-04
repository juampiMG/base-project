package com.jp.app.common

import androidx.fragment.app.Fragment
import dagger.Module

/**
 * Provides base fragment dependencies. This must be included in all fragment modules, which must
 * provide a concrete implementation of [Fragment].
 */
@Module
object BaseFragmentChildModule {
    const val FRAGMENT_CHILD = "BaseFragmentChildModule.fragmentChild"

}

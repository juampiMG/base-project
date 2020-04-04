package com.jp.app.common

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.jp.app.helper.DialogHelper
import com.jp.app.helper.NavigationHelper
import com.jp.app.injector.scope.PerActivity
import dagger.Module
import dagger.Provides


@Module
object BaseActivityModule {

    @JvmStatic
    @Provides
    @PerActivity
    internal fun activityExtras(activity: FragmentActivity): Bundle {
        return activity.intent.extras?.let { it } ?: run { Bundle() }
    }

    @JvmStatic
    @Provides
    @PerActivity
    internal fun activityFragmentManager(activity: FragmentActivity): FragmentManager {
        return activity.supportFragmentManager
    }

    @JvmStatic
    @Provides
    @PerActivity
    internal fun dialogHelper(activity: FragmentActivity, fragmentManager: FragmentManager): DialogHelper {
        return DialogHelper(activity, fragmentManager)
    }

    @JvmStatic
    @Provides
    @PerActivity
    internal fun navigationHelper(activity: FragmentActivity, extras: Bundle): NavigationHelper {
        return NavigationHelper(activity, extras)
    }

}
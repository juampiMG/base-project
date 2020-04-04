package com.jp.app

import android.app.Activity
import android.os.Build
import androidx.multidex.MultiDexApplication
import androidx.appcompat.app.AppCompatDelegate
import com.jp.app.injector.component.DaggerApplicationComponent
import com.jp.data.network.gateway.retrofit.authenticator.IRefreshAuthenticator
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.DaggerApplication
import io.reactivex.Single
import javax.inject.Inject

class SampleApplication :  DaggerApplication(), HasActivityInjector, IRefreshAuthenticator {

    @Inject
    internal lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        initCompatVector()
    }

    // =============== HasActivityInjector =========================================================

    // =============== HasActivityInjector =========================================================

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val component = DaggerApplicationComponent.builder().application(this).build()
        component.inject(this)

        return component
    }

    override fun activityInjector(): DispatchingAndroidInjector<Activity>? {
        return activityInjector
    }

    // =============== Support methods =============================================================

    private fun initCompatVector() {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }

    override fun userOauthRefreshedBearerToken(): Single<String> {
        return Single.just("Used to log again the user and get the proper Token")
    }

    override fun logOut() {
    }

}

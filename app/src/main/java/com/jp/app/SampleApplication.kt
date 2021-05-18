package com.jp.app

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.jp.data.network.gateway.retrofit.authenticator.IRefreshAuthenticator
import dagger.hilt.android.HiltAndroidApp
import io.reactivex.Single

@HiltAndroidApp
class SampleApplication : Application(), IRefreshAuthenticator {

    override fun onCreate() {
        super.onCreate()
        initCompatVector()
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

package com.jp.app.ui.sample

import android.os.Bundle
import com.jp.app.R
import com.jp.app.common.BaseActivity
import com.jp.app.ui.sample.view.SampleFragment
import com.jp.app.utils.NavigationUtils

/**
 * Remember to add activity to the AndroidManifest.xml and to the InjectorModule.kt
 */
class SampleActivity : BaseActivity(),
        SampleFragment.FragmentCallback {

    override fun getLayoutId(): Int {
        return R.layout.generic_activity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            mCurrentFragment = SampleFragment.newInstance(mExtras)
            NavigationUtils.navigateToFragment(
                    activity = this,
                    supportFragmentManager = this.supportFragmentManager,
                    fragment = mCurrentFragment,
                    contentFrame = R.id.content,
                    addToBackStack = false
            )
        } else {
            supportFragmentManager.findFragmentById(R.id.content)?.let {
                mCurrentFragment = it
            }
        }

        setViews()

    }

    private fun setViews() {

    }
}
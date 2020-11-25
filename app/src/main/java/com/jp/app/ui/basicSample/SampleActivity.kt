package com.jp.app.ui.basicSample

import android.os.Bundle
import com.jp.app.R
import com.jp.app.common.BaseActivity
import com.jp.app.ui.basicSample.view.BasicSampleFragment

/**
 * Remember to add activity to the AndroidManifest.xml and to the InjectorModule.kt
 */
class SampleActivity : BaseActivity(),
        BasicSampleFragment.FragmentCallback {

    override fun getLayoutId(): Int {
        return R.layout.generic_activity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            mCurrentFragment = BasicSampleFragment.newInstance(mExtras)
            loadFragment(addToBackStack = false)
        } else {
            supportFragmentManager.findFragmentById(R.id.content)?.let {
                mCurrentFragment = it
                loadFragment(addToBackStack = false)
            }
        }

    }

}
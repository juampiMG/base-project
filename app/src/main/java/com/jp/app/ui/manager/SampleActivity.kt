package com.jp.app.ui.manager

import android.os.Bundle
import com.jp.app.R
import com.jp.app.common.BaseActivity
import com.jp.app.ui.manager.view.ManagerFragment

/**
 * Remember to add activity to the AndroidManifest.xml and to the InjectorModule.kt
 */
class SampleActivity : BaseActivity(),
        ManagerFragment.FragmentCallback {

    override fun getLayoutId(): Int {
        return R.layout.generic_activity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            mCurrentFragment = ManagerFragment.newInstance(mExtras)
            loadFragment(addToBackStack = false)
        } else {
            supportFragmentManager.findFragmentById(R.id.content)?.let {
                mCurrentFragment = it
                loadFragment(addToBackStack = false)
            }
        }

    }

}
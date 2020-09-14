package com.jp.app.ui.manager.view

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import com.jp.app.R
import com.jp.app.common.view.BaseFragmentChild
import com.jp.app.common.view.BaseFragmentParent
import com.jp.app.common.view.IBaseFragmentCallback
import com.jp.app.ui.motionSample.view.MotionFragment
import com.jp.app.ui.manager.viewModel.ManagerViewModel
import com.jp.app.ui.menu.view.MenuFragment
import com.jp.app.ui.childSample.view.ChildSampleFragment
import com.jp.app.utils.NavigationUtils
import javax.inject.Inject


/**
 * Parent Fragment Manager
 */
class ManagerFragment @Inject constructor() : BaseFragmentParent<ManagerViewModel, ManagerFragment.FragmentCallback>(),
        MenuFragment.FragmentCallback,
        ChildSampleFragment.FragmentCallback,
        MotionFragment.FragmentCallback {

    private lateinit var mOptionLoaded: OPTIONS

    override fun getLayoutId(): Int {
        return R.layout.manager_fragment
    }

    enum class OPTIONS {
        MENU, SAMPLE, ACT_API
    }

    interface FragmentCallback : IBaseFragmentCallback {
    }


    override fun getFragmentTitle(): String {
        return "ManagerFragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currentChildFragment = if (savedInstanceState == null) {
            MenuFragment.newInstance(Bundle())
        } else {
            childFragmentManager.findFragmentById(R.id.content)
        }
        NavigationUtils.navigateToFragment(
                activity = activity,
                supportFragmentManager = childFragmentManager,
                fragment = currentChildFragment,
                contentFrame = R.id.content,
                addToBackStack = false)
        setOptionLoaded()
    }

    override fun onViewLoaded(savedInstanceState: Bundle?, view: View?) {
        super.onViewLoaded(savedInstanceState, view)
        view?.isFocusableInTouchMode = true
        view?.requestFocus()
        view?.setOnKeyListener { _, keyCode, event ->
            if (event.action != KeyEvent.ACTION_DOWN) true
            else {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    when (mOptionLoaded) {
                        OPTIONS.MENU -> mActivity.onBackPressed()
                        else -> {
                            mChildSupportFragment.popBackStackImmediate()
                            currentFragment = mChildSupportFragment.findFragmentById(R.id.content)
                            setOptionLoaded()
                        }
                    }
                    true
                } else false
            }
        }
    }

    override fun subscribeToLiveData() {

    }

    override fun setUpViews() {

    }

    override fun applyRTLChanges() {
    }


    override fun setOptionLoaded() {
        when (currentChildFragment) {
            is MenuFragment -> mOptionLoaded = OPTIONS.MENU
            is ChildSampleFragment -> mOptionLoaded = OPTIONS.SAMPLE
            is MotionFragment -> mOptionLoaded = OPTIONS.ACT_API
        }
    }

    // ========================= MenuFragmentCallback ==============================================

    override fun loadOption(option: OPTIONS) {
        when (option) {
            OPTIONS.SAMPLE -> {
                currentChildFragment = ChildSampleFragment.newInstance(Bundle())
            }
            OPTIONS.ACT_API -> {
                currentChildFragment = MotionFragment.newInstance(Bundle())
            }
        }
        setOptionLoaded()
        NavigationUtils.navigateToFragment(
                activity = activity,
                supportFragmentManager = childFragmentManager,
                fragment = currentChildFragment,
                contentFrame = R.id.content,
                addToBackStack = true)
    }


    // ========================= IBaseFragmentChildCallback ========================================

    override fun childBackPressed() {
        childFragmentManager.popBackStack()
    }

    override fun setCurrentFragmentLoad(fragment: BaseFragmentChild<*, *>) {
        currentChildFragment = fragment
    }

    companion object {
        fun newInstance(bundle: Bundle?) = ManagerFragment().apply {
            arguments = bundle ?: Bundle()
        }
    }


}
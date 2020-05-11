package com.jp.app.ui.menu.view

import android.os.Bundle
import com.jp.app.R
import com.jp.app.common.view.BaseFragmentChild
import com.jp.app.common.view.IBaseFragmentChildCallback
import com.jp.app.ui.manager.view.ManagerFragment
import com.jp.app.ui.menu.viewModel.MenuViewModel
import kotlinx.android.synthetic.main.menu_fragment.*


class MenuFragment : BaseFragmentChild<MenuViewModel, MenuFragment.FragmentCallback>() {

    override fun getLayoutId(): Int {
        return R.layout.menu_fragment
    }

    interface FragmentCallback : IBaseFragmentChildCallback {
        fun loadOption(option: ManagerFragment.OPTIONS)
    }


    override fun getFragmentTitle(): String {
        return "MenuFragment"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mViewModel.loadData()
        }
    }

    override fun subscribeToLiveData() {
    }

    override fun setUpChildViews() {
        sample_button.setOnClickListener {
            mChildCallback.loadOption(ManagerFragment.OPTIONS.SAMPLE)
        }

        act_api_button.setOnClickListener {
            mChildCallback.loadOption(ManagerFragment.OPTIONS.ACT_API)
        }
    }

    override fun applyRTLChanges() {
    }


    companion object {
        fun newInstance(bundle: Bundle?) = MenuFragment().apply {
            arguments = bundle ?: Bundle()
        }
    }
}
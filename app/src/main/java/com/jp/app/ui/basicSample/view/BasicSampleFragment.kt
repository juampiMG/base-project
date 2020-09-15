package com.jp.app.ui.basicSample.view

import android.os.Bundle
import com.jp.app.R
import com.jp.app.common.view.BaseFragmentChild
import com.jp.app.common.view.IBaseFragmentChildCallback
import com.jp.app.ui.basicSample.viewModel.BasicSampleViewModel

/**
 * Basic Child Fragment
 * When press back move to the menu fragment
 * It is managed by ChildFragmentManager from Manager Fragment
 * Manager Fragment is responsible to manage the childs Fragments
 */
class BasicSampleFragment : BaseFragmentChild<BasicSampleViewModel, BasicSampleFragment.FragmentCallback>() {
    override fun getLayoutId(): Int {
        return R.layout.sample_fragment
    }

    interface FragmentCallback : IBaseFragmentChildCallback {
    }


    override fun getFragmentTitle(): String {
        return "SampleFragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mViewModel.loadData()
        }
    }

    override fun subscribeToLiveData() {
    }

    companion object {
        fun newInstance(bundle: Bundle?) = BasicSampleFragment().apply {
            arguments = bundle ?: Bundle()
        }
    }

    override fun setUpChildViews() {
    }

    override fun applyRTLChanges() {
    }

}

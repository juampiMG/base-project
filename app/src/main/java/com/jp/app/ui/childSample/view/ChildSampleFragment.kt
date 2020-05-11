package com.jp.app.ui.childSample.view

import android.os.Bundle
import com.jp.app.R
import com.jp.app.common.view.BaseFragmentChild
import com.jp.app.common.view.IBaseFragmentChildCallback
import com.jp.app.ui.childSample.viewModel.ChildSampleViewModel


class ChildSampleFragment : BaseFragmentChild<ChildSampleViewModel, ChildSampleFragment.FragmentCallback>() {
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
        fun newInstance(bundle: Bundle?) = ChildSampleFragment().apply {
            arguments = bundle ?: Bundle()
        }
    }

    override fun setUpChildViews() {
    }

    override fun applyRTLChanges() {
    }

}

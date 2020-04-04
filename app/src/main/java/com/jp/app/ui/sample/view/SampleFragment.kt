package com.jp.app.ui.sample.view

import android.os.Bundle
import androidx.lifecycle.Observer
import com.jp.app.R
import com.jp.app.common.view.BaseFragment
import com.jp.app.common.view.IBaseFragmentCallback
import com.jp.app.ui.sample.viewModel.SampleViewModel


class SampleFragment : BaseFragment<SampleViewModel, SampleFragment.FragmentCallback>() {
    override fun getLayoutId(): Int {
        return R.layout.sample_fragment
    }

    interface FragmentCallback : IBaseFragmentCallback {
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
//        mViewModel.getStatus().observe(viewLifecycleOwner, Observer { status ->
//            if (status != null) {
//            }
//        })
    }


    override fun setUpViews() {
    }

    override fun applyRTLChanges() {
    }

    companion object {
        fun newInstance(bundle: Bundle?) = SampleFragment().apply {
            arguments = bundle ?: Bundle()
        }
    }
}

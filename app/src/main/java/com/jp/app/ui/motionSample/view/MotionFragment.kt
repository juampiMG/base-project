package com.jp.app.ui.motionSample.view

import android.os.Bundle
import com.jp.app.R
import com.jp.app.common.view.BaseFragmentChild
import com.jp.app.common.view.IBaseFragmentChildCallback
import com.jp.app.ui.motionSample.viewModel.MotionViewModel


class MotionFragment : BaseFragmentChild<MotionViewModel, MotionFragment.FragmentCallback>() {

    override fun getLayoutId(): Int {
        return R.layout.motion_fragment
    }

    interface FragmentCallback : IBaseFragmentChildCallback {
    }


    override fun getFragmentTitle(): String {
        return "MotionFragment"
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
    }


    override fun applyRTLChanges() {
    }


    companion object {
        fun newInstance(bundle: Bundle?) = MotionFragment().apply {
            arguments = bundle ?: Bundle()
        }
    }
}
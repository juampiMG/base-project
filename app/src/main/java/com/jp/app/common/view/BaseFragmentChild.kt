package com.jp.app.common.view

import com.jp.app.common.viewModel.IBaseViewModel
import kotlinx.android.synthetic.main.sample_fragment.*
import javax.inject.Inject

/**
 * Base fragment child
 */
abstract class BaseFragmentChild<TChildViewModel : IBaseViewModel, TChildCallback : IBaseFragmentChildCallback> : BaseFragment<TChildViewModel, IBaseFragmentCallback>() {

    @Inject
    lateinit var mChildCallback: TChildCallback

    override fun setUpViews() {
        setUpBackButtonListener()
        setUpChildViews()
    }


    private fun setUpBackButtonListener() {
        back_button?.setOnClickListener {
            mChildCallback.childBackPressed()
        }
    }

    override fun onResume() {
        super.onResume()
        mChildCallback.setCurrentFragmentLoad(this)
    }

    abstract fun setUpChildViews()

}

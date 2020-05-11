package com.jp.app.ui.activityApi.view

import android.net.Uri
import android.os.Bundle
import androidx.activity.invoke
import androidx.activity.result.contract.ActivityResultContracts
import com.jp.app.R
import com.jp.app.common.view.BaseFragmentChild
import com.jp.app.common.view.IBaseFragmentChildCallback
import com.jp.app.ui.activityApi.viewModel.ActAPIViewModel
import kotlinx.android.synthetic.main.act_api_fragment.*


class ActAPIFragment : BaseFragmentChild<ActAPIViewModel, ActAPIFragment.FragmentCallback>() {

    override fun getLayoutId(): Int {
        return R.layout.act_api_fragment
    }

    interface FragmentCallback : IBaseFragmentChildCallback {
    }


    override fun getFragmentTitle(): String {
        return "ActAPIFragment"
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
        open_cam.setOnClickListener {
            val imageUri: Uri? = null
            takePicture(imageUri)
        }
        open_gallery.setOnClickListener {
            pickImages("image/*")
        }
    }


    override fun applyRTLChanges() {
    }

    private val takePicture = registerForActivityResult(ActivityResultContracts.TakePicture()) { bitmap ->
        bitmap?.let {
            picture_image.setImageBitmap(bitmap)
        }
    }

    // Pick Images Contract - Normally this is for all kind of files.
    private val pickImages = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            picture_gallery.setImageURI(it)
        }
    }


    companion object {
        fun newInstance(bundle: Bundle?) = ActAPIFragment().apply {
            arguments = bundle ?: Bundle()
        }
    }
}
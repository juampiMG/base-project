package com.jp.app.common.view

interface IBaseFragmentChildCallback {
    fun childBackPressed()
    fun setCurrentFragmentLoad(fragment: BaseFragmentChild<*, *>)
}

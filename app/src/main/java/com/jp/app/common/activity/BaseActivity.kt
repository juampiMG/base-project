package com.jp.app.common.activity


import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.jp.app.R
import com.jp.app.common.viewModel.IBaseActivityViewModel
import com.jp.app.helper.DialogHelper
import com.jp.app.helper.NavigationHelper
import com.jp.app.model.AlertDialogModel
import com.jp.app.utils.NavigationUtils
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.generic_loading.*
import javax.inject.Inject


/**
 * Base Activity all the activities must inherit from this class
 */

abstract class BaseActivity<TActivityViewModel : IBaseActivityViewModel> : AppCompatActivity(), HasSupportFragmentInjector, LifecycleOwner, IBaseActivityCallback {

    @set:Inject
    internal lateinit var mFragmentInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var mExtras: Bundle  //Useful to pass data between fragments with the same activity

    @Inject
    lateinit var mDialogHelper: DialogHelper

    @Inject
    lateinit var mNavigationHelper: NavigationHelper

    @Inject
    lateinit var mActivityViewModel: TActivityViewModel

    private var mLayoutId: Int = 0

    lateinit var mCurrentFragment: Fragment

    private var mCompositeDisposable: CompositeDisposable? = null


    // =============== HasFragmentInjector =========================================================

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return mFragmentInjector
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        mLayoutId = getLayoutId()
        setContentView(mLayoutId)
        subscribers()
    }

    // =============== Manage Views ================================================================

    abstract fun getLayoutId(): Int


    override fun showLoading(loading: Boolean) {
        generic_loading?.visibility = if (loading) VISIBLE else GONE
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return false
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.stay, R.anim.slide_out_up)
    }

    fun finishWithTransition() {
        finish()
        overridePendingTransition(R.anim.stay, R.anim.slide_out_up)
    }

    fun finishWithTransitionAndResult(result: Int, intent: Intent) {
        setResult(result, intent)
        finish()
        overridePendingTransition(R.anim.stay, R.anim.slide_out_up)
    }

    /**
     * on view is destroy unsubscribe all the pending calls
     */
    override fun onDestroy() {
        super.onDestroy()
        removeAllDisposables()
        mActivityViewModel.onDestroy()
    }

    /**
     * Subscribe to loading progressbar for show or hide on demand from the viewModel
     * Subscribe to the otres live datas that the target fragment require
     */
    private fun subscribers() {
        subscribeLoading()
        subscribeToAlertDialogs()
        subscribeToLiveData()
    }

    /**
     * Subscribe to the loading live data to wait for a signal and show or hide the progressBar
     * generic_loading comes from the generic_loading.xml view. To use the progress bar just include it
     * to the fragment view <include layout="@layout/generic_loading" />
     */
    private fun subscribeLoading() {
        mActivityViewModel.showIsLoading().observe(this,  { isLoading ->
            if (isLoading != null) showLoading(isLoading)
        })
    }

    /**
     * Subscribe to show the Alert Dialogs
     */
    private fun subscribeToAlertDialogs() {
        mActivityViewModel.showAlertDialogOneButton().observe(this, { alertDialogModel ->
            if (alertDialogModel != null) showAlertDialogOneButton(alertDialogModel)
        })

        mActivityViewModel.showAlertDialogTwoButtons().observe(this, { alertDialogModel ->
            if (alertDialogModel != null) showAlertDialogTwoButtons(alertDialogModel)
        })

        mActivityViewModel.showErrorMessageDialog().observe(this, { error ->
            if (error != null) showErrorMessageDialog(error)
        })

        mActivityViewModel.showErrorMessageDialogString().observe(this, { error ->
            showErrorMessageDialog(error)
        })

        mActivityViewModel.showDisplayServerErrorToast().observe(this, {
            showErrorServerMessageToast()
        })

    }

    // =============== ShowDialogs =================================================================

    override fun showAlertDialogTwoButtons(alertDialogModel: AlertDialogModel) {
        val title = if (alertDialogModel.title == -1) "" else getString(alertDialogModel.title)
        val message = if (alertDialogModel.message == -1) "" else getStringFromListOfArgs(
                alertDialogModel.message,
                alertDialogModel.messageArg
        )
        mDialogHelper.alertDialogTwoButtons(
                title,
                message,
                getString(alertDialogModel.leftButtonTitle),
                alertDialogModel.leftButtonListener,
                getString(alertDialogModel.rightButtonTitle),
                alertDialogModel.rightButtonListener,
                true
        )
    }

    override fun showAlertDialogOneButton(alertDialogModel: AlertDialogModel) {
        val title = if (alertDialogModel.title == -1) "" else getString(alertDialogModel.title)
        val message = if (alertDialogModel.message == -1) "" else getStringFromListOfArgs(
                alertDialogModel.message,
                alertDialogModel.messageArg
        )
        mDialogHelper.alertDialogOneButton(
                title,
                message,
                getString(alertDialogModel.rightButtonTitle),
                alertDialogModel.rightButtonListener,
                true
        )
    }

    override fun showErrorMessageDialog(descriptionError: Int) {
        mDialogHelper.errorMessageEventDialog(getString(descriptionError))
    }

    /**
     * OnError can be null, into BaseSubscriber is controlled, if this happens descriptionError is set to empty
     * then from here is shown error alert with the generic error_error
     */
    override fun showErrorMessageDialog(descriptionError: String?) {
        var error = descriptionError
        if (error.isNullOrEmpty()) error = getString(R.string.error_server)
        error.let { mDialogHelper.errorMessageEventDialog(it) }
    }


    override fun showErrorServerMessageToast() {
        Toast.makeText(this, R.string.error_server, Toast.LENGTH_LONG).show()
    }

    internal fun loadFragment(addToBackStack: Boolean) {
        NavigationUtils.navigateToFragment(
                activity = this,
                supportFragmentManager = this.supportFragmentManager,
                fragment = mCurrentFragment,
                contentFrame = R.id.content,
                addToBackStack = addToBackStack
        )
    }


    /**
     * Convert the list of Pair<Int, String> and the message in only one string putting the objects of the pairs as placeholder into the message
     * if  first is -1, means that the string was added as a String, finally add this string to the Aux list
     * If  first is not -1, means that was added as a resource and will get the resource with getString(arg.first), finally add this string to the Aux list
     *
     * return the message plus the strings added into the aux list
     * if there are not args
     * return just the message resource
     */
    private fun getStringFromListOfArgs(message: Int, args: List<Pair<Int, String?>>): String {
        if (args.isNullOrEmpty()) return getString(message)
        val list: MutableList<String> = ArrayList()
        for (arg in args) {
            if (arg.first == -1 && arg.second != null) {
                arg.second?.let { list.add(it) }
            } else {
                list.add(getString(arg.first))
            }
        }
        return getString(message, *list.toTypedArray())
    }


    // =============== Manage Disposable ===========================================================

    fun getCompositeDisposable(): CompositeDisposable {
        val isDisposed = mCompositeDisposable?.isDisposed?.let { it } ?: run { false }
        if (mCompositeDisposable == null || isDisposed)
            mCompositeDisposable = CompositeDisposable()
        return mCompositeDisposable as CompositeDisposable
    }

    fun addDisposable(disposable: Disposable?) {
        if (disposable != null) {
            mCompositeDisposable?.add(disposable)
        }
    }

    fun removeDisposable(disposable: Disposable?) {
        if (disposable != null) {
            if (!disposable.isDisposed) {
                disposable.dispose()
            }
            if (mCompositeDisposable != null) {
                mCompositeDisposable?.remove(disposable)
            }
        }
    }

    fun removeAllDisposables() {
        mCompositeDisposable?.clear()
    }

    fun hasDisposables(): Boolean {
        return if (mCompositeDisposable != null) {
            mCompositeDisposable?.size()?.let { it > 0 } ?: run { false }
        } else {
            false
        }
    }

    abstract fun subscribeToLiveData()

}

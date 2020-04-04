package com.jp.app.common


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.jp.app.R
import com.jp.app.common.view.IBaseFragmentCallback
import com.jp.app.helper.DialogHelper
import com.jp.app.helper.NavigationHelper
import com.jp.app.model.AlertDialogModel
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.generic_loading.*
import javax.inject.Inject


abstract class BaseActivity : AppCompatActivity(), HasSupportFragmentInjector, IBaseFragmentCallback {

    @set:Inject
    internal lateinit var mFragmentInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var mExtras: Bundle  //Useful to pass data between fragments with the same activity
    @Inject
    lateinit var mDialogHelper: DialogHelper
    @Inject
    lateinit var mNavigationHelper: NavigationHelper

    private var mLayoutId: Int = 0

    lateinit var mCurrentFragment : Fragment

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
    }

    // =============== Manage Views ================================================================

    abstract fun getLayoutId(): Int


    override fun isLoading(loading: Boolean) {
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
    }

    // =============== ShowDialogs =================================================================

    override fun alertDialogTwoButtons(alertDialogModel: AlertDialogModel) {
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

    override fun alertDialogOneButton(alertDialogModel: AlertDialogModel) {
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

    override fun errorMessageDialog(descriptionError: Int) {
        mDialogHelper.errorMessageEventDialog(getString(descriptionError))
    }

    /**
     * OnError can be null, into BaseSubscriber is controlled, if this happens descriptionError is set to empty
     * then from here is shown error alert with the generic error_error
     */
    override fun errorMessageDialog(descriptionError: String?) {
        var error = descriptionError
        if (error.isNullOrEmpty()) error = getString(R.string.error_server)
        error?.let { mDialogHelper.errorMessageEventDialog(it) }
    }

    override fun errorMessageToast(descriptionError: Throwable) {
//        RetrofitUtil.displayServerError(this, descriptionError)
    }

    override fun errorServerMessageToast() {
        Toast.makeText(this, R.string.error_server, Toast.LENGTH_LONG).show()
    }


    fun setCheckOkToastAndShow(alertText: Int) {
//        val toast = CheckOkAlertToastComponent(this)
//        toast.setAlertText(getString(alertText))
//        toast.showAlert()
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
}

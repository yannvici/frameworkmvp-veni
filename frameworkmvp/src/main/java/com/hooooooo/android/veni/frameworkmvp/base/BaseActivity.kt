package com.hooooooo.android.veni.frameworkmvp.base

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.ViewCompat
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.hooooooo.android.veni.frameworkmvp.R
import com.hooooooo.android.veni.frameworkmvp.databinding.CircularLoadingBinding
import com.hooooooo.android.veni.frameworkmvp.utils.ActivityManager
import com.hooooooo.android.veni.frameworkmvp.utils.CoroutineManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference
import kotlin.LazyThreadSafetyMode.NONE

/**
 * Created by yann on 2022/10/26
 * <p>
 * Describe:
 */
abstract class BaseActivity<VB : ViewBinding>(
    private val inflate: (LayoutInflater) -> VB,
    @ColorInt private val statusBarBackgroundColor: Int,
    private val statusBarFontColor: Boolean//ture 黑色， false 白色
) : AppCompatActivity(), UiCallBack, BaseView {
    private val weakReference: WeakReference<AppCompatActivity> by lazy(NONE) { WeakReference<AppCompatActivity>(this@BaseActivity) }
    protected val context: Activity by lazy(NONE) { this@BaseActivity }
    protected val viewBinding: VB by lazy(NONE) { inflate(layoutInflater) }
    private val loadingDialog: Dialog by lazy(NONE) { Dialog(this@BaseActivity, R.style.TransDialogStyle) }
    private val msgDialog: Dialog by lazy(NONE) { Dialog(this@BaseActivity, R.style.TransDialogStyle) }
    override fun onCreate(savedInstanceState: Bundle?) {
        window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
        window.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE.or(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
        )
        ViewCompat.getWindowInsetsController(window.decorView)?.isAppearanceLightStatusBars = statusBarFontColor
        window.statusBarColor = statusBarBackgroundColor
        super.onCreate(savedInstanceState)
        initBeforeView(savedInstanceState)
        setContentView(viewBinding.root)
        initData(savedInstanceState)
        weakReference.get()?.let { ActivityManager.addActivity(it) }
    }

    override fun onDestroy() {
        super.onDestroy()
        weakReference.get()?.let {
            ActivityManager.removeActivity(it)
        }
        //prevent activity has leaked window that was originally added
        loadingDialog.dismiss()
        msgDialog.dismiss()
    }

    /**
     * 重写startActivity，使其带动画
     */
    override fun startActivity(intent: Intent?) {
        super.startActivity(intent)
        hideKeyboard()
        overridePendingTransition(0, 0)
    }

    override fun startTransitionActivity(intent: Intent) {
        startActivity(intent)
    }

    override fun finishAfterTransition() {
        super.finishAfterTransition()
        hideKeyboard()
    }

    override fun showLoading() {
        Log.e("showLoading", "showLoading")
        showLoadingDialog()
    }

    override fun abnormalData(message: String) {
        showToastDialog(message)
    }

    override fun abnormalNet() {
        showToastDialog("网络异常，请检查网络配置后重试")
    }

    override fun hideLoading() {
        Log.e("hideLoading", "hideLoading")
        closeLoadingDialog()
    }


    /**
     * 初始化Dialog
     */
    private fun initDialog(dialog: Dialog, msg: String, toastState: Boolean) {
        val dialogViewBinding: CircularLoadingBinding = CircularLoadingBinding.inflate(
            LayoutInflater.from(this@BaseActivity), viewBinding.root as ViewGroup, false
        )
        dialogViewBinding.apply {
            loadIv.visibility = if (toastState) View.GONE else View.VISIBLE
            pointTv.visibility = if (toastState) View.GONE else View.VISIBLE
            pointTv.text = msg
            toastTv.visibility = if (toastState) View.VISIBLE else View.GONE
            toastTv.text = msg
            loadIv.startAnimation(AnimationUtils.loadAnimation(this@BaseActivity, R.anim.rotating_animation))

        }
        dialog.apply {
            setContentView(dialogViewBinding.root)
            setCancelable(false)
            setCanceledOnTouchOutside(false)
        }
    }

    /**
     * 打开加载画面
     */
    private fun showLoadingDialog() = runOnUiThread {
        initDialog(loadingDialog, "加载中，请稍候...", false)
        loadingDialog.window?.apply {
            attributes?.apply {
                width = ViewGroup.LayoutParams.WRAP_CONTENT
                height = ViewGroup.LayoutParams.WRAP_CONTENT
            }
            setGravity(Gravity.CENTER)
            setWindowAnimations(R.style.PopWindowAnimStyle)
        }
        if (!isFinishing) {
            loadingDialog.show()
        }
    }

    /**
     * 打开信息提示画面
     * @param msg 提示信息
     */
    fun showToastDialog(msg: String) = runOnUiThread {
        initDialog(msgDialog, msg, true)
        msgDialog.window?.apply {
            setDimAmount(0f)
            attributes?.apply {
                width = 300
                height = ViewGroup.LayoutParams.WRAP_CONTENT
            }
            setGravity(Gravity.CENTER)
            setWindowAnimations(R.style.PopWindowAnimStyle)
        }
        if (!isFinishing) {
            msgDialog.show()
        }
        lifecycleScope.launch {
            delay(2000)
            CoroutineManager.mainCoroutineDispatcher {
                msgDialog.hide()
            }
        }
    }

    /**
     * 关闭加载画面
     */
    private fun closeLoadingDialog() = runOnUiThread { loadingDialog.hide() }

    /**
     * 关闭软键盘
     */
    protected fun hideKeyboard() {
        val imm: InputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        val v: View = window.peekDecorView()
        imm.hideSoftInputFromWindow(v.windowToken, 0)
    }

    /**
     * EditTextView中清除按钮显示隐藏
     *
     * @param imageView 清除按钮
     * @param editText  对应edit
     * @return TextWatcher
     */
    protected open fun textClearWatcher(imageView: AppCompatImageView, editText: AppCompatEditText): TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) = Unit
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) = Unit
        override fun afterTextChanged(s: Editable) {
            if (s.isEmpty()) {
                imageView.visibility = View.GONE
            } else {
                imageView.visibility = View.VISIBLE
                imageView.setOnClickListener {
                    editText.setText("")
                    editText.requestFocus()
                }
            }
        }
    }

}
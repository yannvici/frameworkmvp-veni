package com.hooooooo.android.veni.frameworkmvp.utils

import android.app.Dialog
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.hooooooo.android.veni.frameworkmvp.R
import com.hooooooo.android.veni.frameworkmvp.databinding.CircularLoadingBinding
import java.util.*

/**
 * Created by heyangpeng on 2022/12/28
 * <p>
 * Describe:
 */
object DialogManager {
    private val loadingDialog: Dialog? by lazy(LazyThreadSafetyMode.NONE) {
        ActivityManager.getTaskTop()?.let { Dialog(it, R.style.TransDialogStyle) }
    }
    private val msgDialog: Dialog? by lazy(LazyThreadSafetyMode.NONE) { ActivityManager.getTaskTop()?.let { Dialog(it, R.style.TransDialogStyle) } }

    /**
     * 初始化Dialog
     */
    private fun initDialog(dialog: Dialog, msg: String, toastState: Boolean) {
        val dialogViewBinding: CircularLoadingBinding = CircularLoadingBinding.inflate(
            LayoutInflater.from(ActivityManager.getTaskTop()), null, false
        )
        dialogViewBinding.apply {
            loadIv.visibility = if (toastState) View.GONE else View.VISIBLE
            pointTv.visibility = if (toastState) View.GONE else View.VISIBLE
            pointTv.text = msg
            toastTv.visibility = if (toastState) View.VISIBLE else View.GONE
            toastTv.text = msg
            loadIv.startAnimation(AnimationUtils.loadAnimation(ActivityManager.getTaskTop(), R.anim.rotating_animation))

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
    fun showLoadingDialog() = ActivityManager.getTaskTop()?.apply {
        runOnUiThread {
            Log.e("showLoadingDialog", "showLoadingDialog")
            loadingDialog?.let {
                initDialog(it, "加载中，请稍候...", false)
                it.window?.apply {
                    attributes?.apply {
                        width = ViewGroup.LayoutParams.WRAP_CONTENT
                        height = ViewGroup.LayoutParams.WRAP_CONTENT
                    }
                    setGravity(Gravity.CENTER)
                    setWindowAnimations(R.style.PopWindowAnimStyle)
                }
                if (!isFinishing) {
                    it.show()
                }
            }
        }
    }

    /**
     * 打开信息提示画面
     * @param msg 提示信息
     */
    fun showToastDialog(msg: String) = ActivityManager.getTaskTop()?.apply {
        Log.e("msg=", msg)
        runOnUiThread {
            msgDialog?.let {
                initDialog(it, msg, true)
                it.window?.apply {
                    setDimAmount(0f)
                    attributes?.apply {
                        width = 300
                        height = ViewGroup.LayoutParams.WRAP_CONTENT
                    }
                    setGravity(Gravity.CENTER)
                    setWindowAnimations(R.style.PopWindowAnimStyle)
                }
                if (!isFinishing) {
                    it.show()
                }
                Timer().schedule(object : TimerTask() {
                    override fun run() {
                        runOnUiThread {
                            it.hide()
                        }
                    }
                }, 2000)
            }
        }
    }

    /**
     * 关闭加载画面
     */
    fun closeLoadingDialog() = ActivityManager.getTaskTop()?.apply {
        runOnUiThread { loadingDialog?.apply { if (isShowing) hide() } }
        Log.e("closeLoadingDialog", "closeLoadingDialog")
    }

    fun dialogDismiss() {
        ActivityManager.getTaskTop()?.apply {
            runOnUiThread {
                loadingDialog?.apply { dismiss() }
                msgDialog?.apply { dismiss() }
            }
        }
    }
}
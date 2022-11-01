package com.hooooooo.android.veni.frameworkmvp.base

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.viewbinding.ViewBinding
import com.hooooooo.android.veni.frameworkmvp.entry.StatusBarTheme
import com.hooooooo.android.veni.frameworkmvp.utils.ActivityManager
import java.lang.ref.WeakReference

/**
 * Created by yann on 2022/10/26
 * <p>
 * Describe:
 */
abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity(), UiCallBack {
    protected lateinit var context: Activity
    protected lateinit var viewBinding: VB
    override fun onCreate(savedInstanceState: Bundle?) {
        window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
        window.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE.or(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
        )
        ViewCompat.getWindowInsetsController(window.decorView)?.isAppearanceLightStatusBars = setStatusBarTheme().fontColor
        window.statusBarColor = setStatusBarTheme().backgroundColor
        super.onCreate(savedInstanceState)
        initBeforeView(savedInstanceState)
        viewBinding = bindActivityViewBinding()
        context = this
        initData(savedInstanceState)
        setContentView(viewBinding.root)
        WeakReference<Activity>(this).get()?.let { ActivityManager.addActivity(it) }
    }

    /**
     * 绑定视图
     */
    protected abstract fun bindActivityViewBinding(): VB

    /**
     * 设置状态栏主题
     */
    protected abstract fun setStatusBarTheme(): StatusBarTheme

    /**
     * 重写startActivity，使其带动画
     */
    override fun startActivity(intent: Intent?) {
        super.startActivity(intent)
        overridePendingTransition(0, 0)
    }
}
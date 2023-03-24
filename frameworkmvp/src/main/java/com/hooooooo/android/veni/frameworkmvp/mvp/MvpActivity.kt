package com.hooooooo.android.veni.frameworkmvp.mvp

import android.graphics.Color
import android.view.LayoutInflater
import androidx.annotation.ColorInt
import androidx.viewbinding.ViewBinding
import com.hooooooo.android.veni.frameworkmvp.base.BaseActivity
import com.hooooooo.android.veni.frameworkmvp.base.BasePresenter

/**
 * Created by yann on 2022/10/27
 * <p>
 * Describe:
 */
abstract class MvpActivity<V : BaseActivity<VB>, VB : ViewBinding, P : BasePresenter>(
    inflate: (LayoutInflater) -> VB,
    @ColorInt statusBarBackgroundColor: Int = Color.BLUE,
    statusBarFontColor: Boolean = false
) : BaseActivity<VB>(inflate, statusBarBackgroundColor, statusBarFontColor), PresenterCallBack<P> {
    protected val presenter: P by lazy { createPresenter() }
}
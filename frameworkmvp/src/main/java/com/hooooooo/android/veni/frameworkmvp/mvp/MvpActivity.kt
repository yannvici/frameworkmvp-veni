package com.hooooooo.android.veni.frameworkmvp.mvp

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import androidx.annotation.ColorInt
import androidx.viewbinding.ViewBinding
import com.hooooooo.android.veni.frameworkmvp.base.BaseActivity
import com.hooooooo.android.veni.frameworkmvp.base.BasePresenter
import com.hooooooo.android.veni.frameworkmvp.base.UiCallBack

/**
 * Created by yann on 2022/10/27
 * <p>
 * Describe:
 */
abstract class MvpActivity<V : BaseActivity<*>, VB : ViewBinding, P : BasePresenter<V>>(
    inflate: (LayoutInflater) -> VB,
    @ColorInt statusBarBackgroundColor: Int = Color.BLUE,
    statusBarFontColor: Boolean = false
) : BaseActivity<VB>(inflate, statusBarBackgroundColor, statusBarFontColor), PresenterCallBack<V, P> {
    protected lateinit var presenter: P
    override fun initBeforeView(savedInstanceState: Bundle?) {
        super.initBeforeView(savedInstanceState)
        presenter = createPresenter()
        presenter.attach(registerView())
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detach()
    }
}
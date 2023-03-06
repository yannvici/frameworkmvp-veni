package com.hooooooo.android.veni.frameworkmvp.mvp

import com.hooooooo.android.veni.frameworkmvp.base.BaseActivity
import com.hooooooo.android.veni.frameworkmvp.base.BasePresenter
import com.hooooooo.android.veni.frameworkmvp.base.UiCallBack

/**
 * Created by heyangpeng on 2022/10/27
 * <p>
 * Describe:
 */
interface PresenterCallBack<V : BaseActivity<*>, P : BasePresenter<V>> {
    /**
     * 创建presenter
     */
    fun createPresenter(): P

    /**
     * 将视图和presenter绑定
     */
    fun registerView(): V
}
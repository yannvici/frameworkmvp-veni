package com.hooooooo.android.veni.frameworkmvp.mvp

import com.hooooooo.android.veni.frameworkmvp.base.BasePresenter

/**
 * Created by heyangpeng on 2022/10/27
 * <p>
 * Describe:
 */
interface PresenterCallBack<P : BasePresenter> {
    /**
     * 创建presenter
     */
    fun createPresenter(): P
}
package com.hooooooo.android.veni.frameworkmvp.base

/**
 * Created by heyangpeng on 2022/12/29
 * <p>
 * Describe:
 */
interface IBasePresenter<V: UiCallBack> {
    /**
     * 关联view
     */
    fun attach(v: V)

    /**
     * 分离view
     */
    fun detach()


    /**
     * 获取view
     */
    fun getView(): @UnsafeVariance V?

    fun destroy()
}
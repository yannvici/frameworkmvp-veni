package com.hooooooo.android.veni.frameworkmvp.base

import java.lang.ref.WeakReference

/**
 * Created by yann on 2022/10/26
 * <p>
 * Describe:
 */
abstract class BasePresenter<V : UiCallBack> {
    private lateinit var mWeakReference: WeakReference<V>

    /**
     * 关联view
     */
    fun attach(v: V) {
        mWeakReference = WeakReference<V>(v)
    }

    /**
     * 分离view
     */
    fun detach() = mWeakReference.clear()

    /**
     * 获取view
     */
    fun getView(): @UnsafeVariance V? = mWeakReference.get()
}
package com.hooooooo.android.veni.frameworkmvp.base

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import java.lang.ref.WeakReference
import kotlin.LazyThreadSafetyMode.SYNCHRONIZED

/**
 * Created by yann on 2022/10/26
 * <p>
 * Describe:
 */
abstract class BasePresenter<V : UiCallBack> {
    private lateinit var mWeakReference: WeakReference<V>
    protected val pCoroutineScope by lazy(SYNCHRONIZED) { CoroutineScope(Dispatchers.Main) }

    /**
     * 关联view
     */
    fun attach(v: V) {
        mWeakReference = WeakReference<V>(v)
    }

    /**
     * 分离view
     */
    open fun detach() {
        Log.e("detach", "detachP")
        mWeakReference.clear()
        pCoroutineScope.cancel()
    }

    /**
     * 获取view
     */
    fun getView(): V? = if (::mWeakReference.isInitialized) mWeakReference.get() else null

}
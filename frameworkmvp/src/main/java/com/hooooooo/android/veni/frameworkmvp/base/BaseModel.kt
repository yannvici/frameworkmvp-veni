package com.hooooooo.android.veni.frameworkmvp.base

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel

/**
 * Created by heyangpeng on 2022/12/29
 * <p>
 * Describe:
 */
abstract class BaseModel : IModel {
    protected val mCoroutineScope by lazy { CoroutineScope(Dispatchers.IO) }
    override fun destroy() {
        Log.e("detach","detachM")
        mCoroutineScope.cancel()
    }

}
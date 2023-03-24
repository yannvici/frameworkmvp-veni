package com.hooooooo.android.veni.frameworkmvp.base

import android.util.Log
import com.google.gson.Gson
import com.hooooooo.android.veni.frameworkmvp.Constants
import com.hooooooo.android.veni.frameworkmvp.utils.CoroutineManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Response
import java.lang.ref.WeakReference
import java.text.ParseException

/**
 * Created by yann on 2022/10/26
 * <p>
 * Describe:
 */
abstract class BasePresenter<V : BaseActivity<*>> {
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
    open fun detach() {
        mWeakReference.clear()
    }

    /**
     * 获取view
     */
    fun getView(): V? = if (::mWeakReference.isInitialized) mWeakReference.get() else null

    protected suspend inline fun <T> getResult(crossinline block: suspend () -> Result<T>): Result<T> =
        withContext(CoroutineScope(Dispatchers.Main).coroutineContext) {
            block()
        }

    protected suspend inline fun <T> getResponseString(crossinline block: suspend () -> T): T? =
        withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
            block()
        }

    protected suspend inline fun <reified T> getDataResponse(crossinline result: suspend () -> Result<Response>): T? {
        getView()?.showLoading()
        val resultData = withContext(CoroutineScope(Dispatchers.Main).coroutineContext) {
            result()
        }
        return if (resultData.isSuccess) {
            getView()?.hideLoading()
            val data = CoroutineManager.ioCoroutineDispatcher {
                Gson().fromJson(resultData.getOrNull()?.body?.string(), T::class.java)
            }
            Log.e("data==", data.toString())
            if (resultData.getOrNull()?.isSuccessful == true) {
                data
            } else {
                getView()?.abnormalNet()
                null
            }
        } else {
            getView()?.apply {
                hideLoading()
                abnormalNet()
            }
            null
        }
    }

    protected inline fun <reified T> getData(response: BaseResponse, crossinline block: () -> T?) =
        if (response.code == 200) {
            block()
        } else {
            getView()?.apply {
                hideLoading()
                abnormalData(response.msg ?: Constants.UNKNOWN)
            }
            null
        }

    protected suspend inline fun <T : BaseResponse, R> fire(crossinline block: suspend () -> T, crossinline responseData: (T) -> R): Result<R> =
        withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
            runCatching {
                block()
            }.fold(
                onSuccess = {
                    if (it.code == 200) {
                        val data = responseData(it)
                        Result.success(data)
                    } else {
                        Result.failure(Exception(it.msg ?: Constants.UNKNOWN))
                    }
                },
                onFailure = {
                    Result.failure(it)
                }
            )
        }

}
package com.hooooooo.android.veni.framework_mvp

import android.util.Log
import com.google.gson.Gson
import com.hooooooo.android.veni.frameworkmvp.base.BasePresenter
import com.hooooooo.android.veni.frameworkmvp.base.BaseView
import com.hooooooo.android.veni.frameworkmvp.utils.ActivityManager
import com.hooooooo.android.veni.frameworkmvp.utils.DialogManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import okhttp3.Response

/**
 * Created by yann on 2022/10/27
 * <p>
 * Describe:
 */
class MainContract {
    class MainPresenter(private val model: MainModel = MainModel()) : BasePresenter<MainActivity>() {

        fun test1(callBAck: IMain) {
            callBAck.test("fff")
        }

        suspend fun requestCopyWriting2(): String {
            getView()?.showLoading()
            val result = withContext(pCoroutineScope.coroutineContext) {
                model.requestCopyWriting3()
            }
            return if (result.isSuccess) {
                getView()?.hideLoading()
                val data = Gson().fromJson(result.getOrNull()?.body?.string(), BaseResponse::class.java)
                if (result.getOrNull()?.isSuccessful == true) {
                    if (data.code == 200) {
                        data.result?.content ?: ""
                    } else {
                        getView()?.showToastDialog(data.msg)
                        ""
                    }
                } else {
                    "sss"
                }
            } else {
                getView()?.hideLoading()
                result.onFailure {
                    it.message?.let { it1 ->
                        getView()?.showToastDialog(it1)
                    }
                }
                "网路错误"
            }
        }

        suspend fun requestCopyWriting(): String {
            getView()?.showLoading()
//            delay(1000)
//            val response: Response = withContext(CoroutineScope(Dispatchers.Main).coroutineContext) {
            val response: Response = withContext(pCoroutineScope.coroutineContext) {
                Log.e("coroutineContext2", coroutineContext.toString())
                model.requestCopyWriting2()
            }
            response.apply {
                val data = Gson().fromJson(body?.string(), BaseResponse::class.java)
                delay(2000)
                getView()?.hideLoading()
                return if (isSuccessful) {
                    if (data.code == 200) {
                        data.result?.content ?: ""
                    } else {
                        getView()?.showToastDialog(data.msg)
                        ""
                    }
                } else {
                    ""
                }
            }
        }

        override fun detach() {
            super.detach()
            model.destroy()
        }

    }
}

interface IMain : BaseView {
    fun test(string: String)
}

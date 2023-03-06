package com.hooooooo.android.veni.framework_mvp

import android.util.Log
import com.google.gson.Gson
import com.hooooooo.android.veni.frameworkmvp.base.BaseModel
import com.hooooooo.android.veni.frameworkmvp.utils.DialogManager
import com.hooooooo.android.veni.frameworkmvp.utils.OkHttpClientUtil
import com.hooooooo.android.veni.frameworkmvp.utils.RequestParam
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import okhttp3.Response

/**
 * Created by heyangpeng on 2022/12/28
 * <p>
 * Describe:
 */
class MainModel : BaseModel() {
    suspend fun requestCopyWriting(): String? {
//        DialogManager.showLoadingDialog()
//        val s = withContext(Dispatchers.IO) {
//            val response = OkHttpClientUtil.createPostCall("https://apis.tianapi.com/pyqwenan/index",
//                RequestParam().apply { put("key", "7981f801f36ce8d416a39631ef9bc0af") }
//            ).execute()
//            delay(4000)
//
//            if (response.isSuccessful) {
//                Gson().fromJson(response.body?.string(), BaseResponse::class.java).result?.content ?: ""
//            } else {
//                null
//            }
//        }

        val s: String? = withContext(mCoroutineScope.coroutineContext) {

//             CoroutineScope(Dispatchers.Main).launch {DialogManager.showLoadingDialog() }
            DialogManager.showLoadingDialog()
            val response = OkHttpClientUtil.createPostCall("https://apis.tianapi.com/pyqwenan/index",
                RequestParam().apply { put("key", "7981f801f36ce8d416a39631ef9bc0af") }
            ).execute()
            delay(5000)
//            CoroutineScope(Dispatchers.Main).launch { DialogManager.closeLoadingDialog() }
            DialogManager.closeLoadingDialog()
            if (response.isSuccessful) {
                Gson().fromJson(response.body?.string(), BaseResponse::class.java).result?.content ?: ""
            } else {
                null
            }

        }
//        CoroutineScope(Dispatchers.Main).launch { DialogManager.closeLoadingDialog() }
        return s
    }

    suspend fun requestCopyWriting2(): Response = withContext(mCoroutineScope.coroutineContext) {
        Log.e("coroutineContext3", coroutineContext.toString())
        OkHttpClientUtil.createPostCall("https://apis.tianapi.com/pyqwenan/index",
            RequestParam().apply { put("key", "7981f801f36ce8d416a39631ef9bc0af") }
        ).execute()
    }

    suspend fun requestCopyWriting3() = fire {
        OkHttpClientUtil.createPostCall("https://apis.tianapi.com/pyqwenan/index",
            RequestParam().apply { put("key", "7981f801f36ce8d416a39631ef9bc0af") }
        ).execute()
    }
}
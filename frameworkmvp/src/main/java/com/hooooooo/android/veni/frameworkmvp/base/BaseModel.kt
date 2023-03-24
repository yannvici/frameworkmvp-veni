package com.hooooooo.android.veni.frameworkmvp.base

import com.google.gson.Gson
import com.hooooooo.android.veni.frameworkmvp.Constants
import kotlinx.coroutines.*
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException
import kotlin.coroutines.resumeWithException

/**
 * Created by heyangpeng on 2022/12/29
 * <p>
 * Describe:
 */
abstract class BaseModel : IModel {
    suspend inline fun fire(crossinline block: suspend () -> Response): Result<Response> = kotlin.runCatching {
        withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
            block()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    protected suspend inline fun <reified T : BaseResponse> Call.await(): T = suspendCancellableCoroutine { continuation ->
        continuation.invokeOnCancellation {
            cancel()
        }
        enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                continuation.resumeWithException(e)
            }

            override fun onResponse(call: Call, response: Response) {
                response.body?.let { it ->
                    continuation.resume(Gson().fromJson(it.string(), T::class.java)) {
                        continuation.resumeWithException(it)
                    }
                } ?: continuation.resumeWithException(NullPointerException(Constants.UNKNOWN))
            }
        })
    }
}
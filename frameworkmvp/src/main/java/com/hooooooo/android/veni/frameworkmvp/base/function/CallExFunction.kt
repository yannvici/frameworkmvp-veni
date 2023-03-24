package com.hooooooo.android.veni.frameworkmvp.base.function

import com.hooooooo.android.veni.frameworkmvp.exception.HttpStatusCodeException
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

/**
 * Created by heyangpeng on 2023/3/24
 * <p>
 * Describe:
 */

suspend fun <T> Call<T>.await(): T = suspendCancellableCoroutine { continuation ->
    continuation.invokeOnCancellation {
        cancel()
    }
    enqueue(object : Callback<T> {
        override fun onResponse(call: Call<T>, response: Response<T>) {
            try {
                response.body()?.let {
                    continuation.resume(it)
                }
            } catch (t: Throwable) {
                continuation.resumeWithException(HttpStatusCodeException(response.raw()))
            }
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            continuation.resumeWithException(t)
        }
    })
}

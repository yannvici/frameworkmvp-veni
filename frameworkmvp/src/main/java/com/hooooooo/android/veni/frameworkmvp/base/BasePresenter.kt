package com.hooooooo.android.veni.frameworkmvp.base

import com.hooooooo.android.veni.frameworkmvp.exception.ParseException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Created by yann on 2022/10/26
 * <p>
 * Describe:
 */
abstract class BasePresenter {
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
                        Result.failure(ParseException(it.code.toString(), it.msg))
                    }
                },
                onFailure = {
                    Result.failure(it)
                }
            )
        }


}
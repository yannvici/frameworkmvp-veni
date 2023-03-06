package com.hooooooo.android.veni.frameworkmvp.base

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Response

/**
 * Created by heyangpeng on 2022/12/29
 * <p>
 * Describe:
 */
abstract class BaseModel : IModel {
    suspend fun fire(block: suspend () -> Response): Result<Response> = kotlin.runCatching {
        withContext(Dispatchers.IO) {
            block()
        }
    }
}
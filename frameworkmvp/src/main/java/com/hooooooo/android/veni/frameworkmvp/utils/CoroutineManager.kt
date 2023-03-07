package com.hooooooo.android.veni.frameworkmvp.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Created by heyangpeng on 2023/3/3
 * <p>
 * Describe:
 */
object CoroutineManager {

    suspend inline fun <T> mainCoroutineDispatcher(crossinline block: () -> T) =
        withContext(CoroutineScope(Dispatchers.Main).coroutineContext) {
            block.invoke()
        }

    suspend inline fun <T> ioCoroutineDispatcher(crossinline block: () -> T) =
        withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
            block.invoke()
        }

    suspend inline fun <T> defaultCoroutineDispatcher(crossinline block: () -> T) =
        withContext(CoroutineScope(Dispatchers.Default).coroutineContext) {
            block.invoke()
        }

    suspend inline fun <T> unconfinedCoroutineDispatcher(crossinline block: () -> T) =
        withContext(CoroutineScope(Dispatchers.Unconfined).coroutineContext) {
            block.invoke()
        }
}
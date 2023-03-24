package com.hooooooo.android.veni.frameworkmvp.exception

import okio.IOException

/**
 * Created by yann on 2023/3/13
 * <p>
 * Describe:
 */
class ParseException(code: String, message: String?) : IOException(message) {
    @get:JvmName("errorCode")
    private val errorCode: String by lazy { code }

    fun getErrorCode(): String = errorCode


    override fun getLocalizedMessage(): String {
        super.getLocalizedMessage()
        return errorCode
    }

    override fun toString(): String {
        return javaClass.name + ":" +
                "\n\nCode=" + errorCode + " message=" + message
    }
}
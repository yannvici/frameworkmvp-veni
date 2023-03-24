package com.hooooooo.android.veni.frameworkmvp.exception

import okhttp3.*
import okio.IOException

/**
 * Created by yann on 2023/3/13
 * <p>
 * Describe:
 */
class HttpStatusCodeException(private val response: Response) : IOException() {
    @get:JvmName("protocol")
    private val protocol: Protocol by lazy { response.protocol }//http协议

    @get:JvmName("statusCode")
    private val statusCode: Int by lazy { response.code } //Http响应状态吗

    @get:JvmName("requestMethod")
    private val requestMethod: String by lazy { response.request.method }//请求方法，Get/Post等

    @get:JvmName("httpUrl")
    private val httpUrl: HttpUrl by lazy { response.request.url }//请求Url及查询参数

    @get:JvmName("responseHeaders")
    private val responseHeaders: Headers by lazy { response.headers } //响应头

    @get:JvmName("body")
    private val body: ResponseBody? by lazy { response.body }
    private var result: String? = null//返回结果

    override fun getLocalizedMessage(): String {
        super.getLocalizedMessage()
        return statusCode.toString()
    }

    fun getStatusCode(): Int = statusCode

    fun getRequestMethod(): String = requestMethod

    fun getRequestUrl(): String = httpUrl.toString()

    fun getHttpUrl(): HttpUrl = httpUrl

    fun getResponseHeaders(): Headers = responseHeaders

    fun getResponseBody(): ResponseBody? = body

    @Throws(IOException::class)
    fun getResult(): String? = if (result == null) body?.string() else result

    override fun toString(): String {
        return " request end ------>" +
                "\n" + javaClass.name + ":" +
                "\n" + requestMethod + " " + httpUrl +
                "\n\n" + protocol + " " + statusCode + " " + message +
                "\n" + responseHeaders
    }
}
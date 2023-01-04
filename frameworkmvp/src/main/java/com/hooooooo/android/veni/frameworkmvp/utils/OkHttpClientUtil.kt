package com.hooooooo.android.veni.frameworkmvp.utils

import android.util.Log
import com.google.gson.Gson
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.util.concurrent.TimeUnit
import kotlin.LazyThreadSafetyMode.NONE
import kotlin.LazyThreadSafetyMode.SYNCHRONIZED

/**
 * Created by heyangpeng on 2022/12/16
 * <p>
 * Describe:
 */
object OkHttpClientUtil {

    private val FILE_TYPE: MediaType? by lazy(NONE) { "application/octet-stream".toMediaTypeOrNull() }
    private val _APPLICATION_JSON_TYPE: MediaType? by lazy(NONE) { "application/x-www-form-urlencoded;charset=utf-8".toMediaTypeOrNull() }
    private val APPLICATION_JSON_TYPE: MediaType? by lazy(NONE) { "application/json;charset=utf-8".toMediaTypeOrNull() }
    private const val TIME_OUT: Int = 10
    private val client: OkHttpClient by lazy(SYNCHRONIZED) {
        val clientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
        clientBuilder.apply {
            addInterceptor {
                return@addInterceptor it.proceed(
                    it.request()
                        .newBuilder()
                        .addHeader("User-Agent", "Mobile")
                        .build()
                )
            }
            //设置超时时间
            //设置超时时间
            connectTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS)
            readTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS)
            writeTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS)
            //支持重定向
            //支持重定向
            followRedirects(true)
            //https支持
            //https支持
            hostnameVerifier { _, _ -> true }
        }
        clientBuilder.build()
    }
    private val builder: Request.Builder by lazy(SYNCHRONIZED) { Request.Builder() }

    fun createPostCall(url: String, params: RequestParam, headers: RequestParam?): Call {
        val mFormBodyBuild: FormBody.Builder = FormBody.Builder()
        params.urlParams.forEach(mFormBodyBuild::add)
        val mHeaderBuild: Headers.Builder = Headers.Builder()
        headers?.apply {
            urlParams.forEach(mHeaderBuild::add)
        }
        return client.newCall(
            builder.url(url)
                .post(mFormBodyBuild.build())
                .headers(mHeaderBuild.build())
                .build()
        )
    }

    fun createPostCall(url: String, params: RequestParam): Call = createPostCall(url, params, null)

    fun createGetCall(url: String, params: RequestParam, headers: RequestParam?): Call {
        val urlBuilder: StringBuilder = StringBuilder(url).append("?")
        params.urlParams.forEach {
            urlBuilder.append(it.key).append("=").append(it.value).append("&")
        }
        val mHeaderBuild: Headers.Builder = Headers.Builder()
        headers?.apply {
            urlParams.forEach(mHeaderBuild::add)
        }
        return client.newCall(
            builder.url(urlBuilder.substring(0, urlBuilder.length - 1))
                .get()
                .headers(mHeaderBuild.build())
                .build()
        )
    }

    fun createGetCall(url: String, params: RequestParam): Call = createGetCall(url, params, null)

    fun createMonitorCall(url: String, params: RequestParam): Call {
        val urlBuilder: StringBuilder = StringBuilder(url).append("&")
        if (params.hasParams()) {
            params.urlParams.forEach {
                urlBuilder.append(it.key).append("=").append(it.value).append("&")
            }
        }
        return client.newCall(
            builder.url(urlBuilder.substring(0, urlBuilder.length - 1))
                .get()
                .build()
        )
    }

    fun createMultiPostCall(url: String, params: RequestParam): Call {
        val requestBody: MultipartBody.Builder = MultipartBody.Builder()
        requestBody.setType(MultipartBody.FORM)
        params.fileParams.forEach {
            if (it.value is File) {
                requestBody.addPart(
                    Headers.headersOf("Content-Disposition", "form-data; name=\"${it.key}\""),
                    (it.value as File).asRequestBody(null)
                )
            } else if (it.value is String) {
                requestBody.addPart(
                    Headers.headersOf("Content-Disposition", "form-data; name=\"${it.key}\""),
                    (it.value as String).toRequestBody(null)
                )
            }
        }
        return client.newCall(
            builder.url(url)
                .post(requestBody.build())
                .build()
        )
    }

    fun createApplicationJsonCall(url: String, params: RequestParam, headers: RequestParam?): Call {
        val mHeaderBuild = Headers.Builder()
        headers?.apply {
            urlParams.forEach(mHeaderBuild::add)
        }
        Log.e("", "s=" + Gson().toJson(params.urlParams))
        return client.newCall(
            builder.url(url)
                .headers(mHeaderBuild.build())
                .post(Gson().toJson(params.urlParams).toRequestBody(APPLICATION_JSON_TYPE))
                .build()
        )
    }

    fun createApplicationJsonCall(url: String, params: Map<String, Any>): Call =
        client.newCall(
            builder.url(url)
                .post(Gson().toJson(params).toRequestBody(APPLICATION_JSON_TYPE))
                .build()
        )

    fun createApplicationJsonCall(url: String, requestParam: RequestParam): Call = createApplicationJsonCall(url, requestParam, null)

    fun downFile(url: String): Call = client.newCall(
        builder.url(url)
            .build()
    )

}
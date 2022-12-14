package com.hooooooo.android.veni.frameworkmvp.net

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier

/**
 * Created by yann on 2022/10/28
 * <p>
 * Describe:服务构造器 设置api服务
 */
object ServiceGenerator {
    private lateinit var baseUrl: String
    private var connectTimeOut: Long = 1000
    private var readTimeOut: Long = 1000
    private var writeTimeOut: Long = 1000

    /**
     * 设置服务器地址，建议在 Application 中初始化
     */
    fun initBaseUrl(url: String): ServiceGenerator {
        baseUrl = url
        return this@ServiceGenerator
    }

    /**
     * 设置连接超时时间，默认10秒，建议在 Application 中初始化
     */
    fun initConnectTimeOut(connectTimeOut: Long): ServiceGenerator {
        this.connectTimeOut = connectTimeOut
        return this@ServiceGenerator
    }

    /**
     * 设置读取超时时间，默认10秒，建议在 Application 中初始化
     */
    fun initReadTimeOut(readTimeOut: Long): ServiceGenerator {
        this.readTimeOut = readTimeOut
        return this@ServiceGenerator
    }

    /**
     * 设置写入超时时间，默认10秒，建议在 Application 中初始化
     */
    fun initWriteTimeOut(writeTimeOut: Long): ServiceGenerator {
        this.writeTimeOut = writeTimeOut
        return this@ServiceGenerator
    }

    /**
     * 创建service实例
     */
    fun <T> createService(serviceClass: Class<T>): T? {
        val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        okHttpClientBuilder.apply {
            addInterceptor {
                it.proceed(
                    it.request()
                        .newBuilder()
                        .addHeader("User-Agent", "Mobile")
                        .build()
                )
            }
            addInterceptor(httpLoggingInterceptor)
            //设置超时时间
            connectTimeout(connectTimeOut, TimeUnit.MILLISECONDS)
            readTimeout(readTimeOut, TimeUnit.MILLISECONDS)
            writeTimeout(writeTimeOut, TimeUnit.MILLISECONDS)
            //支持重定向
            followRedirects(true)
            //https支持
            hostnameVerifier((HostnameVerifier { _, _ -> true }))
        }
        return if (::baseUrl.isInitialized) {
            Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClientBuilder.build())
                .build()
                .create(serviceClass)
        } else {
            null
        }

//        return baseUrl?.let {
//            Retrofit.Builder()
//                .baseUrl(it)
//                .addConverterFactory(GsonConverterFactory.create())
//                .client(okHttpClientBuilder.build())
//                .build()
//                .create(serviceClass)
//        }

//        return Retrofit.Builder()
//            .baseUrl(baseUrl)
//            .addConverterFactory(GsonConverterFactory.create())
//            .client(okHttpClientBuilder.build())
//            .build()
//            .create(serviceClass)
    }

}
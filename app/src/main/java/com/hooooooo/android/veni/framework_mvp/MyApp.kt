package com.hooooooo.android.veni.framework_mvp

import android.app.Application
import com.hooooooo.android.veni.frameworkmvp.net.ServiceGenerator

/**
 * Created by yann on 2022/10/28
 * <p>
 * Describe:
 */
class MyApp : Application() {
    companion object {
        private lateinit var instance: MyApp
        fun getInstance(): MyApp = if (::instance.isInitialized) instance else throw NotImplementedError("instance is not initialized")
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        ServiceGenerator.initBaseUrl("https://apis.tianapi.com/")
            .initConnectTimeOut(1000)
            .initReadTimeOut(1000)
            .initWriteTimeOut(1000)
    }
}
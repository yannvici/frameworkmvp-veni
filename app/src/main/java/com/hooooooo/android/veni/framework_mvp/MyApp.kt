package com.hooooooo.android.veni.framework_mvp

import android.app.Application
import com.hooooooo.android.veni.frameworkmvp.net.ServiceGenerator

/**
 * Created by yann on 2022/10/28
 * <p>
 * Describe:
 */
class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        ServiceGenerator.initBaseUrl("https://free-api.heweather.net")
            .initConnectTimeOut(1000)
            .initReadTimeOut(1000)
            .initWriteTimeOut(1000)
    }
}
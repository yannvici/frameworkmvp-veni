package com.hooooooo.android.veni.frameworkmvp.base.function

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import com.google.gson.JsonSyntaxException
import com.hooooooo.android.veni.frameworkmvp.Constants
import com.hooooooo.android.veni.frameworkmvp.exception.HttpStatusCodeException
import com.hooooooo.android.veni.frameworkmvp.exception.ParseException
import com.hooooooo.android.veni.frameworkmvp.utils.ActivityManager
import kotlinx.coroutines.TimeoutCancellationException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

/**
 * Created by yann on 2023/3/13
 * <p>
 * Describe:
 */
val Throwable.errorCode: Int
    get() = when (this) {
        is HttpStatusCodeException -> this.getStatusCode()
        is ParseException -> this.getErrorCode().toIntOrNull() ?: -1
        else -> -1
    }
val Throwable.errorMsg: String
    get() = if (this is UnknownHostException) {
        if (ActivityManager.getTaskTop()?.application?.let { isNetworkConnected(it) } == true)
            "当前无网络，请检查你的网络设置"
        else "网络连接不可用，请稍后重试！"
    } else if (this is SocketTimeoutException  //okhttp全局设置超时
        || this is TimeoutException     //rxjava中的timeout方法超时
        || this is TimeoutCancellationException  //协程超时
    ) {
        "连接超时,请稍后再试"
    } else if (this is ConnectException) {
        "网络不给力，请稍候重试！"
    } else if (this is HttpStatusCodeException) {               //请求失败异常
        "Http状态码异常 $message"
    } else if (this is JsonSyntaxException) {  //请求成功，但Json语法异常,导致解析失败
        "数据解析失败,请检查数据是否正确"
    } else if (this is ParseException) { // ParseException异常表明请求成功，但是数据不正确
        this.message ?: (Constants.UNKNOWN + "，错误码${getErrorCode()}")   //msg为空，显示code
    } else {
        message ?: this.toString()
    }


private fun isNetworkConnected(context: Context): Boolean {
    val mConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network: Network? = mConnectivityManager.activeNetwork
    if (network != null) {
        return mConnectivityManager.getNetworkCapabilities(network)?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) ?: false
    }
    return false
}


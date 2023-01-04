package com.hooooooo.android.veni.frameworkmvp.net

import android.util.Log
import com.google.gson.Gson
import com.hooooooo.android.veni.frameworkmvp.base.BaseResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by yann on 2022/10/28
 * <p>
 * Describe:
 */
abstract class NetCallBack<T> : Callback<T> {
    companion object {
        const val TAG: String = "NetCallBack"
    }

    override fun onResponse(call: Call<T>, response: Response<T>) {
        response.body()?.let {
            if (response.isSuccessful) {
                val baseResponse = Gson().fromJson(Gson().toJson(response.body()), BaseResponse::class.java)
                when (baseResponse.code) {
//                    404 -> Log.e(TAG, baseResponse.data.toString())
//                    500 -> Log.e(TAG, baseResponse.data.toString())
                    else -> {
//                        Log.e(TAG, baseResponse.data.toString())
                        onSuccess(call, response)
                    }
                }
            } else {
                onFailed()
            }
        }
    }

    /**
     * 请求成功回调
     */
    abstract fun onSuccess(call: Call<T>, response: Response<T>)

    /**
     * 请求失败回调
     */
    abstract fun onFailed()
}
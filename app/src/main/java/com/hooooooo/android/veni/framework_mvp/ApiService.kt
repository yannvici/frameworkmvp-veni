package com.hooooooo.android.veni.framework_mvp

import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Created by yann on 2022/10/28
 * <p>
 * Describe:
 */
interface ApiService {
    @POST("/pyqwenan/index")
    fun requestCopyWriting(@Query("key") key: String): Call<CopyWritingResponse>
}
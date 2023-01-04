package com.hooooooo.android.veni.framework_mvp

import com.google.gson.annotations.SerializedName

/**
 * Created by yann on 2022/10/26
 * <p>
 * Describe:
 */
data class BaseResponse(val code: Int, val msg: String, @SerializedName("result") val result: CopyWriting?=null)

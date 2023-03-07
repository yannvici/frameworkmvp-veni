package com.hooooooo.android.veni.framework_mvp

import com.hooooooo.android.veni.frameworkmvp.base.BaseResponse

/**
 * Created by heyangpeng on 2022/12/28
 * <p>
 * Describe:
 */
data class CopyWritingResponse(
    override val code: Int?,
    override val msg: String?,
    val result: CopyWriting?
) : BaseResponse()

data class CopyWriting(
    val content: String?,
    val source: String?
)

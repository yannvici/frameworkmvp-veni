package com.hooooooo.android.veni.framework_mvp

import com.hooooooo.android.veni.frameworkmvp.base.BaseModel
import com.hooooooo.android.veni.frameworkmvp.utils.OkHttpClientUtil
import com.hooooooo.android.veni.frameworkmvp.utils.RequestParam
import okhttp3.Response

/**
 * Created by heyangpeng on 2022/12/28
 * <p>
 * Describe:
 */
class MainModel : BaseModel() {
    suspend fun requestCopyWriting(): Result<Response> = fire {
        OkHttpClientUtil.createPostCall("https://apis.tianapi.com/pyqwenan/index",
            RequestParam().apply { put("key", "7981f801f36ce8d416a39631ef9bc0af") }
        ).execute()
    }

}
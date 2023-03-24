package com.hooooooo.android.veni.framework_mvp

import com.hooooooo.android.veni.frameworkmvp.base.BaseModel
import com.hooooooo.android.veni.frameworkmvp.base.function.await
import com.hooooooo.android.veni.frameworkmvp.net.ServiceGenerator


/**
 * Created by heyangpeng on 2022/12/28
 * <p>
 * Describe:
 */

class MainModel : BaseModel() {
    suspend fun requestCopyWriting3(): CopyWritingResponse = ServiceGenerator
        .createService(ApiService::class.java)
        .requestCopyWriting("7981f801f36ce8d416a39631ef9bc0af")
        .await()
}
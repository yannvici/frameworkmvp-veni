package com.hooooooo.android.veni.framework_mvp

import com.hooooooo.android.veni.frameworkmvp.base.BasePresenter

/**
 * Created by yann on 2022/10/27
 * <p>
 * Describe:
 */

class MainPresenter(private val model: MainModel = MainModel()) : BasePresenter() {
    suspend fun requestCopyWriting2(): Result<String> = fire(
        block = {
            model.requestCopyWriting3()
        },
        responseData = {
            it.result?.content ?: ""
        }
    )
}



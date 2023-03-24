package com.hooooooo.android.veni.framework_mvp

import com.hooooooo.android.veni.frameworkmvp.base.BasePresenter

/**
 * Created by yann on 2022/10/27
 * <p>
 * Describe:
 */

class MainPresenter(private val model: MainModel = MainModel()) : BasePresenter<MainActivity>() {
    suspend fun requestCopyWriting(): String {
        val result = getDataResponse<CopyWritingResponse> {
            model.requestCopyWriting()
        }
        return result?.let {
            getData(it) {
                it.result?.content ?: "暂无数据"
            } ?: "暂无数据"
        } ?: "暂无数据"
    }

    suspend fun requestCopyWriting1(): Result<String> = fire(
        block = {
            model.requestCopyWriting1()
        }
    ) {
        it.result?.content ?: ""
    }
}



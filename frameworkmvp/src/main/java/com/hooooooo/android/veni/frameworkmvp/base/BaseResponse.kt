package com.hooooooo.android.veni.frameworkmvp.base

/**
 * Created by yann on 2022/10/26
 * <p>
 * Describe:
 */
abstract class BaseResponse {
    open val code: Int? by lazy { null }
    open val msg: String? by lazy { null }
}

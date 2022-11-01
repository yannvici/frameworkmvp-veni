package com.hooooooo.android.veni.frameworkmvp.base

import android.os.Bundle

/**
 * Created by yann on 2022/10/26
 * <p>
 * Describe:
 */
interface UiCallBack {
    //初始化 savedInstanceState
    fun initBeforeView(savedInstanceState: Bundle?) = Unit

    //初始化
    fun initData(savedInstanceState: Bundle?) = Unit
}
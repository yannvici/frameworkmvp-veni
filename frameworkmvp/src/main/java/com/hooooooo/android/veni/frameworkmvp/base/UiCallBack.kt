package com.hooooooo.android.veni.frameworkmvp.base

import android.content.Intent
import android.os.Bundle
import com.hooooooo.android.veni.frameworkmvp.entry.StatusBarTheme

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

    /**
     * 画面跳转带过渡动画
     *
     * @param intent 跳转意图
     */
    fun startTransitionActivity(intent: Intent)
}
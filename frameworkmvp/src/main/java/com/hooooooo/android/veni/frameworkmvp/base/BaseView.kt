package com.hooooooo.android.veni.frameworkmvp.base

/**
 * Created by yann on 2022/10/26
 * <p>
 * Describe:
 */
interface BaseView {
    /**
     * 加载弹框
     */
    fun showLoading()

    /**
     * 关闭加载弹框
     */
    fun hideLoading()

    /**
     * 网路错误提示框
     */
    fun abnormalNet()

    /**
     * 信息提示框
     */
    fun abnormalData(message: String)
}
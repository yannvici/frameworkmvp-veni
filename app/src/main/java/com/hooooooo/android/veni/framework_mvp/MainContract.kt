package com.hooooooo.android.veni.framework_mvp

import com.hooooooo.android.veni.frameworkmvp.base.BasePresenter
import com.hooooooo.android.veni.frameworkmvp.base.BaseView

/**
     * Created by yann on 2022/10/27
 * <p>
 * Describe:
 */
class MainContract {
    class MainPresenter : BasePresenter<MainActivity>() {
        fun test1(callBAck: IMain) {
            callBAck.test("fff")
        }
    }

    interface IMain : BaseView {
        fun test(string: String)
    }
}
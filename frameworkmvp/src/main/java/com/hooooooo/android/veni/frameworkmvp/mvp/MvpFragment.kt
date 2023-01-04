package com.hooooooo.android.veni.frameworkmvp.mvp

import android.os.Bundle
import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import com.hooooooo.android.veni.frameworkmvp.base.BaseFragment
import com.hooooooo.android.veni.frameworkmvp.base.BasePresenter
import com.hooooooo.android.veni.frameworkmvp.base.UiCallBack

/**
 * Created by yann on 2022/10/27
 * <p>
 * Describe:
 */
abstract class MvpFragment<V : UiCallBack, VB : ViewBinding, P : BasePresenter<V>>(inflate: (LayoutInflater) -> VB) :
    BaseFragment<VB>(inflate), PresenterCallBack<V, P> {
    protected lateinit var presenter: P
    override fun initBeforeView(savedInstanceState: Bundle?) {
        super.initBeforeView(savedInstanceState)
        presenter = createPresenter()
        presenter.attach(registerView())
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detach()
    }
}
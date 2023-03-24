package com.hooooooo.android.veni.frameworkmvp.mvp

import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import com.hooooooo.android.veni.frameworkmvp.base.BaseActivity
import com.hooooooo.android.veni.frameworkmvp.base.BaseFragment
import com.hooooooo.android.veni.frameworkmvp.base.BasePresenter

/**
 * Created by yann on 2022/10/27
 * <p>
 * Describe:
 */
abstract class MvpFragment<V : BaseActivity<VB>, VB : ViewBinding, P : BasePresenter>(inflate: (LayoutInflater) -> VB) :
    BaseFragment<VB>(inflate), PresenterCallBack<P> {
    protected val presenter: P by lazy { createPresenter() }

}
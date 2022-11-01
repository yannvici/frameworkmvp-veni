package com.hooooooo.android.veni.frameworkmvp.base

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding


/**
 * Created by yann on 2022/10/27
 * <p>
 * Describe:
 */
abstract class BaseFragment<VB : ViewBinding> : Fragment(), UiCallBack {
    protected lateinit var viewBinding: ViewBinding
    protected lateinit var mContext: Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.window?.apply {
            requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
            setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE.or(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
            )
        }
        initBeforeView(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        viewBinding = bindFragmentViewBinding()
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData(savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is Activity) {
            mContext = context
        }
    }

    /**
     * 重写startActivity，使其具备动画
     */
    override fun startActivity(intent: Intent?) {
        super.startActivity(intent)
        activity?.overridePendingTransition(0, 0)
    }

    /**
     * 绑定视图
     */
   protected abstract fun bindFragmentViewBinding(): VB
}
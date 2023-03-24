package com.hooooooo.android.veni.framework_mvp

import android.content.Intent
import android.graphics.Color
import android.os.*
import androidx.annotation.RequiresApi
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.hooooooo.android.veni.framework_mvp.databinding.ActivityMainBinding
import com.hooooooo.android.veni.frameworkmvp.base.function.errorMsg
import com.hooooooo.android.veni.frameworkmvp.mvp.MvpActivity
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch


class MainActivity : MvpActivity<MainActivity, ActivityMainBinding, MainPresenter>(
    ActivityMainBinding::inflate,
    Color.parseColor("#FF3700B3"),
    false
) {

    @RequiresApi(Build.VERSION_CODES.P)
    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        viewBinding.main.setOnClickListener {
            startActivity(Intent().setClass(this@MainActivity, RecyclerDiffActivity::class.java))
        }
        MainScope().launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                loadData {
                    viewBinding.main.text = presenter.requestCopyWriting2().onFailure {
                        showToastDialog(it.errorMsg)
                    }.getOrNull() ?: ""
                }
            }
        }
    }

    override fun createPresenter(): MainPresenter = MainPresenter()
}
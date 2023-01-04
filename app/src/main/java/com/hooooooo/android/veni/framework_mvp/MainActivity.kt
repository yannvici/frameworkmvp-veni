package com.hooooooo.android.veni.framework_mvp

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.hooooooo.android.veni.framework_mvp.MainContract.MainPresenter
import com.hooooooo.android.veni.framework_mvp.databinding.ActivityMainBinding
import com.hooooooo.android.veni.frameworkmvp.entry.StatusBarTheme
import com.hooooooo.android.veni.frameworkmvp.mvp.MvpActivity
import com.hooooooo.android.veni.frameworkmvp.net.ServiceGenerator
import kotlinx.coroutines.launch

class MainActivity : MvpActivity<MainActivity, ActivityMainBinding, MainPresenter>(ActivityMainBinding::inflate) {

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
//        presenter.test1(this)
        ServiceGenerator.createService(ApiService::class.java)
        Log.e("context", "$context")

        lifecycleScope.launch {
            Log.e("coroutineContext1", coroutineContext.toString())
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewBinding.main.text = presenter.requestCopyWriting()
            }
        }
        viewBinding.main.setOnClickListener {
            startActivity(Intent().setClass(this@MainActivity, RecyclerDiffActivity::class.java))
        }
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        Log.e("onResume", "onResume")
    }

    override fun onStart() {
        super.onStart()
        Log.e("onStart", "onStart")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("onDestroy", "onDestroyA")
    }

    override fun setStatusBarTheme(): StatusBarTheme = StatusBarTheme(getColor(R.color.purple_500), false)
//    override fun test(string: String) {
//        viewBinding.main.text = string
//
//    }

    override fun registerView(): MainActivity = this@MainActivity
    override fun createPresenter(): MainPresenter = MainPresenter()

}
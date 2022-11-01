package com.hooooooo.android.veni.framework_mvp

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import com.hooooooo.android.veni.framework_mvp.MainContract.MainPresenter
import com.hooooooo.android.veni.framework_mvp.databinding.ActivityMainBinding
import com.hooooooo.android.veni.frameworkmvp.mvp.MvpActivity
import com.hooooooo.android.veni.frameworkmvp.entry.StatusBarTheme
import com.hooooooo.android.veni.frameworkmvp.net.ServiceGenerator

class MainActivity : MvpActivity<MainActivity, ActivityMainBinding, MainPresenter>(), MainContract.IMain {

    override fun bindActivityViewBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        presenter.test1(this)
        ServiceGenerator.createService(ApiService::class.java)
        Log.e("context", "$context")

    }

    override fun setStatusBarTheme(): StatusBarTheme = StatusBarTheme(Color.WHITE, true)
    override fun test(string: String) {
        viewBinding.main.text = string
    }

    override fun createPresenter(): MainPresenter = MainPresenter()
    override fun registerView(): MainActivity = this@MainActivity

}
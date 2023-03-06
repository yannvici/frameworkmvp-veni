package com.hooooooo.android.veni.framework_mvp

import android.content.Intent
import android.graphics.Color
import android.os.*
import android.util.Base64
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.hooooooo.android.veni.framework_mvp.MainContract.MainPresenter
import com.hooooooo.android.veni.framework_mvp.databinding.ActivityMainBinding
import com.hooooooo.android.veni.frameworkmvp.mvp.MvpActivity
import com.hooooooo.android.veni.frameworkmvp.net.ServiceGenerator
import kotlinx.coroutines.launch
import java.security.SecureRandom
import javax.crypto.KeyGenerator


class MainActivity : MvpActivity<MainActivity, ActivityMainBinding, MainPresenter>(
    ActivityMainBinding::inflate,
    Color.WHITE,
    true
) {
    private val progressDialog: ProgressDialog by lazy {
        ProgressDialog(this).apply {
            setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
            setCancelable(true)
            setTitle("正在下载")
            setMessage("请稍后...")
            setProgress(0)
            setCanceledOnTouchOutside(true)
            setIcon(R.drawable.ic_launcher_background)
//            setIndeterminate(true)
            show()
        }
    }
    private val handler: Handler by lazy {
        object : Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                progressDialog.apply {
                    setMax(100)
                    getMax()
                    setProgress(msg.what % 100)
                    setSecondaryProgress(if ((msg.what % 100) < 100) msg.what % 100 + 20 else 100)
                }
            }
        }
    }
    private val runnable: Runnable by lazy {
        Runnable {
            var i = 0
            while (true) {
                try {
                    Thread.sleep(200)
                    i++
                    handler.sendEmptyMessage(i)
                } catch (_: Exception) {
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
//        presenter.test1(this)
        ServiceGenerator.createService(ApiService::class.java)
        Log.e("context", "$context")
        hMacSHA256()
        lifecycleScope.launch {
            Log.e("coroutineContext1", coroutineContext.toString())
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
//                viewBinding.main.text = presenter.requestCopyWriting2()
                val n: String? = presenter.requestCopyWriting2()
                n?.apply {
                    viewBinding.main.setText(n)
                } ?: run {
                    viewBinding.main.setText("FFF")
                }
            }
        }
        viewBinding.main.setOnClickListener {
            startActivity(Intent().setClass(this@MainActivity, RecyclerDiffActivity::class.java))
        }

    }


    private fun secureRandomTest() {
        val output: ByteArray = ByteArray(16)
        val s = SecureRandom().nextInt(10)
        Log.e("SecureRandom", s.toString())
    }

    private fun hMacSHA256() {
        val generator: KeyGenerator = KeyGenerator.getInstance("HmacSHA256")
        val generateKey = generator.generateKey()
        val key: ByteArray = generateKey.encoded
        Log.e("key", key.contentToString())
        Log.e("hMacSHA256", Base64.encodeToString(key, Base64.DEFAULT))
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        Log.e("onResume", "onResume")
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onStart() {
        super.onStart()
        Log.e("onStart", "onStart")
//        Thread(runnable).start()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("onDestroy", "onDestroyA")
    }

//    override fun setStatusBarTheme(): StatusBarTheme = StatusBarTheme(getColor(R.color.purple_500), false)
//    override fun test(string: String) {
//        viewBinding.main.text = string
//
//    }

    override fun registerView(): MainActivity = this@MainActivity
    override fun createPresenter(): MainPresenter = MainPresenter()

}
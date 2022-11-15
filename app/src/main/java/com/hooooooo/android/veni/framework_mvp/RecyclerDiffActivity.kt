package com.hooooooo.android.veni.framework_mvp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by heyangpeng on 2022/11/9
 * <p>
 * Describe:
 */
class RecyclerDiffActivity : AppCompatActivity() {
    private val recyclerView: RecyclerView? by lazy { findViewById(R.id.ry_ts) }
    private val tv: AppCompatTextView? by lazy { findViewById(R.id.tv_test) }
    private val listString: List<String> = arrayListOf("androidx", "appcompat", "widget", "AppCompatTextView", "recyclerview", "Test1", "Test2")
    private val listStringAnther: List<String> =
        arrayListOf("androidx1", "appcompat1", "DividerItemDecoration1", "AppCompatTextView1", "Test111", "Test21")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_diff_test)
        recyclerView?.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        val adapter = RecyclerDiffAdapter()
        adapter.submitData(listString)
        recyclerView?.adapter = adapter
        tv?.setOnClickListener {
            adapter.setItem(2, "TEST!!")
            adapter.addData(listStringAnther )
        }
    }
}
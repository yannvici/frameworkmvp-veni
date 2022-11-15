package com.hooooooo.android.veni.frameworkmvp.base

import android.util.Log
import android.view.View
import androidx.annotation.IntRange
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

/**
 * Created by yann on 2022/11/9
 * <p>
 * Describe:
 */
abstract class BaseAdapter<VB : ViewBinding, T : Any, VH : BaseAdapter<VB, T, VH>.BaseViewHolder<VB>>
    : RecyclerView.Adapter<VH>() {
    companion object {
        private const val TAG: String = "BaseAdapter"
    }

    private var data: MutableList<T> = ArrayList()

    /** 标记对象 */
    private var tag: Any? = null

    private val mDiff: AsyncListDiffer<T> = AsyncListDiffer(this@BaseAdapter, initItemCallback())

    abstract fun initItemCallback(): DiffUtil.ItemCallback<T>

    override fun onBindViewHolder(holder: VH, position: Int) = holder.onBindView(position)

    override fun getItemCount(): Int = getCount()

    open fun submitData(data: List<T>) {
        this.data = data.toMutableList()
        mDiff.submitList(data)
    }

    open fun getCount(): Int = mDiff.currentList.size

    /**
     * 获取当前数据
     */
    open fun getData(): MutableList<T> {
        return this.data
    }

    /**
     * 追加一些数据
     */
    open fun addData(data: List<T>) {
        this.data.addAll(data)
        mDiff.submitList(this.data)
    }

    /**
     * 清空当前数据
     */
    open fun clearData() {
        this.data.clear()
        mDiff.submitList(data)
    }

    /**
     * 获取某个位置上的数据
     */
    open fun getItem(@IntRange(from = 0) position: Int): T? = if (position < this.data.size) {
        mDiff.currentList[position]
    } else {
        Log.d(TAG, "坐标超界")
        null
    }


    /**
     * 更新某个位置上的数据
     */
    open fun setItem(@IntRange(from = 0) position: Int, item: T) {
        if (position < this.data.size) {
            this.data[position] = item
            mDiff.submitList(this.data)
        } else {
            Log.d(TAG, "坐标超界")
        }
    }

    /**
     * 添加单条数据
     */
    open fun addItem(item: T) {
        addItem(this.data.size, item)
    }

    open fun addItem(@IntRange(from = 0) position: Int, item: T) {
        if (position < this.data.size) {
            this.data.add(position, item)
        } else {
            this.data.add(item)
        }
        mDiff.submitList(this.data)
    }

    /**
     * 删除单条数据
     */
    open fun removeItem(item: T) {
        val index = this.data.indexOf(item)
        if (index != -1) {
            removeItem(index)
        } else {
            Log.d(TAG, "值不存在")
        }
    }

    open fun removeItem(@IntRange(from = 0) position: Int) {
        if (position < this.data.size) {
            this.data.removeAt(position)
            mDiff.submitList(this.data)
        } else {
            Log.d(TAG, "坐标超界")
        }

    }

    /**
     * 获取标记
     */
    open fun getTag(): Any? {
        return tag
    }

    /**
     * 设置标记
     */
    open fun setTag(tag: Any) {
        this.tag = tag
    }

    abstract inner class BaseViewHolder<VB : ViewBinding> constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        protected lateinit var itemViewBinding: VB


        constructor(itemViewBinding: VB) : this(itemViewBinding.root) {
            this.itemViewBinding = itemViewBinding
        }

        open fun getItemView(): View = itemViewBinding.root

        /**
         * 数据绑定回调
         */
        abstract fun onBindView(position: Int)

    }
}
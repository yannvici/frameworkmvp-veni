package com.hooooooo.android.veni.framework_mvp

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import com.hooooooo.android.veni.framework_mvp.RecyclerDiffAdapter.RecyclerDiffViewHolder
import com.hooooooo.android.veni.framework_mvp.databinding.LayoutItemBinding
import com.hooooooo.android.veni.frameworkmvp.base.BaseAdapter

/**
 * Created by heyangpeng on 2022/11/9
 * <p>
 * Describe:
 */
class RecyclerDiffAdapter :
    BaseAdapter<LayoutItemBinding, String, RecyclerDiffViewHolder>() {
    private lateinit var mContext: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerDiffViewHolder {
        mContext = parent.context
        return RecyclerDiffViewHolder(
            LayoutItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    inner class RecyclerDiffViewHolder(itemViewBinding: LayoutItemBinding) :
        BaseViewHolder<LayoutItemBinding>(itemViewBinding) {
        override fun onBindView(position: Int) {
            itemViewBinding.tes.text = getItem(position)
            itemView.setOnClickListener {
                Toast.makeText(mContext, getItem(position), Toast.LENGTH_SHORT).show()

            }
        }
    }

//    inner class RecyclerDiffViewHolder1 : BaseViewHolder<LayoutItemBinding>(R.layout.layout_item) {
//        private val tes: AppCompatTextView? by lazy { findViewById(R.id.tes) }
//        override fun onBindView(position: Int) {
//            tes?.text = getItem(position)
//            tes?.setOnClickListener {
//                Toast.makeText(mContext, getItem(position), Toast.LENGTH_SHORT).show()
//            }
//        }
//    }

    override fun initItemCallback(): DiffUtil.ItemCallback<String> {
        return object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }
        }
    }
}
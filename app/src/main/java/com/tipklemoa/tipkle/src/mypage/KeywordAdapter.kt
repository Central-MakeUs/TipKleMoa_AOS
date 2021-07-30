package com.tipklemoa.tipkle.src.mypage

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.tipklemoa.tipkle.R
import com.tipklemoa.tipkle.src.mypage.model.ResultKeyword
import com.tipklemoa.tipkle.src.tipkle.model.ResultTipFolder

class KeywordAdapter(val context: Context, private val keywordList: List<ResultKeyword>):
    RecyclerView.Adapter<KeywordAdapter.ItemViewHolder>(){
    private var listener: OnItemClickListener? = null

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvKeyword = itemView.findViewById<TextView>(R.id.tvKeyword)
        //private val btnX = itemView.findViewById<ImageView>(R.id.btnKeywordX)

        fun bind(keyword: ResultKeyword, context: Context) {

            tvKeyword.text = keyword.keyword
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): KeywordAdapter.ItemViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_keyword_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: KeywordAdapter.ItemViewHolder, position: Int) {
        holder.bind(keywordList[position], context)
        holder.itemView.findViewById<ImageView>(R.id.btnKeywordX).setOnClickListener {
            listener!!.onClicked(keywordList[position].keywordId)
        }
    }

    override fun getItemCount(): Int {
        return keywordList.size
    }

    fun setOnItemClickListener(listener:OnItemClickListener){
        this.listener = listener
    }

    interface OnItemClickListener{
        fun onClicked(folderId:Int)
    }
}
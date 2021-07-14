package com.tipklemoa.tipkle.src.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.tipklemoa.tipkle.R
import com.tipklemoa.tipkle.src.home.model.ResultHomePreviewFeed
import com.bumptech.glide.request.RequestOptions
import com.tipklemoa.tipkle.src.search.model.ResultKeyword
import org.w3c.dom.Text

class KeywordAdapter(val context: Context, val rankNumList:List<Int>, private val keywordList: List<ResultKeyword>):
    RecyclerView.Adapter<KeywordAdapter.ItemViewHolder>(){

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvRankNum = itemView.findViewById<TextView>(R.id.tvKeywordRankNum)
        private val tvRankKeyword = itemView.findViewById<TextView>(R.id.tvRankKeyword)

        fun bind(keyword: ResultKeyword, rank:Int, context: Context) {

            if (rank==1 || rank==2 || rank==3){
                tvRankNum.setTextColor(context.resources.getColor(R.color.mint))
            }
            tvRankNum.text = rank.toString()
            tvRankKeyword.text = keyword.keyword //멤버 이름.

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
        holder.bind(keywordList[position], rankNumList[position], context)
    }

    override fun getItemCount(): Int {
        return keywordList.size
    }
}
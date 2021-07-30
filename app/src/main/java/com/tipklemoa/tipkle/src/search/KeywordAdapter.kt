package com.tipklemoa.tipkle.src.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tipklemoa.tipkle.R
import com.tipklemoa.tipkle.src.search.model.ResultKeyword

class KeywordAdapter(val context: Context, val rankNumList:List<Int>?=null, private val keywordList: List<ResultKeyword>):
    RecyclerView.Adapter<KeywordAdapter.ItemViewHolder>(){
    private var listener: OnItemClickListener? = null

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvRankNum = itemView.findViewById<TextView>(R.id.tvKeywordRankNum)
        private val tvRankKeyword = itemView.findViewById<TextView>(R.id.tvRankKeyword)
        private val imgSearchIcon = itemView.findViewById<ImageView>(R.id.imgSearchIcon)

        fun bind(keyword: ResultKeyword, rank:Int?, context: Context) {

            if (rankNumList!=null) {
                imgSearchIcon.visibility = View.INVISIBLE
                if (rank == 1 || rank == 2 || rank == 3) {
                    tvRankNum.setTextColor(context.resources.getColor(R.color.mint))
                }
                tvRankNum.text = rank.toString()
            }
            else{
                imgSearchIcon.visibility = View.VISIBLE
                tvRankNum.visibility = View.INVISIBLE
            }
            tvRankKeyword.text = keyword.keyword //멤버 이름.
            tvRankKeyword.setOnClickListener {
                listener!!.onClicked(keyword.keyword)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,

        viewType: Int
    ): KeywordAdapter.ItemViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_search_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: KeywordAdapter.ItemViewHolder, position: Int) {
        if (rankNumList!=null) {
            holder.bind(keywordList[position], rankNumList[position], context)
        }
        else{
            holder.bind(keywordList[position], null, context)
        }
    }

    override fun getItemCount(): Int {
        return keywordList.size
    }

    fun setOnItemClickListener(listener:OnItemClickListener){
        this.listener = listener
    }

    interface OnItemClickListener{
        fun onClicked(keyword:String)
    }
}
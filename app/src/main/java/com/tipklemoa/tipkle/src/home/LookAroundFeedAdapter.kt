package com.tipklemoa.tipkle.src.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tipklemoa.tipkle.R
import com.tipklemoa.tipkle.src.home.model.ResultLookAround
import androidx.recyclerview.widget.LinearLayoutManager


class LookAroundFeedAdapter(val context: Context, private val feedList: List<ResultLookAround>):
    RecyclerView.Adapter<LookAroundFeedAdapter.ItemViewHolder>(){

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imgProfile = itemView.findViewById<ImageView>(R.id.imgLookAroundProfile)
        private val tvLookAroundUserName = itemView.findViewById<TextView>(R.id.tvLookAroundUserName)
        private val tvLookAroundTime = itemView.findViewById<TextView>(R.id.tvLookAroundTime)
        private val ratingBar = itemView.findViewById<RatingBar>(R.id.ratingBar)
        private val tvRatingBar = itemView.findViewById<TextView>(R.id.tvRatingFloat)
        private val tvWhen = itemView.findViewById<TextView>(R.id.tvLookAroundWhen)
        private val tvHow = itemView.findViewById<TextView>(R.id.tvLookAroundHow)
        private val tvLookAroundtext = itemView.findViewById<TextView>(R.id.tvLookAroundText)
        private val imgFeed = itemView.findViewById<RecyclerView>(R.id.rvLookAroundImageSlide)

        fun bind(feed: ResultLookAround, context: Context) {

            Glide
                .with(context)
                .load(feed.profileImgUrl)
                .into(imgProfile) // 프로필

            tvLookAroundUserName.text = feed.nickName //닉네임

            tvLookAroundTime.text = feed.createdAt //시간
            ratingBar.numStars = feed.star //ratingbar
            tvRatingBar.text = feed.score //점수 (소수점)
            tvWhen.text = feed.whenText
            tvHow.text = feed.howText
            tvLookAroundtext.text = feed.description

            //이미지 피드
            val layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            imgFeed.layoutManager = layoutManager
            var adapter = LookAroundImageAdapter(context, feed.imgUrl)
            imgFeed.adapter = adapter
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LookAroundFeedAdapter.ItemViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_home_lookaround, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: LookAroundFeedAdapter.ItemViewHolder, position: Int) {
        holder.bind(feedList[position], context)
    }

    override fun getItemCount(): Int {
        return feedList.size
    }
}
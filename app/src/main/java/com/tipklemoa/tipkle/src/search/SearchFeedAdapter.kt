package com.tipklemoa.tipkle.src.search

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tipklemoa.tipkle.R
import androidx.recyclerview.widget.LinearLayoutManager
import com.tipklemoa.tipkle.src.main.FeedDetailActivity
import com.tipklemoa.tipkle.src.home.LookAroundImageAdapter
import com.tipklemoa.tipkle.src.search.model.ResultSearch

class SearchFeedAdapter(val context: Context, private val searchList: List<ResultSearch>):
    RecyclerView.Adapter<SearchFeedAdapter.ItemViewHolder>(){

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imgProfile = itemView.findViewById<ImageView>(R.id.imgLookAroundProfile)
        private val tvSearchUserName = itemView.findViewById<TextView>(R.id.tvLookAroundUserName)
        private val tvLookAroundTime = itemView.findViewById<TextView>(R.id.tvLookAroundTime)
        private val ratingBar = itemView.findViewById<RatingBar>(R.id.ratingBar)
        private val tvRatingBar = itemView.findViewById<TextView>(R.id.tvRatingFloat)
        private val tvWhen = itemView.findViewById<TextView>(R.id.tvLookAroundWhen)
        private val tvHow = itemView.findViewById<TextView>(R.id.tvLookAroundHow)
        private val tvLookAroundtext = itemView.findViewById<TextView>(R.id.tvLookAroundText)
        private val imgFeed = itemView.findViewById<RecyclerView>(R.id.rvLookAroundImageSlide)
        private val layoutWhenToImage = itemView.findViewById<ConstraintLayout>(R.id.layoutwhentoimage)

        fun bind(feed: ResultSearch, context: Context) {

            Glide
                .with(context)
                .load(feed.profileImgUrl)
                .circleCrop()
                .error(R.drawable.ic_img_profile)
                .into(imgProfile) // ?????????

            tvSearchUserName.text = feed.nickName //?????????

            tvLookAroundTime.text = feed.createdAt //??????
            ratingBar.rating = feed.star.toFloat() //ratingbar
            tvRatingBar.text = feed.score //?????? (?????????)
            tvWhen.text = feed.whenText
            tvHow.text = feed.howText
            tvLookAroundtext.text = feed.description

            if (feed.description==""){
                tvLookAroundtext.visibility = View.GONE
            }

            //????????? ??????
            val layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            imgFeed.layoutManager = layoutManager
            var adapter = LookAroundImageAdapter(context, feed.imgUrl)
            imgFeed.adapter = adapter

            layoutWhenToImage.setOnClickListener {
                val intent = Intent(context, FeedDetailActivity::class.java)
                intent.putExtra("postId", feed.postId)
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchFeedAdapter.ItemViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_home_lookaround, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchFeedAdapter.ItemViewHolder, position: Int) {
        holder.bind(searchList[position], context)
//        holder.itemView.setOnClickListener {
//            val intent = Intent(context, FeedDetailActivity::class.java)
//            intent.putExtra("postId", searchList[position].postId)
//            context.startActivity(intent)
//        }
    }

    override fun getItemCount(): Int {
        return searchList.size
    }
}
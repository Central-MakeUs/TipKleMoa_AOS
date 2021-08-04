package com.tipklemoa.tipkle.src.home

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
import com.tipklemoa.tipkle.src.home.model.ResultLookAround
import androidx.recyclerview.widget.LinearLayoutManager
import com.tipklemoa.tipkle.src.FeedDetailActivity

class LookAroundFeedAdapter(val context: Context, private val feedList: List<ResultLookAround>):
    RecyclerView.Adapter<LookAroundFeedAdapter.ItemViewHolder>(){

    var postId = 0
    private var listener: OnItemClickListener? = null

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
        private val layoutWhenToImage = itemView.findViewById<ConstraintLayout>(R.id.layoutwhentoimage)

        fun bind(feed: ResultLookAround, context: Context) {

            postId = feed.postId

            Glide
                .with(context)
                .load(feed.profileImgUrl)
                .error(R.drawable.ic_img_profile)
                .circleCrop()
                .into(imgProfile) // 프로필

            tvLookAroundUserName.text = feed.nickName //닉네임

            tvLookAroundTime.text = feed.createdAt //시간
            ratingBar.rating = feed.star.toFloat() //ratingbar
            tvRatingBar.text = feed.score //점수 (소수점)
            tvWhen.text = feed.whenText
            tvHow.text = feed.howText
            tvLookAroundtext.text = feed.description
            if (feed.description==""){
                tvLookAroundtext.visibility = View.GONE
            }
            else{
                tvLookAroundtext.visibility = View.VISIBLE
            }

            //이미지 피드
            val layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            imgFeed.layoutManager = layoutManager
            val adapter = LookAroundImageAdapter(context, feed.imgUrl)
            imgFeed.adapter = adapter

            layoutWhenToImage.setOnClickListener {
                val intent = Intent(context, FeedDetailActivity::class.java)
                intent.putExtra("postId", feed.postId)
                context.startActivity(intent)
            }

            imgFeed.setOnClickListener {
                val intent = Intent(context, FeedDetailActivity::class.java)
                intent.putExtra("postId", feed.postId)
                context.startActivity(intent)
            }

            itemView.setOnClickListener {
                listener!!.onClicked(feed.postId)
            }
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
        holder.itemView.setOnClickListener {
            val intent = Intent(context, FeedDetailActivity::class.java)
            intent.putExtra("postId", feedList[position].postId)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return feedList.size
    }

    fun setOnItemClickListener(listener:OnItemClickListener){
        this.listener = listener
    }

    interface OnItemClickListener{
        fun onClicked(position: Int)
    }
}
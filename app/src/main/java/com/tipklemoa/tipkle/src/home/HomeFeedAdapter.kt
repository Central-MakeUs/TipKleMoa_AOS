package com.tipklemoa.tipkle.src.home

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





class HomeFeedAdapter(val context: Context, private val feedList: List<ResultHomePreviewFeed>):
    RecyclerView.Adapter<HomeFeedAdapter.ItemViewHolder>(){

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvFeedTitle = itemView.findViewById<TextView>(R.id.tvHomeFeedTitle)
        private val imgFeed = itemView.findViewById<ImageView>(R.id.imgHomeFeed)

        fun bind(feed: ResultHomePreviewFeed, context: Context) {
//            val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
//            val width = (windowManager.defaultDisplay.width * 0.1).toInt()
//
//            itemView.layoutParams = RecyclerView.LayoutParams(
//                width,
//                RecyclerView.LayoutParams.MATCH_PARENT
//            )

            var requestOptions = RequestOptions()
            requestOptions = requestOptions.transform(CenterCrop(), RoundedCorners(8))


            tvFeedTitle.text = feed.title //멤버 이름
            Glide
                .with(context)
                .load(feed.thumbnailUrl)
                .apply(requestOptions)
                .into(imgFeed) //멤버 프로필
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeFeedAdapter.ItemViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_home_preview_feed, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeFeedAdapter.ItemViewHolder, position: Int) {
        holder.bind(feedList[position], context)
    }

    override fun getItemCount(): Int {
        return feedList.size
    }
}
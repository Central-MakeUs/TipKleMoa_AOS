package com.tipklemoa.tipkle.src.tipkle

import android.content.Context
import android.content.Intent
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
import com.tipklemoa.tipkle.src.FeedDetailActivity
import com.tipklemoa.tipkle.src.tipkle.model.Post
import com.tipklemoa.tipkle.src.tipkle.model.PostInfo

class TipkleFolderFeedAdapter(val context: Context, private val postList: List<Post>):
    RecyclerView.Adapter<TipkleFolderFeedAdapter.ItemViewHolder>(){

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imgFolderFeed = itemView.findViewById<ImageView>(R.id.imgFolderFeed)
        private val tvFolderFeedName = itemView.findViewById<TextView>(R.id.tvFolderFeedName)


        fun bind(post: Post, context: Context) {
            var requestOptions = RequestOptions()
            requestOptions = requestOptions.transform(CenterCrop(), RoundedCorners(6))

            Glide
                .with(context)
                .load(post.imgUrl)
                .apply(requestOptions)
                .into(imgFolderFeed) //이미지리스트

            tvFolderFeedName.text = post.title
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TipkleFolderFeedAdapter.ItemViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_folder_feed, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: TipkleFolderFeedAdapter.ItemViewHolder, position: Int) {
        holder.bind(postList[position], context)
        holder.itemView.setOnClickListener {
            val intent = Intent(context, FeedDetailActivity::class.java)
            intent.putExtra("postId", postList[position].postId)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return postList.size
    }
}
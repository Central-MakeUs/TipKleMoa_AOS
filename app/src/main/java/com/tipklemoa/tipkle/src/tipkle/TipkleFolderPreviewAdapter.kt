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
import com.tipklemoa.tipkle.src.tipkle.model.PostInfo

class TipkleFolderPreviewAdapter(val context: Context, private val postInfoList: List<PostInfo>):
    RecyclerView.Adapter<TipkleFolderPreviewAdapter.ItemViewHolder>(){

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imgFolderPreview = itemView.findViewById<ImageView>(R.id.imgFolderImage)

        fun bind(postInfo: PostInfo, context: Context) {
            var requestOptions = RequestOptions()
            requestOptions = requestOptions.transform(CenterCrop(), RoundedCorners(6))

            Glide
                .with(context)
                .load(postInfo.imgUrl)
                .apply(requestOptions)
                .into(imgFolderPreview) //이미지리스트

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TipkleFolderPreviewAdapter.ItemViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_folder_preview, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: TipkleFolderPreviewAdapter.ItemViewHolder, position: Int) {
        holder.bind(postInfoList[position], context)
        holder.itemView.setOnClickListener {
            val intent = Intent(context, FeedDetailActivity::class.java)
            intent.putExtra("postId", postInfoList[position].postId)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return postInfoList.size
    }
}
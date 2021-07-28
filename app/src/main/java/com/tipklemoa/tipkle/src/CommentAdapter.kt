package com.tipklemoa.tipkle.src

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
import com.tipklemoa.tipkle.src.model.ResultComment
import com.tipklemoa.tipkle.src.tipkle.model.ResultTipFolder

class CommentAdapter(val context: Context, private val commentList: List<ResultComment>):
    RecyclerView.Adapter<CommentAdapter.ItemViewHolder>(){
//    private var listener: OnItemClickListener? = null

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvCommentName = itemView.findViewById<TextView>(R.id.tvCommentName)
        private val imgCommentProfile = itemView.findViewById<ImageView>(R.id.imgCommentProfile)
        private val tvCommentContent = itemView.findViewById<TextView>(R.id.tvCommentContent)
        private val tvCommentTime = itemView.findViewById<TextView>(R.id.tvCommentTime)

        fun bind(comment: ResultComment, context: Context) {
//
//            var requestOptions = RequestOptions()
//            requestOptions = requestOptions.transform(CenterCrop(), RoundedCorners(6))

            tvCommentName.text = comment.nickName
            tvCommentContent.text = comment.content
            tvCommentTime.text = comment.createdAt

             Glide
                    .with(context)
                    .load(comment.profileImgUrl)
                    .into(imgCommentProfile)
//            if (comment.isAuthor=='Y'){
//
//            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CommentAdapter.ItemViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_comment, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommentAdapter.ItemViewHolder, position: Int) {
        holder.bind(commentList[position], context)
//        holder.itemView.setOnClickListener {
//            listener!!.onClicked(commentList[position].folderId)
//        }
    }

    override fun getItemCount(): Int {
        return commentList.size
    }

//    fun setOnItemClickListener(listener:OnItemClickListener){
//        this.listener = listener
//    }
//
//    interface OnItemClickListener{
//        fun onClicked(folderId:Int)
//    }
}
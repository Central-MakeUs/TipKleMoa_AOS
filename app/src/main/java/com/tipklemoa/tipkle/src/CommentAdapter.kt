package com.tipklemoa.tipkle.src

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tipklemoa.tipkle.R
import com.tipklemoa.tipkle.src.model.ResultComment

class CommentAdapter(val context: Context, private val commentList: List<ResultComment>):
    RecyclerView.Adapter<CommentAdapter.ItemViewHolder>(){
    private var listener: OnItemClickListener? = null

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
                    .circleCrop()
                    .error(R.drawable.ic_img_profile)
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
        holder.itemView.findViewById<Button>(R.id.btnCommentEdit).setOnClickListener {
            listener!!.onClicked(commentList[position].commentId, commentList[position].isAuthor)
        }
    }

    override fun getItemCount(): Int {
        return commentList.size
    }

    fun setOnItemClickListener(listener:OnItemClickListener){
        this.listener = listener
    }

    interface OnItemClickListener{
        fun onClicked(commentId:Int, isAuthor:Char)
    }
}
package com.tipklemoa.tipkle.src.main

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
import com.bumptech.glide.request.RequestOptions
import com.tipklemoa.tipkle.R
import com.tipklemoa.tipkle.src.tipkle.model.ResultTipFolder

class BookMarkFolderAdapter(val context: Context, private val folderList: List<ResultTipFolder>):
    RecyclerView.Adapter<BookMarkFolderAdapter.ItemViewHolder>(){
    private var listener: OnItemClickListener? = null

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvBookMarkTitle = itemView.findViewById<TextView>(R.id.tvBookMarkTitle)
        private val imgBookMarkFeed = itemView.findViewById<ImageView>(R.id.imgBookMarkFeed)

        fun bind(folder: ResultTipFolder, context: Context) {

            var requestOptions = RequestOptions()
            requestOptions = requestOptions.transform(CenterCrop(), RoundedCorners(6))

            tvBookMarkTitle.text = folder.folderName

            if (folder.postsInfo.isNotEmpty()){
                Glide
                    .with(context)
                    .load(folder.postsInfo[0].imgUrl)
                    .apply(requestOptions)
                    .into(imgBookMarkFeed) //이미지리스트
            }
            else{
                Glide
                    .with(context)
                    .load(R.drawable.ic_empty_folder)
                    .apply(requestOptions)
                    .into(imgBookMarkFeed)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_bookmark_folder_list, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(folderList[position], context)
        holder.itemView.setOnClickListener {
            listener!!.onClicked(folderList[position].folderId)
        }
    }

    override fun getItemCount(): Int {
        return folderList.size
    }

    fun setOnItemClickListener(listener: OnItemClickListener){
        this.listener = listener
    }

    interface OnItemClickListener{
        fun onClicked(folderId:Int)
    }
}
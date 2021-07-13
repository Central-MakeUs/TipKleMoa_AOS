package com.tipklemoa.tipkle.src.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tipklemoa.tipkle.R

class LookAroundImageAdapter(val context: Context, private val imageList: List<String>):
    RecyclerView.Adapter<LookAroundImageAdapter.ItemViewHolder>(){

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imgLookAround = itemView.findViewById<ImageView>(R.id.imgLookAround)

        fun bind(image:String, context: Context) {

            Glide
                .with(context)
                .load(image)
                .into(imgLookAround) // 프로필

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_lookaround_image, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(imageList[position], context)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }
}
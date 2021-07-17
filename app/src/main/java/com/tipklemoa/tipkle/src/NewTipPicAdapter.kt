package com.tipklemoa.tipkle.src

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tipklemoa.tipkle.R

class NewTipPicAdapter(val context: Context, private val searchList: List<String>):
    RecyclerView.Adapter<NewTipPicAdapter.ItemViewHolder>(){
    private var listener: OnItemClickListener? = null

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imgTip = itemView.findViewById<ImageView>(R.id.imgNewTipPic)
        private val btnNewTipX = itemView.findViewById<Button>(R.id.btnNewTipX)

        fun bind(image: String, context: Context) {

            Glide
                .with(context)
                .load(image)
                .into(imgTip) // 이미지리스트

            btnNewTipX.setOnClickListener {
                listener!!.onClicked(adapterPosition)
            }

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NewTipPicAdapter.ItemViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_newtip_picitem, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewTipPicAdapter.ItemViewHolder, position: Int) {
        holder.bind(searchList[position], context)
    }

    override fun getItemCount(): Int {
        return searchList.size
    }

    fun setOnButtonClickListener(listener:OnItemClickListener){
        this.listener = listener
    }

    interface OnItemClickListener{
        fun onClicked(position: Int)
    }
}
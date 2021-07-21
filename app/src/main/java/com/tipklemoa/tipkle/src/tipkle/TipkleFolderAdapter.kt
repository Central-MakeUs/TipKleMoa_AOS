package com.tipklemoa.tipkle.src.tipkle

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tipklemoa.tipkle.R
import com.tipklemoa.tipkle.src.tipkle.model.ResultTipFolder

class TipkleFolderAdapter(val context: Context, private val folderList: List<ResultTipFolder>):
    RecyclerView.Adapter<TipkleFolderAdapter.ItemViewHolder>(){

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvFolderName = itemView.findViewById<TextView>(R.id.tvTipkleFolderName)
        private val rvFolderPreview = itemView.findViewById<RecyclerView>(R.id.rvFolderPreView)
        private val imgFolderEmptyPreview = itemView.findViewById<ImageView>(R.id.imgFolderEmptyPreview)

        fun bind(folder: ResultTipFolder, context: Context) {

            tvFolderName.text = folder.folderName
            if (folder.postInfo.isNullOrEmpty()){
                imgFolderEmptyPreview.visibility = View.VISIBLE
            }
            else{
                imgFolderEmptyPreview.visibility = View.INVISIBLE
                val layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                rvFolderPreview.layoutManager = layoutManager
                val adapter = TipkleFolderPreviewAdapter(context, folder.postInfo)
                rvFolderPreview.adapter = adapter
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TipkleFolderAdapter.ItemViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_tip_folder, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: TipkleFolderAdapter.ItemViewHolder, position: Int) {
        holder.bind(folderList[position], context)
    }

    override fun getItemCount(): Int {
        return folderList.size
    }
}
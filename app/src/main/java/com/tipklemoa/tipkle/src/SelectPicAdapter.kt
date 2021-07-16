package com.tipklemoa.tipkle.src

import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.marginRight
import androidx.core.view.setPadding
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.tipklemoa.tipkle.R
import com.tipklemoa.tipkle.src.home.model.ResultHomePreviewFeed
import com.bumptech.glide.request.RequestOptions

class SelectPicAdapter(val context: Context, private val imageUriList: ArrayList<String>):
    BaseAdapter() {

    var visibleFlag = false

    private var items = ArrayList<String>()

    init {
        this.items = imageUriList
    }

    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(position: Int): Any {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun updateView(flag: Boolean) {
        visibleFlag = flag
    }

    override fun getView(p: Int, convertView: View?, parent: ViewGroup?): View {

        val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val gridView = layoutInflater.inflate(R.layout.layout_gallery_item, null)
        val imageView = gridView.findViewById<ImageView>(R.id.imgGalleryItem)
        val frame = gridView.findViewById<ImageView>(R.id.imgFrame)
        val text = gridView.findViewById<TextView>(R.id.tvSelectImageCount)

        val display = context.resources.displayMetrics
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        imageView.setPadding(2)
        imageView.layoutParams = LinearLayout.LayoutParams(display.widthPixels / 3,
            display.widthPixels / 3)

        if (items[p]=="1"){
            Glide.with(context).load(R.drawable.ic_album_box).into(imageView)
        }
        else{
            Glide
                .with(context)
                .load(items[p])
                .into(imageView)
        }
        return imageView
    }
}
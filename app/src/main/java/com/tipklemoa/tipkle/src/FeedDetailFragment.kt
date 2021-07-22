package com.tipklemoa.tipkle.src

import android.content.Intent
import android.os.Bundle
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.tipklemoa.tipkle.R
import com.tipklemoa.tipkle.config.BaseFragment
import com.tipklemoa.tipkle.databinding.LayoutBannerBinding
import com.tipklemoa.tipkle.databinding.LayoutDetailFeedViewpagerBinding
import com.tipklemoa.tipkle.databinding.ViewpagerPickedTipTabBinding
import com.tipklemoa.tipkle.src.home.model.BannerResponse
import com.tipklemoa.tipkle.src.home.model.CategoryListResponse

class FeedDetailFragment(private var image:String) : BaseFragment<LayoutDetailFeedViewpagerBinding>(LayoutDetailFeedViewpagerBinding::bind,
    R.layout.layout_detail_feed_viewpager) {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        Glide.with(this)
            .load(image)
            .into(binding.feedimageitem)

        binding.feedimageitem.setOnClickListener {
            val intent = Intent(requireContext(), ViewOriginalImageActivity::class.java)
            intent.putExtra("image", image)
            startActivity(intent)
        }
    }
}
package com.tipklemoa.tipkle.src.home

import android.os.Bundle
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.tipklemoa.tipkle.R
import com.tipklemoa.tipkle.config.BaseFragment
import com.tipklemoa.tipkle.databinding.LayoutBannerBinding
import com.tipklemoa.tipkle.databinding.ViewpagerPickedTipTabBinding
import com.tipklemoa.tipkle.src.home.model.BannerResponse
import com.tipklemoa.tipkle.src.home.model.CategoryListResponse

class HomeBannerFragment(private var image:String, var title:String) : BaseFragment<LayoutBannerBinding>(LayoutBannerBinding::bind,
    R.layout.layout_banner) {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        Glide.with(this)
            .load(image)
            .into(binding.banneritem)

        binding.bannerTitle.text = title
    }
}
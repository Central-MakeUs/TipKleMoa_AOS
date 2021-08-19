package com.tipklemoa.tipkle.src.home

import android.content.Intent
import android.os.Bundle
import com.bumptech.glide.Glide
import com.tipklemoa.tipkle.R
import com.tipklemoa.tipkle.config.BaseFragment
import com.tipklemoa.tipkle.databinding.LayoutBannerBinding
import com.tipklemoa.tipkle.src.main.FeedDetailActivity

class HomeBannerFragment(private var image:String, var title:String, var postId:Int) : BaseFragment<LayoutBannerBinding>(LayoutBannerBinding::bind,
    R.layout.layout_banner) {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        Glide.with(this)
            .load(image)
            .into(binding.banneritem)

        //binding.banneritem.alpha = 0.7F

        binding.bannerTitle.text = title

        binding.banneritem.setOnClickListener {
            val intent = Intent(requireContext(), FeedDetailActivity::class.java)
            intent.putExtra("postId", postId)
            startActivity(intent)
        }
    }
}
package com.tipklemoa.tipkle.src.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.tipklemoa.tipkle.src.home.model.ResultBanner

class HomeBannerFragmentStateAdapter(fragmentActivity: FragmentActivity, val bannerList:List<ResultBanner>): FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> HomeBannerFragment(bannerList[0].thumbnailUrl, bannerList[0].title, bannerList[0].postId)
            1 -> HomeBannerFragment(bannerList[1].thumbnailUrl, bannerList[1].title, bannerList[1].postId)
            2 -> HomeBannerFragment(bannerList[2].thumbnailUrl, bannerList[2].title, bannerList[2].postId)
            else -> HomeBannerFragment(bannerList[3].thumbnailUrl, bannerList[3].title, bannerList[3].postId)
        }
    }
}

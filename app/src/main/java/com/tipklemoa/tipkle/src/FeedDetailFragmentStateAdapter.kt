package com.tipklemoa.tipkle.src

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.tipklemoa.tipkle.src.home.model.ResultBanner
import com.tipklemoa.tipkle.src.model.ResultDetailFeed

class FeedDetailFragmentStateAdapter(fragmentActivity: FragmentActivity, val detailImageList:List<String>): FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = detailImageList.size

    override fun createFragment(position: Int): Fragment {
        return FeedDetailFragment(detailImageList[position])
    }
}

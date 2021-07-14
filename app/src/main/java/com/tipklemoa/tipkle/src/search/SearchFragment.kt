package com.tipklemoa.tipkle.src.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.tipklemoa.tipkle.R
import com.tipklemoa.tipkle.config.BaseFragment
import com.tipklemoa.tipkle.databinding.FragmentHomeBinding
import com.tipklemoa.tipkle.databinding.FragmentSearchBinding
import com.tipklemoa.tipkle.src.PagerFragmentStateAdapter
import com.tipklemoa.tipkle.src.home.PickedTipFragment
import com.tipklemoa.tipkle.src.home.TodayTipFragment

class SearchFragment : BaseFragment<FragmentSearchBinding>(
    FragmentSearchBinding::bind,
    R.layout.fragment_search
){
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val pagerAdapter = PagerFragmentStateAdapter(requireActivity())
        // 2개의 Fragment Add
        pagerAdapter.addFragment(PopularKeywordFragment())
        pagerAdapter.addFragment(RecentKeywordFragment())
        // Adapter
        binding.searchViewPager.adapter = pagerAdapter

        binding.searchViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
            }
        })

        val tabTextArray = arrayOf("인기 검색어", "최근 검색어")
        // TabLayout attach
        TabLayoutMediator(binding.searchTopTab, binding.searchViewPager) { tab, position ->
            tab.text = tabTextArray[position]
        }.attach()

        binding.searchViewPager.isUserInputEnabled = false
    }
}
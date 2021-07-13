package com.tipklemoa.tipkle.src.home

import android.os.Bundle
import com.tipklemoa.tipkle.R
import com.tipklemoa.tipkle.config.BaseFragment
import com.tipklemoa.tipkle.databinding.ViewpagerTodayTipTabBinding

class TodaytipViewPagerFragment : BaseFragment<ViewpagerTodayTipTabBinding>(
    ViewpagerTodayTipTabBinding::bind,
    R.layout.viewpager_today_tip_tab
){
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

//        val pagerAdapter = PagerFragmentStateAdapter(requireActivity())
//        // 2개의 Fragment Add
//        pagerAdapter.addFragment(TodayTipFragment())
//        pagerAdapter.addFragment(PickedTipFragment())
//        // Adapter
//        binding.todaytipvpInner.adapter = pagerAdapter
//
//        binding.todaytipvpInner.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
//            override fun onPageSelected(position: Int) {
//                super.onPageSelected(position)
//            }
//        })
//
//        val tabTextArray = arrayOf("오늘의 팁", "관심 팁")
//        // TabLayout attach
//        TabLayoutMediator(binding.todayCatTab, binding.todaytipvpInner) { tab, position ->
//            tab.text = tabTextArray[position]
//        }.attach()
//
//        binding.todaytipvpInner.isUserInputEnabled = false
    }
}
package com.tipklemoa.tipkle.src.home

import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.afollestad.materialdialogs.bottomsheets.setPeekHeight
import com.afollestad.materialdialogs.customview.customView
import com.google.android.material.tabs.TabLayoutMediator
import com.tipklemoa.tipkle.PagerFragmentStateAdapter
import com.tipklemoa.tipkle.R
import com.tipklemoa.tipkle.config.BaseFragment
import com.tipklemoa.tipkle.databinding.ViewpagerHomeTabBinding
import com.tipklemoa.tipkle.src.home.PickedTipFragment
import com.tipklemoa.tipkle.src.home.TodayTipFragment

class HomeTopViewPagerFragment : BaseFragment<ViewpagerHomeTabBinding>(ViewpagerHomeTabBinding::bind,
    R.layout.viewpager_home_tab) {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.btnSettingCategory.setOnClickListener {
            MaterialDialog(requireContext(), BottomSheet()).show {
                customView(R.layout.home_bottomsheet_layout)
                setPeekHeight()
            }
        }

        val pagerAdapter = PagerFragmentStateAdapter(requireActivity())
        // 2개의 Fragment Add
        pagerAdapter.addFragment(TodayTipFragment())
        pagerAdapter.addFragment(PickedTipFragment())
        // Adapter
        binding.vpInner.adapter = pagerAdapter

        binding.vpInner.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
            }
        })

        val tabTextArray = arrayOf("오늘의 팁", "관심 팁")
        // TabLayout attach
        TabLayoutMediator(binding.homeTabTop, binding.vpInner) { tab, position ->
            tab.text = tabTextArray[position]
        }.attach()

        binding.vpInner.isUserInputEnabled = false
    }
}
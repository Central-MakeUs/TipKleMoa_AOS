package com.tipklemoa.tipkle.src.search

import android.os.Bundle
import android.view.KeyEvent
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.tipklemoa.tipkle.R
import com.tipklemoa.tipkle.config.BaseFragment
import com.tipklemoa.tipkle.databinding.FragmentSearchBinding
import com.tipklemoa.tipkle.src.PagerFragmentStateAdapter
import com.tipklemoa.tipkle.src.home.HomeTopViewPagerFragment
import com.tipklemoa.tipkle.src.home.LookAroundFragment
import com.tipklemoa.tipkle.src.search.model.KeywordResponse
import com.tipklemoa.tipkle.src.search.model.SearchResponse

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

        //검색 불러오기
        binding.edtSearch.setOnKeyListener { v, keyCode, event ->
            if (event.action==KeyEvent.ACTION_DOWN && keyCode==KeyEvent.KEYCODE_ENTER){
                val text = binding.edtSearch.text.toString()
                if (text.isNullOrEmpty()){
                    Toast.makeText(requireContext(), "검색어를 입력해주세요", Toast.LENGTH_SHORT).show()
                }
                else{
                    val bundle = bundleOf("keyword" to text)
                    // 요청키로 수신측의 리스너에 값을 전달
                    setFragmentResult("keyword", bundle)
                    parentFragmentManager.beginTransaction().replace(R.id.searchFrame, SearchResultFragment()).commit()
                }
            }
            true
        }
    }
}
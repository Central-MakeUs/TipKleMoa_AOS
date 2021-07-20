package com.tipklemoa.tipkle.src.search

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.add
import androidx.fragment.app.setFragmentResult
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.tipklemoa.tipkle.R
import com.tipklemoa.tipkle.config.BaseFragment
import com.tipklemoa.tipkle.databinding.FragmentSearchBinding
import com.tipklemoa.tipkle.src.PagerFragmentStateAdapter
import com.tipklemoa.tipkle.src.home.HomeTopViewPagerFragment
import com.tipklemoa.tipkle.src.home.LookAroundFragment
import com.tipklemoa.tipkle.src.home.model.ResultLookAround
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

        val searchAutoCompleteTextView = binding.edtSearch.findViewById<SearchView.SearchAutoComplete>(R.id.search_src_text)
        searchAutoCompleteTextView.setHintTextColor(resources.getColor(R.color.DBGray))
        searchAutoCompleteTextView.setTextColor(resources.getColor(R.color.black))

        binding.edtSearch.setOnQueryTextFocusChangeListener { v, hasFocus ->
            if (hasFocus){
                val imm:InputMethodManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(v, 0)
            }
        }

        binding.edtSearch.setOnQueryTextListener(object:SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                val text = binding.edtSearch.query.toString()
                if (text.isEmpty()){
                    Toast.makeText(requireContext(), "검색어를 입력해주세요", Toast.LENGTH_SHORT).show()
                }
                else{
                    val bundle = Bundle()
                    bundle.putString("keyword", text)

                    val searchResultFragment = SearchResultFragment()
                    searchResultFragment.arguments = bundle

                    parentFragmentManager.beginTransaction()
                        .replace(R.id.searchFrame, searchResultFragment)
                        .addToBackStack(null)
                        .commit()
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        }

        )
    }
}
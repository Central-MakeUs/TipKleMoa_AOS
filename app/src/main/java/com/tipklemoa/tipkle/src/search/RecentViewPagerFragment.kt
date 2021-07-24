package com.tipklemoa.tipkle.src.search

import android.os.Bundle
import com.tipklemoa.tipkle.R
import com.tipklemoa.tipkle.config.BaseFragment
import com.tipklemoa.tipkle.databinding.ViewpagerRecentKeywordBinding
import com.tipklemoa.tipkle.databinding.ViewpagerTodayTipTabBinding
import com.tipklemoa.tipkle.src.search.model.KeywordResponse
import com.tipklemoa.tipkle.src.search.model.SearchResponse

class RecentViewPagerFragment : BaseFragment<ViewpagerRecentKeywordBinding>(
    ViewpagerRecentKeywordBinding::bind,
    R.layout.viewpager_recent_keyword
), SearchFragmentView{

    override fun onResume() {
        super.onResume()

        showLoadingDialog(requireContext())
        SearchService(this).tryGetKeyword("recent")
    }

    private val onClicked = object: KeywordAdapter.OnItemClickListener{
        override fun onClicked(keyword: String) {
            val bundle = Bundle()
            bundle.putString("keyword", keyword)

            val searchResultFragment = SearchResultFragment()
            searchResultFragment.arguments = bundle

            parentFragmentManager.beginTransaction()
                .replace(R.id.searchFrame, searchResultFragment)
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onGetKeywordSuccess(response: KeywordResponse) {
        dismissLoadingDialog()
        val pageAdapter = KeywordAdapter(requireContext(), null, response.result)
        pageAdapter.setOnItemClickListener(onClicked)
        binding.rvRecentKeyword.adapter = pageAdapter
    }

    override fun onGetKeywordFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)
    }

    override fun onGetSearchSuccess(response: SearchResponse) {
        TODO("Not yet implemented")
    }

    override fun onGetSearchFailure(message: String) {
        TODO("Not yet implemented")
    }
}
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
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        showLoadingDialog(requireContext())
        SearchService(this).trySearchFeed("recent")
    }

    override fun onGetKeywordSuccess(response: KeywordResponse) {
        dismissLoadingDialog()
        var rankNumList = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        val pageAdapter = KeywordAdapter(requireContext(), rankNumList, response.result)
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
package com.tipklemoa.tipkle.src.search

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.tabs.TabLayout
import com.tipklemoa.tipkle.R
import com.tipklemoa.tipkle.config.ApplicationClass
import com.tipklemoa.tipkle.config.BaseFragment
import com.tipklemoa.tipkle.config.BaseResponse
import com.tipklemoa.tipkle.databinding.ViewpagerPickedTipTabBinding
import com.tipklemoa.tipkle.databinding.ViewpagerPopularKeywordBinding
import com.tipklemoa.tipkle.databinding.ViewpagerRecentKeywordBinding
import com.tipklemoa.tipkle.src.home.model.BannerResponse
import com.tipklemoa.tipkle.src.home.model.CategoryListResponse
import com.tipklemoa.tipkle.src.home.model.HomePreviewFeedResponse
import com.tipklemoa.tipkle.src.home.model.LookAroundResponse
import com.tipklemoa.tipkle.src.search.model.KeywordResponse

class PopularViewPagerFragment : BaseFragment<ViewpagerPopularKeywordBinding>(ViewpagerPopularKeywordBinding::bind,
    R.layout.viewpager_popular_keyword), SearchFragmentView {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        showLoadingDialog(requireContext())
        SearchService(this).tryLookAroundFeed("popular")
    }

    override fun onGetKeywordSuccess(response: KeywordResponse) {
        dismissLoadingDialog()
        var rankNumList = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        val pageAdapter = KeywordAdapter(requireContext(), rankNumList, response.result)
        binding.rvPopularKeyword.adapter = pageAdapter
    }

    override fun onGetKeywordFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)
    }
}
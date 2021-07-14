package com.tipklemoa.tipkle.src.search

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.setFragmentResultListener
import com.tipklemoa.tipkle.R
import com.tipklemoa.tipkle.config.ApplicationClass
import com.tipklemoa.tipkle.config.BaseFragment
import com.tipklemoa.tipkle.config.BaseResponse
import com.tipklemoa.tipkle.databinding.FragmentHomeBinding
import com.tipklemoa.tipkle.databinding.FragmentLookAroundBinding
import com.tipklemoa.tipkle.databinding.FragmentSearchResultBinding
import com.tipklemoa.tipkle.src.home.model.BannerResponse
import com.tipklemoa.tipkle.src.home.model.CategoryListResponse
import com.tipklemoa.tipkle.src.home.model.HomePreviewFeedResponse
import com.tipklemoa.tipkle.src.home.model.LookAroundResponse
import com.tipklemoa.tipkle.src.search.model.KeywordResponse
import com.tipklemoa.tipkle.src.search.model.SearchResponse

class SearchResultFragment : BaseFragment<FragmentSearchResultBinding>(
    FragmentSearchResultBinding::bind,
    R.layout.fragment_search_result
), SearchFragmentView {

    lateinit var keyword:String
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setFragmentResultListener("keyword") { key, bundle ->
            // We use a String here, but any type that can be put in a Bundle is supported
            keyword = bundle.getString("keyword")!!
            // Do something with the result...
        }

        showLoadingDialog(requireContext())
        SearchService(this).trySearchFeed(null, "recent", keyword)
    }

    override fun onGetKeywordSuccess(response: KeywordResponse) {
        TODO("Not yet implemented")
    }

    override fun onGetKeywordFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onGetSearchSuccess(response: SearchResponse) {
        dismissLoadingDialog()
        val rvAdapter =
        binding.rvSearchFeed.adapter =
    }

    override fun onGetSearchFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)
    }
}
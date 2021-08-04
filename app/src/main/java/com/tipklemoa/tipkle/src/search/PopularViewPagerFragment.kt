package com.tipklemoa.tipkle.src.search

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import com.tipklemoa.tipkle.R
import com.tipklemoa.tipkle.config.BaseFragment
import com.tipklemoa.tipkle.databinding.ViewpagerPopularKeywordBinding
import com.tipklemoa.tipkle.src.search.model.KeywordResponse
import com.tipklemoa.tipkle.src.search.model.SearchResponse

class PopularViewPagerFragment : BaseFragment<ViewpagerPopularKeywordBinding>(ViewpagerPopularKeywordBinding::bind,
    R.layout.viewpager_popular_keyword), SearchFragmentView {

    override fun onResume() {
        super.onResume()

        if (!isNetworkConnected()){
            showCustomToast("네트워크 연결을 확인해주세요!")
        }
        else{
            showLoadingDialog(requireContext())
            SearchService(this).tryGetKeyword("popular")
        }
    }

    override fun onGetKeywordSuccess(response: KeywordResponse) {
        dismissLoadingDialog()
        val rankNumList = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        val pageAdapter = KeywordAdapter(requireContext(), rankNumList, response.result)
        pageAdapter.setOnItemClickListener(onClicked)
        binding.rvPopularKeyword.adapter = pageAdapter
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
}
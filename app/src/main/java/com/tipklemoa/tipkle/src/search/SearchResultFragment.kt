package com.tipklemoa.tipkle.src.search

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.SearchView
import com.tipklemoa.tipkle.R
import com.tipklemoa.tipkle.config.BaseFragment
import com.tipklemoa.tipkle.databinding.FragmentSearchResultBinding
import com.tipklemoa.tipkle.src.search.model.KeywordResponse
import com.tipklemoa.tipkle.src.search.model.SearchResponse

class SearchResultFragment : BaseFragment<FragmentSearchResultBinding>(
    FragmentSearchResultBinding::bind,
    R.layout.fragment_search_result
), SearchFragmentView {
    lateinit var keyword:String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        keyword = arguments?.getString("keyword").toString()
        showLoadingDialog(requireContext())
        SearchService(this).trySearchFeed(null, "recent", keyword)

        Log.d("keyword", keyword)

        binding.edtSearchResult.setText(keyword)

        binding.edtSearchResult.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus){
                if (binding.edtSearchResult.text.isNullOrEmpty()){
                    binding.btnSearchDelete.visibility = View.GONE
                }
                else{
                    binding.btnSearchDelete.visibility = View.VISIBLE
                }
            }
        }

        binding.btnSearchDelete.setOnClickListener {
            binding.edtSearchResult.setText("")
        }

        binding.tvLookAroundRecent.setOnClickListener {
            binding.tvLookAroundRecent.setTextColor(resources.getColor(R.color.black))
            binding.tvLookAroundPopular.setTextColor(resources.getColor(R.color.DBGray))
            showLoadingDialog(requireContext())
            SearchService(this).trySearchFeed(search=keyword, order="recent")
        }

        binding.tvLookAroundPopular.setOnClickListener {
            binding.tvLookAroundPopular.setTextColor(resources.getColor(R.color.black))
            binding.tvLookAroundRecent.setTextColor(resources.getColor(R.color.DBGray))
            showLoadingDialog(requireContext())
            SearchService(this).trySearchFeed(search=keyword, order="popular")
        }

        binding.nestedScrollView.isNestedScrollingEnabled = false

        binding.btnSearchBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    override fun onGetKeywordSuccess(response: KeywordResponse) {
        TODO("Not yet implemented")
    }

    override fun onGetKeywordFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onGetSearchSuccess(response: SearchResponse) {
        dismissLoadingDialog()
        val rvAdapter = SearchFeedAdapter(requireContext(), response.result)
        binding.rvSearchFeed.adapter = rvAdapter
    }

    override fun onGetSearchFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)
    }
}
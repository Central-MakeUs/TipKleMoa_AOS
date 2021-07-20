package com.tipklemoa.tipkle.src.search

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.RecyclerView
import com.tipklemoa.tipkle.R
import com.tipklemoa.tipkle.config.BaseFragment
import com.tipklemoa.tipkle.databinding.FragmentSearchResultBinding
import com.tipklemoa.tipkle.src.home.HomeService
import com.tipklemoa.tipkle.src.home.LookAroundFeedAdapter
import com.tipklemoa.tipkle.src.home.model.ResultLookAround
import com.tipklemoa.tipkle.src.search.model.KeywordResponse
import com.tipklemoa.tipkle.src.search.model.ResultSearch
import com.tipklemoa.tipkle.src.search.model.SearchResponse

class SearchResultFragment : BaseFragment<FragmentSearchResultBinding>(
    FragmentSearchResultBinding::bind,
    R.layout.fragment_search_result
), SearchFragmentView {
    lateinit var keyword:String
    private var searchResultList = mutableListOf<ResultSearch>()
    private var page = 1      // 현재 페이지
    var isFeedEnd = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        keyword = arguments?.getString("keyword").toString()

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
            SearchService(this).trySearchFeed(search=keyword, order="recent", page=1, limit=5)
        }

        binding.tvLookAroundPopular.setOnClickListener {
            binding.tvLookAroundPopular.setTextColor(resources.getColor(R.color.black))
            binding.tvLookAroundRecent.setTextColor(resources.getColor(R.color.DBGray))
            showLoadingDialog(requireContext())
            SearchService(this).trySearchFeed(search=keyword, order="popular", page=1, limit=5)
        }

        binding.nestedScrollView.isNestedScrollingEnabled = false

        binding.btnSearchBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.rvSearchFeed.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

//             direction:  양수일경우엔 오른쪽 스크롤, 음수일경우엔 왼쪽 스크롤
//                수평으로 더이상 스크롤이 안되면, 데이터를 더해서 불러옴
                if (!binding.rvSearchFeed.canScrollVertically(1)) {
                    if (!isFeedEnd) {
                        dismissLoadingDialog()
                        showLoadingDialog(requireContext())
                        SearchService(this@SearchResultFragment).trySearchFeed(
                            null,
                            "popular",
                            keyword, ++page, 5)
                    }
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()

        page = 1
        searchResultList.clear()
        isFeedEnd = false
        showLoadingDialog(requireContext())
        SearchService(this).trySearchFeed(null, "recent", keyword, page, 5)
    }

    override fun onGetKeywordSuccess(response: KeywordResponse) {
        TODO("Not yet implemented")
    }

    override fun onGetKeywordFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onGetSearchSuccess(response: SearchResponse) {
        dismissLoadingDialog()
        var searchAdapter = SearchFeedAdapter(requireContext(), response.result)
//      맨 처음(page=0) -> 데이터가 하나라도 있으면
        if (page == 1 && response.result.isNotEmpty()) {
            Log.d("test", "결과 있음")

            searchResultList.addAll(response.result)
            searchAdapter = SearchFeedAdapter(requireContext(), searchResultList)
            binding.rvSearchFeed.adapter = searchAdapter
        }
//      page=1부터 불러오고, 둥지가 있으면 추가해줘야함 ->
        else if (page != 1 && response.result.isNotEmpty()) {
            searchResultList.addAll(response.result)
            searchAdapter.notifyItemInserted(searchResultList.size - 1)
        }

//        페이지추가 끝
        if (page != 1 && response.result.isNullOrEmpty()) {
            Log.d("둥지", "둥지끝")
            isFeedEnd = true
        }
    }

    override fun onGetSearchFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)
    }
}
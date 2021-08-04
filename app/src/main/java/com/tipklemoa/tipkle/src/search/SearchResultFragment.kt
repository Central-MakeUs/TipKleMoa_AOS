package com.tipklemoa.tipkle.src.search

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_ENTER
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.tipklemoa.tipkle.R
import com.tipklemoa.tipkle.config.BaseFragment
import com.tipklemoa.tipkle.databinding.FragmentSearchResultBinding
import com.tipklemoa.tipkle.src.search.model.KeywordResponse
import com.tipklemoa.tipkle.src.search.model.ResultSearch
import com.tipklemoa.tipkle.src.search.model.SearchResponse

class SearchResultFragment : BaseFragment<FragmentSearchResultBinding>(
    FragmentSearchResultBinding::bind,
    R.layout.fragment_search_result
), SearchFragmentView {
    lateinit var keyword:String
    private var searchResultList = mutableListOf<ResultSearch>()
    var order="recent"
    private var page = 1      // 현재 페이지
    var isFeedEnd = false
    var searchAdapter:SearchFeedAdapter?=null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        keyword = arguments?.getString("keyword").toString()

        Log.d("keyword", keyword)

        binding.edtSearchResult.setText(keyword)

//        binding.edtSearchResult.addTextChangedListener(object :TextWatcher{
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                if (s.isNullOrEmpty()){
//                    binding.btnSearchDelete.visibility = View.INVISIBLE
//                }
//                else{
//                    binding.btnSearchDelete.visibility = View.VISIBLE
//                }
//            }
//
//            override fun afterTextChanged(s: Editable?) {
//
//            }
//
//        })

        binding.edtSearchResult.setOnKeyListener { v, keyCode, event ->
            if (event.action==KeyEvent.ACTION_DOWN && keyCode==KEYCODE_ENTER){
                binding.tvLookAroundRecent.setTextColor(resources.getColor(R.color.black))
                binding.tvLookAroundPopular.setTextColor(resources.getColor(R.color.DBGray))

                showLoadingDialog(requireContext())

                order = "recent"
                keyword = binding.edtSearchResult.text.toString()
                page = 1
                searchResultList.clear()
                SearchService(this@SearchResultFragment).trySearchFeed(
                    null,
                    order,
                    keyword, page, 5)
            }
            false
        }

        binding.btnSearchDelete.setOnClickListener {
            binding.edtSearchResult.setText("")
            binding.btnSearchDelete.visibility = View.INVISIBLE
        }

        binding.tvLookAroundRecent.setOnClickListener {
            binding.tvLookAroundRecent.setTextColor(resources.getColor(R.color.black))
            binding.tvLookAroundPopular.setTextColor(resources.getColor(R.color.DBGray))
            showLoadingDialog(requireContext())
            order = "recent"
            page = 1
            searchResultList.clear()
            SearchService(this).trySearchFeed(search=keyword, order=order, page=page, limit=5)
        }

        binding.tvLookAroundPopular.setOnClickListener {
            binding.tvLookAroundPopular.setTextColor(resources.getColor(R.color.black))
            binding.tvLookAroundRecent.setTextColor(resources.getColor(R.color.DBGray))
            showLoadingDialog(requireContext())
            order = "popular"
            page = 1
            searchResultList.clear()
            SearchService(this).trySearchFeed(search=keyword, order=order, page=page, limit=5)
        }

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
                            order,
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
        Handler(Looper.getMainLooper()).postDelayed({
            dismissLoadingDialog()
        }, 1500)

//      맨 처음(page=1) -> 데이터가 하나라도 있으면
        if (page == 1 && response.result.isNotEmpty()) {
            Log.d("case", "1")

            binding.layoutEmptysearch.visibility = View.INVISIBLE
            binding.rvSearchFeed.visibility = View.VISIBLE
            searchResultList.addAll(response.result)
            Log.d("case", searchResultList.size.toString())
            searchAdapter = SearchFeedAdapter(requireContext(), searchResultList)
            binding.rvSearchFeed.adapter = searchAdapter
        }
//      page=1부터 불러오고, 데이터가 있으면 추가해줘야함 ->
        else if (page != 1 && response.result.isNotEmpty()) {
            binding.layoutEmptysearch.visibility = View.INVISIBLE
            Log.d("case", "2")

            searchResultList.addAll(response.result)
            Log.d("case", searchResultList.size.toString())
            searchAdapter!!.notifyItemInserted(searchResultList.size - 1)
        }
//        페이지추가 끝
        else if (page != 1 && response.result.isNullOrEmpty()) {
            binding.layoutEmptysearch.visibility = View.INVISIBLE
            Log.d("case", "3")
            Log.d("case", searchResultList.size.toString())

            isFeedEnd = true
        }
        else if (response.result.isNullOrEmpty()){
            binding.rvSearchFeed.visibility = View.INVISIBLE
            binding.layoutEmptysearch.visibility = View.VISIBLE
        }
    }

    override fun onGetSearchFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)
    }
}
package com.tipklemoa.tipkle.src.search

import com.tipklemoa.tipkle.src.search.model.KeywordResponse
import com.tipklemoa.tipkle.src.search.model.SearchResponse

interface SearchFragmentView {
    fun onGetKeywordSuccess(response: KeywordResponse)
    fun onGetKeywordFailure(message: String)

    fun onGetSearchSuccess(response:SearchResponse)
    fun onGetSearchFailure(message: String)
}
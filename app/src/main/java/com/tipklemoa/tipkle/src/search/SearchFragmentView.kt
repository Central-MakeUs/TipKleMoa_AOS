package com.tipklemoa.tipkle.src.search

import com.tipklemoa.tipkle.src.search.model.KeywordResponse

interface SearchFragmentView {
    fun onGetKeywordSuccess(response: KeywordResponse)
    fun onGetKeywordFailure(message: String)
}
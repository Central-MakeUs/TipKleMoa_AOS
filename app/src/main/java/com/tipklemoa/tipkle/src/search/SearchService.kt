package com.tipklemoa.tipkle.src.search

import com.tipklemoa.tipkle.config.ApplicationClass
import com.tipklemoa.tipkle.config.BaseResponse
import com.tipklemoa.tipkle.src.home.model.*
import com.tipklemoa.tipkle.src.search.model.KeywordResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchService(val view: SearchFragmentView) {
    fun tryLookAroundFeed(order:String){
        val searchRetrofitInterface = ApplicationClass.sRetrofit.create(SearchRetrofitInterface::class.java)
        searchRetrofitInterface.getKeyword(order).enqueue(object: Callback<KeywordResponse> {
            override fun onResponse(call: Call<KeywordResponse>, response: Response<KeywordResponse>) {
                view.onGetKeywordSuccess(response.body() as KeywordResponse)
            }

            override fun onFailure(call: Call<KeywordResponse>, t: Throwable) {
                view.onGetKeywordFailure(t.message ?: "통신 오류")
            }
        })
    }
}
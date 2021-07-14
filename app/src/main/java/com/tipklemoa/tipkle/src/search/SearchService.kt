package com.tipklemoa.tipkle.src.search

import com.tipklemoa.tipkle.config.ApplicationClass
import com.tipklemoa.tipkle.config.BaseResponse
import com.tipklemoa.tipkle.src.home.HomeRetrofitInterface
import com.tipklemoa.tipkle.src.home.model.*
import com.tipklemoa.tipkle.src.search.model.KeywordResponse
import com.tipklemoa.tipkle.src.search.model.SearchResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchService(val view: SearchFragmentView) {
    fun trySearchFeed(categoryName:String?=null, order:String, search:String){
        val searchRetrofitInterface = ApplicationClass.sRetrofit.create(SearchRetrofitInterface::class.java)
        searchRetrofitInterface.getSearchFeed(categoryName, order, search).enqueue(object: Callback<SearchResponse> {
            override fun onResponse(call: Call<SearchResponse>, response: Response<SearchResponse>) {
                view.onGetSearchSuccess(response.body() as SearchResponse)
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                view.onGetSearchFailure(t.message ?: "통신 오류")
            }
        })
    }
}
package com.tipklemoa.tipkle.src.search

import com.tipklemoa.tipkle.src.home.model.*
import com.tipklemoa.tipkle.src.search.model.KeywordResponse
import com.tipklemoa.tipkle.src.search.model.SearchResponse
import retrofit2.Call
import retrofit2.http.*

interface SearchRetrofitInterface {

//  검색어 조회
    @GET("/search")
    fun getKeyword(@Query("order") order:String): Call<KeywordResponse>

    //검색 피드 목록 조회 (팁끌 들러보기)
    @GET("/posts")
    fun getSearchFeed(@Query("categoryName") categoryName:String?=null, @Query("order") order:String, @Query("search") search:String,
    @Query("page") page:Int, @Query("limit") limit:Int)
            : Call<SearchResponse>
}
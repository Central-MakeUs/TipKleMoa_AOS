package com.tipklemoa.tipkle.src

import com.tipklemoa.tipkle.src.model.DetailFeedResponse
import retrofit2.Call
import retrofit2.http.*

interface MainRetrofitInterface {

    //피드 상세조회 API
    @GET("/posts/{postId}")
    fun getFeedDetail(@Path("postId") postId:Int): Call<DetailFeedResponse>

//    //사용자 카테고리 수정
//    @PATCH("/users/categories")
//    fun patchCategory(@Body params: PatchCategoryRequest): Call<BaseResponse>
//
//    //피드 목록 조회 (팁끌 들러보기)
//    @GET("/posts")
//    fun getLookAroundFeed(@Query("categoryName") categoryName:String, @Query("order") order:String, @Query("search") search:String?=null)
//            : Call<LookAroundResponse>
}
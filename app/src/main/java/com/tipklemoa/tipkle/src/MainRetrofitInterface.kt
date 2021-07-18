package com.tipklemoa.tipkle.src

import com.tipklemoa.tipkle.config.BaseResponse
import com.tipklemoa.tipkle.src.model.DetailFeedResponse
import com.tipklemoa.tipkle.src.model.NewTipResponse
import com.tipklemoa.tipkle.src.model.PostNewTipRequest
import retrofit2.Call
import retrofit2.http.*

interface MainRetrofitInterface {

    //피드 상세조회 API
    @GET("/posts/{postId}")
    fun getFeedDetail(@Path("postId") postId:Int): Call<DetailFeedResponse>

    //피드 삭제
    @DELETE("/posts/{postId}")
    fun deleteFeed(@Path("postId") postId:Int): Call<BaseResponse>

    //게시글 등록
    @POST("/post")
    fun postNewTip(@Body params: PostNewTipRequest): Call<NewTipResponse>
//
//    //피드 목록 조회 (팁끌 들러보기)
//    @GET("/posts")
//    fun getLookAroundFeed(@Query("categoryName") categoryName:String, @Query("order") order:String, @Query("search") search:String?=null)
//            : Call<LookAroundResponse>
}
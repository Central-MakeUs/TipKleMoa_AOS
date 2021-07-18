package com.tipklemoa.tipkle.src.mypage

import com.tipklemoa.tipkle.config.BaseResponse
import com.tipklemoa.tipkle.src.model.DetailFeedResponse
import com.tipklemoa.tipkle.src.model.NewTipResponse
import com.tipklemoa.tipkle.src.model.PostNewTipRequest
import com.tipklemoa.tipkle.src.mypage.model.MyPageResponse
import retrofit2.Call
import retrofit2.http.*

interface MyPageRetrofitInterface {

    //마이페이지 API
    @GET("/users/profiles")
    fun getFeedDetail(): Call<MyPageResponse>

    //로그아웃
    @PATCH("/logout")
    fun patchLogout(): Call<BaseResponse>

    //회원탈
    @DELETE("/users")
    fun deleteUser(): Call<BaseResponse>
//
//    //피드 목록 조회 (팁끌 들러보기)
//    @GET("/posts")
//    fun getLookAroundFeed(@Query("categoryName") categoryName:String, @Query("order") order:String, @Query("search") search:String?=null)
//            : Call<LookAroundResponse>
}
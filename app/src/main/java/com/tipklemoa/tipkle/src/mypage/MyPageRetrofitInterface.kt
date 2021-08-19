package com.tipklemoa.tipkle.src.mypage

import com.tipklemoa.tipkle.config.BaseResponse
import com.tipklemoa.tipkle.src.mypage.model.*
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

    @PATCH("/users/nickname")
    fun patchUserNickName(@Body params: PostEditNickNameRequest) : Call<BaseResponse>

    //피드 목록 조회 (팁끌 들러보기)
    @PATCH("/users/profileImg")
    fun patchProfileImage(@Body params: PostEditProfileRequest) : Call<BaseResponse>

    //키워드 조회 API
    @GET("/keywords")
    fun getKeywords(): Call<KeywordResponse>

    //마이페이지 API
    @POST("/keywords")
    fun postKeyword(@Body params: PostKeywordRequest): Call<BaseResponse>

    //마이페이지 API
    @DELETE("/keywords/{keywordId}")
    fun deleteKeyword(@Path("keywordId") keywordId:Int): Call<BaseResponse>
}
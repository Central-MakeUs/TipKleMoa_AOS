package com.tipklemoa.tipkle.src.home

import com.tipklemoa.tipkle.config.BaseResponse
import com.tipklemoa.tipkle.src.home.model.BannerResponse
import com.tipklemoa.tipkle.src.home.model.CategoryListResponse
import com.tipklemoa.tipkle.src.login.model.KakaoLoginResponse
import com.tipklemoa.tipkle.src.login.model.KakaoRegisterResponse
import com.tipklemoa.tipkle.src.login.model.PostKakaoLoginRequest
import com.tipklemoa.tipkle.src.login.model.PostKakaoRegisterRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface HomeRetrofitInterface {

//  관심 카테고리 조회
    @GET("/users/categories")
    fun getPickedCategoryList(): Call<CategoryListResponse>

    //배너 조회
    @GET("/banners")
    fun getBanners(): Call<BannerResponse>
//
//    //카카오 회원가입
//    @POST("/signup/kakao")
//    fun postKakaoRegister(@Body params: PostKakaoRegisterRequest): Call<KakaoRegisterResponse>
//
//    //카카오 자동로그인 (jwt 검증)
//    @GET("/auto-login")
//    fun getAutoLogin(): Call<BaseResponse>
}
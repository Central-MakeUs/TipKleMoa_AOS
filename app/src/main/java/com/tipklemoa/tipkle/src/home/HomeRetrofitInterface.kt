package com.tipklemoa.tipkle.src.home

import com.tipklemoa.tipkle.config.BaseResponse
import com.tipklemoa.tipkle.src.home.model.BannerResponse
import com.tipklemoa.tipkle.src.home.model.CategoryListResponse
import com.tipklemoa.tipkle.src.home.model.HomePreviewFeedResponse
import com.tipklemoa.tipkle.src.login.model.KakaoLoginResponse
import com.tipklemoa.tipkle.src.login.model.KakaoRegisterResponse
import com.tipklemoa.tipkle.src.login.model.PostKakaoLoginRequest
import com.tipklemoa.tipkle.src.login.model.PostKakaoRegisterRequest
import retrofit2.Call
import retrofit2.http.*

interface HomeRetrofitInterface {

//  관심 카테고리 조회
    @GET("/users/categories")
    fun getPickedCategoryList(): Call<CategoryListResponse>

    //배너 조회
    @GET("/banners")
    fun getBanners(): Call<BannerResponse>

    //메인 최신순, 인기순 피드
    @GET("/categories/{categoryName}/tips")
    fun getPreviewFeed(@Path("categoryName") categoryName:String, @Query("order") order:String): Call<HomePreviewFeedResponse>

    //사용자 카테고리 수정
    @PATCH("/users/categories")
    fun patchCategory(): Call<BaseResponse>
}
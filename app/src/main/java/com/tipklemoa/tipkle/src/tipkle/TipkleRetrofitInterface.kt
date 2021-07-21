package com.tipklemoa.tipkle.src.tipkle

import com.tipklemoa.tipkle.config.BaseResponse
import com.tipklemoa.tipkle.src.home.model.*
import com.tipklemoa.tipkle.src.login.model.KakaoLoginResponse
import com.tipklemoa.tipkle.src.login.model.KakaoRegisterResponse
import com.tipklemoa.tipkle.src.login.model.PostKakaoLoginRequest
import com.tipklemoa.tipkle.src.login.model.PostKakaoRegisterRequest
import com.tipklemoa.tipkle.src.tipkle.model.MakeFolderResponse
import com.tipklemoa.tipkle.src.tipkle.model.PostNewFolderRequest
import com.tipklemoa.tipkle.src.tipkle.model.TipFolderResponse
import retrofit2.Call
import retrofit2.http.*

interface TipkleRetrofitInterface {

    //폴더 조회
    @GET("/bookmarks")
    fun getTipkleFolders(): Call<TipFolderResponse>

    //배너 조회
    @POST("/folders")
    fun postFolders(postNewFolderRequest: PostNewFolderRequest): Call<MakeFolderResponse>
//
//    //메인 최신순, 인기순 피드
//    @GET("/categories/{categoryName}/tips")
//    fun getPreviewFeed(@Path("categoryName") categoryName:String, @Query("order") order:String): Call<HomePreviewFeedResponse>
//
//    //사용자 카테고리 수정
//    @PATCH("/users/categories")
//    fun patchCategory(@Body params: PatchCategoryRequest): Call<BaseResponse>
//
//    //피드 목록 조회 (팁끌 들러보기)
//    @GET("/posts")
//    fun getLookAroundFeed(@Query("categoryName") categoryName:String, @Query("order") order:String, @Query("search") search:String?=null,
//                          @Query("page") page:Int, @Query("limit") limit:Int)
//            : Call<LookAroundResponse>
}
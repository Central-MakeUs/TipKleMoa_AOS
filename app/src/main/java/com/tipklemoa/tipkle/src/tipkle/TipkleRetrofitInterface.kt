package com.tipklemoa.tipkle.src.tipkle

import com.tipklemoa.tipkle.config.BaseResponse
import com.tipklemoa.tipkle.src.home.model.*
import com.tipklemoa.tipkle.src.login.model.KakaoLoginResponse
import com.tipklemoa.tipkle.src.login.model.KakaoRegisterResponse
import com.tipklemoa.tipkle.src.login.model.PostKakaoLoginRequest
import com.tipklemoa.tipkle.src.login.model.PostKakaoRegisterRequest
import com.tipklemoa.tipkle.src.tipkle.model.FolderFeedResponse
import com.tipklemoa.tipkle.src.tipkle.model.MakeFolderResponse
import com.tipklemoa.tipkle.src.tipkle.model.PostNewFolderRequest
import com.tipklemoa.tipkle.src.tipkle.model.TipFolderResponse
import retrofit2.Call
import retrofit2.http.*

interface TipkleRetrofitInterface {

    @GET("/bookmarks")
    fun getTipkleFolders(): Call<TipFolderResponse>

    @POST("/folders")
    fun postFolders(@Body params: PostNewFolderRequest): Call<MakeFolderResponse>

    @GET("/folders/{folderId}/posts")
    fun getFolderFeed(@Path("folderId") folderId:Int): Call<FolderFeedResponse>

    @DELETE("/folders/{folderId}")
    fun deleteFolder(@Path("folderId") folderId:Int): Call<BaseResponse>
//
//    //피드 목록 조회 (팁끌 들러보기)
//    @GET("/posts")
//    fun getLookAroundFeed(@Query("categoryName") categoryName:String, @Query("order") order:String, @Query("search") search:String?=null,
//                          @Query("page") page:Int, @Query("limit") limit:Int)
//            : Call<LookAroundResponse>
}
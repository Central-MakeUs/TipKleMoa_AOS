package com.tipklemoa.tipkle.src

import com.google.gson.annotations.SerializedName
import com.tipklemoa.tipkle.config.BaseResponse
import com.tipklemoa.tipkle.src.home.model.PatchCategoryRequest
import com.tipklemoa.tipkle.src.model.DetailFeedResponse
import com.tipklemoa.tipkle.src.model.NewTipResponse
import com.tipklemoa.tipkle.src.model.PostAddBookMarkRequest
import com.tipklemoa.tipkle.src.model.PostNewTipRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.*
import java.util.ArrayList

import retrofit2.http.POST

import retrofit2.http.FormUrlEncoded

interface MainRetrofitInterface {

    //피드 상세조회 API
    @GET("/posts/{postId}")
    fun getFeedDetail(@Path("postId") postId:Int): Call<DetailFeedResponse>

    //피드 삭제
    @DELETE("/posts/{postId}")
    fun deleteFeed(@Path("postId") postId:Int): Call<BaseResponse>

    //게시글 등록
    @POST("/posts")
    fun postNewTip(@Body params: PostNewTipRequest): Call<NewTipResponse>

    //북마크 추가
    @POST("/folders/{folderId}/posts")
    fun postBookMark(@Path("folderId") folderId:Int, @Body params: PostAddBookMarkRequest)
            : Call<BaseResponse>

    //북마크 삭제
    @DELETE("/folders/posts/{postId}")
    fun deleteBookMark(@Path("postId") postId:Int): Call<BaseResponse>

}
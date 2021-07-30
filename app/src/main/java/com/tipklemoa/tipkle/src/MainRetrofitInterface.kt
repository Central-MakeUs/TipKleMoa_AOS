package com.tipklemoa.tipkle.src

import com.tipklemoa.tipkle.config.BaseResponse
import com.tipklemoa.tipkle.src.model.*
import retrofit2.Call
import retrofit2.http.*
import retrofit2.http.POST

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

    //별점 등록
    @POST("/posts/{postId}/stars")
    fun postStar(@Path("postId") postId:Int, @Body params:PostStarRequest): Call<BaseResponse>

    //댓글 조회
    @GET("/posts/{postId}/comments")
    fun getComments(@Path("postId") postId:Int): Call<CommentResponse>

    //댓글 등록
    @POST("/posts/{postId}/comments")
    fun postComment(@Path("postId") postId:Int, @Body params:PostCommentRequest): Call<BaseResponse>

    //댓글 삭제
    @DELETE("/posts/comments/{commentId}")
    fun deleteComment(@Path("commentId") commentId:Int): Call<BaseResponse>

    //피드 삭제
    @POST("/posts/{postId}/reports")
    fun postReportFeed(@Path("postId") postId:Int, @Body params:PostReportFeedRequest): Call<BaseResponse>

    @POST("/posts/comments/{commentId}/reports")
    fun postReportComment(@Path("commentId") commentId:Int, @Body params:PostReportFeedRequest): Call<BaseResponse>
}
package com.tipklemoa.tipkle.src

import com.tipklemoa.tipkle.config.ApplicationClass
import com.tipklemoa.tipkle.config.BaseResponse
import com.tipklemoa.tipkle.src.model.*
import org.w3c.dom.Comment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainService(val view: MainView) {

    fun tryGetFeedDetail(postId:Int){
        val mainRetrofitInterface = ApplicationClass.sRetrofit.create(MainRetrofitInterface::class.java)
        mainRetrofitInterface.getFeedDetail(postId).enqueue(object: Callback<DetailFeedResponse> {
            override fun onResponse(call: Call<DetailFeedResponse>, response: Response<DetailFeedResponse>) {
                view.onGetFeedDetailSuccess(response.body() as DetailFeedResponse)
            }

            override fun onFailure(call: Call<DetailFeedResponse>, t: Throwable) {
                view.onGetFeedDetailFailure(t.message ?: "통신 오류")
            }
        })
    }

    //피드삭제
    fun tryDeleteFeed(postId:Int){
        val mainRetrofitInterface = ApplicationClass.sRetrofit.create(MainRetrofitInterface::class.java)
        mainRetrofitInterface.deleteFeed(postId).enqueue(object: Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                view.onDeleteFeedSuccess(response.body() as BaseResponse)
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                view.onDeleteFeedFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryPostNewTip(postNewTipRequest: PostNewTipRequest){
        val mainRetrofitInterface = ApplicationClass.sRetrofit.create(MainRetrofitInterface::class.java)
        mainRetrofitInterface.postNewTip(postNewTipRequest).enqueue(object: Callback<NewTipResponse> {
            override fun onResponse(call: Call<NewTipResponse>, response: Response<NewTipResponse>) {
                view.onPostSuccess(response.body() as NewTipResponse)
            }

            override fun onFailure(call: Call<NewTipResponse>, t: Throwable) {
                view.onPostFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryPostBookMark(folderId:Int, postAddBookMarkRequest: PostAddBookMarkRequest){
        val mainRetrofitInterface = ApplicationClass.sRetrofit.create(MainRetrofitInterface::class.java)
        mainRetrofitInterface.postBookMark(folderId, postAddBookMarkRequest).enqueue(object: Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                view.onPostBookMarkSuccess(response.body() as BaseResponse)
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                view.onPostBookMarkFailure(t.message ?: "통신 오류")
            }
        })
    }
    //피드삭제
    fun tryDeleteBookMark(postId:Int){
        val mainRetrofitInterface = ApplicationClass.sRetrofit.create(MainRetrofitInterface::class.java)
        mainRetrofitInterface.deleteBookMark(postId).enqueue(object: Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                view.onDeleteBookmarkSuccess(response.body() as BaseResponse)
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                view.onDeleteBookmarkFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryPostStar(postId:Int, postStarRequest: PostStarRequest){
        val mainRetrofitInterface = ApplicationClass.sRetrofit.create(MainRetrofitInterface::class.java)
        mainRetrofitInterface.postStar(postId, postStarRequest).enqueue(object: Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                view.onPostStarSuccess(response.body() as BaseResponse)
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                view.onPostStarFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryGetComments(postId:Int){
        val mainRetrofitInterface = ApplicationClass.sRetrofit.create(MainRetrofitInterface::class.java)
        mainRetrofitInterface.getComments(postId).enqueue(object: Callback<CommentResponse> {
            override fun onResponse(call: Call<CommentResponse>, response: Response<CommentResponse>) {
                view.onGetCommentSuccess(response.body() as CommentResponse)
            }

            override fun onFailure(call: Call<CommentResponse>, t: Throwable) {
                view.onGetCommentFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryPostComment(postId:Int, postCommentRequest: PostCommentRequest){
        val mainRetrofitInterface = ApplicationClass.sRetrofit.create(MainRetrofitInterface::class.java)
        mainRetrofitInterface.postComment(postId, postCommentRequest).enqueue(object: Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                view.onPostCommentSuccess(response.body() as BaseResponse)
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                view.onPostCommentFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryDeleteComment(commentId:Int){
        val mainRetrofitInterface = ApplicationClass.sRetrofit.create(MainRetrofitInterface::class.java)
        mainRetrofitInterface.deleteComment(commentId).enqueue(object: Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                view.onDeleteCommentSuccess(response.body() as BaseResponse)
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                view.onDeleteCommentFailure(t.message ?: "통신 오류")
            }
        })
    }
}
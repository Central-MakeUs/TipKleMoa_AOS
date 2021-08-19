package com.tipklemoa.tipkle.src.main

import com.tipklemoa.tipkle.config.BaseResponse
import com.tipklemoa.tipkle.src.main.model.CommentResponse
import com.tipklemoa.tipkle.src.main.model.DetailFeedResponse
import com.tipklemoa.tipkle.src.main.model.NewTipResponse

interface MainView {
    fun onGetFeedDetailSuccess(response: DetailFeedResponse)
    fun onGetFeedDetailFailure(message: String)

    fun onDeleteFeedSuccess(response: BaseResponse)
    fun onDeleteFeedFailure(message: String)

    fun onPostSuccess(response: NewTipResponse)
    fun onPostFailure(message: String)

    fun onPostBookMarkSuccess(response: BaseResponse)
    fun onPostBookMarkFailure(message: String)

    fun onDeleteBookmarkSuccess(response: BaseResponse)
    fun onDeleteBookmarkFailure(message: String)

    fun onPostStarSuccess(response: BaseResponse)
    fun onPostStarFailure(message: String)

    fun onGetCommentSuccess(response: CommentResponse)
    fun onGetCommentFailure(message: String)

    fun onPostCommentSuccess(response: BaseResponse)
    fun onPostCommentFailure(message: String)

    fun onDeleteCommentSuccess(response: BaseResponse)
    fun onDeleteCommentFailure(message: String)

    fun onPostFeedReportSuccess(response: BaseResponse)
    fun onPostFeedReportFailure(message: String)

    fun onPostCommentReportSuccess(response: BaseResponse)
    fun onPostCommentReportFailure(message: String)
}
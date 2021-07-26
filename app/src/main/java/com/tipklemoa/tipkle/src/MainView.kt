package com.tipklemoa.tipkle.src

import com.tipklemoa.tipkle.config.BaseResponse
import com.tipklemoa.tipkle.src.home.model.BannerResponse
import com.tipklemoa.tipkle.src.home.model.CategoryListResponse
import com.tipklemoa.tipkle.src.home.model.HomePreviewFeedResponse
import com.tipklemoa.tipkle.src.home.model.LookAroundResponse
import com.tipklemoa.tipkle.src.login.model.KakaoLoginResponse
import com.tipklemoa.tipkle.src.login.model.KakaoRegisterResponse
import com.tipklemoa.tipkle.src.model.CommentResponse
import com.tipklemoa.tipkle.src.model.DetailFeedResponse
import com.tipklemoa.tipkle.src.model.NewTipResponse

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

}
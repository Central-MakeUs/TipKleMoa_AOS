package com.tipklemoa.tipkle.src.mypage

import com.tipklemoa.tipkle.config.BaseResponse
import com.tipklemoa.tipkle.src.home.model.BannerResponse
import com.tipklemoa.tipkle.src.home.model.CategoryListResponse
import com.tipklemoa.tipkle.src.home.model.HomePreviewFeedResponse
import com.tipklemoa.tipkle.src.home.model.LookAroundResponse
import com.tipklemoa.tipkle.src.login.model.KakaoLoginResponse
import com.tipklemoa.tipkle.src.login.model.KakaoRegisterResponse
import com.tipklemoa.tipkle.src.model.DetailFeedResponse
import com.tipklemoa.tipkle.src.model.NewTipResponse
import com.tipklemoa.tipkle.src.mypage.model.MyPageResponse

interface MyPageView {
    fun onGetMyPageSuccess(response: MyPageResponse)
    fun onGetMyPageFailure(message: String)

    fun onLogoutSuccess(response: BaseResponse)
    fun onLogoutFailure(message: String)

    fun onDeleteUserSuccess(response: BaseResponse)
    fun onDeleteUserFailure(message: String)
//
//    fun onGetHomePreviewFeedSuccess(response: HomePreviewFeedResponse)
//    fun onGetHomePreviewFeedFailure(message: String)
//
//    fun onPatchCategorySuccess(response: BaseResponse)
//    fun onPatchCategoryFailure(message: String)
//
//    fun onGetLookAroundFeedSuccess(response: LookAroundResponse)
//    fun onGetLookAroundFeedFailure(message: String)
}
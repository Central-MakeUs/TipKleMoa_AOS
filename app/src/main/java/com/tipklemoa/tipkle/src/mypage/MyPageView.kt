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
import com.tipklemoa.tipkle.src.mypage.model.KeywordResponse
import com.tipklemoa.tipkle.src.mypage.model.MyPageResponse

interface MyPageView {
    fun onGetMyPageSuccess(response: MyPageResponse)
    fun onGetMyPageFailure(message: String)

    fun onLogoutSuccess(response: BaseResponse)
    fun onLogoutFailure(message: String)

    fun onDeleteUserSuccess(response: BaseResponse)
    fun onDeleteUserFailure(message: String)

    fun onPatchProfileImgSuccess(response: BaseResponse)
    fun onPatchProfileImgFailure(message: String)

    fun onPatchNickNameSuccess(response: BaseResponse)
    fun onPatchNickNameFailure(message: String)

    fun onGetKeywordSuccess(response: KeywordResponse)
    fun onGetKeywordFailure(message: String)

    fun onPostKeywordSuccess(response: BaseResponse)
    fun onPostKeywordFailure(message: String)

    fun onDeleteKeywordSuccess(response: BaseResponse)
    fun onDeleteKeywordFailure(message: String)
}
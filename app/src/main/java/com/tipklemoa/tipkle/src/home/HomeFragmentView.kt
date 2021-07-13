package com.tipklemoa.tipkle.src.home

import com.tipklemoa.tipkle.config.BaseResponse
import com.tipklemoa.tipkle.src.home.model.BannerResponse
import com.tipklemoa.tipkle.src.home.model.CategoryListResponse
import com.tipklemoa.tipkle.src.home.model.HomePreviewFeedResponse
import com.tipklemoa.tipkle.src.home.model.LookAroundResponse
import com.tipklemoa.tipkle.src.login.model.KakaoLoginResponse
import com.tipklemoa.tipkle.src.login.model.KakaoRegisterResponse

interface HomeFragmentView {
    fun onGetPickedCategoryListSuccess(response: CategoryListResponse)
    fun onGetPickedCategoryListFailure(message: String)

    fun onGetBannerSuccess(response: BannerResponse)
    fun onGetBannerFailure(message: String)

    fun onGetHomePreviewFeedSuccess(response: HomePreviewFeedResponse)
    fun onGetHomePreviewFeedFailure(message: String)

    fun onPatchCategorySuccess(response: BaseResponse)
    fun onPatchCategoryFailure(message: String)

    fun onGetLookAroundFeedSuccess(response: LookAroundResponse)
    fun onGetLookAroundFeedFailure(message: String)
}
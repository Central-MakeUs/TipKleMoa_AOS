package com.tipklemoa.tipkle.src.home

import com.tipklemoa.tipkle.config.BaseResponse
import com.tipklemoa.tipkle.src.home.model.BannerResponse
import com.tipklemoa.tipkle.src.home.model.CategoryListResponse
import com.tipklemoa.tipkle.src.login.model.KakaoLoginResponse
import com.tipklemoa.tipkle.src.login.model.KakaoRegisterResponse

interface HomeFragmentView {
    fun onGetPickedCategoryListSuccess(response: CategoryListResponse)
    fun onGetPickedCategoryListFailure(message: String)

    fun onGetBannerSuccess(response: BannerResponse)
    fun onGetBannerFailure(message: String)

//    fun onPostKakaoLoginSuccess(response: KakaoLoginResponse)
//    fun onPostKakaoLoginFailure(message: String)
//
//    fun onPostKakaoRegisterSuccess(response: KakaoRegisterResponse)
//    fun onPostKakaoRegisterFailure(message: String)
//
//    fun onGetAutoLoginSuccess(response: BaseResponse)
//    fun onGetAutoLoginFailure(message: String)
}
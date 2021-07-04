package com.tipklemoa.tipkle.src.login

import com.tipklemoa.tipkle.src.login.model.KakaoLoginResponse

interface LoginActivityView {
//    카카오 로그인 검증
    fun onPostKakaoLoginSuccess(response: KakaoLoginResponse)
    fun onPostKakaoLoginFailure(message: String)

//    fun onPostKakaoRegisterSuccess(response: BaseResponse)
//    fun onPostKakaoRegisterFailure(message: String)
//
//    fun onPostKakaoLoginSuccess(response: KakaoLoginResponse)
//    fun onPostKakaoLoginFailure(message: String)
}
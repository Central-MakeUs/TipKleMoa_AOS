package com.tipklemoa.tipkle.src.login

import com.tipklemoa.tipkle.config.BaseResponse
import com.tipklemoa.tipkle.src.login.model.KakaoLoginResponse
import com.tipklemoa.tipkle.src.login.model.KakaoRegisterResponse

interface LoginActivityView {
//    카카오 로그인 검증
    fun onPostKakaoLoginSuccess(response: KakaoLoginResponse)
    fun onPostKakaoLoginFailure(message: String)

    fun onPostKakaoRegisterSuccess(response: KakaoRegisterResponse)
    fun onPostKakaoRegisterFailure(message: String)

    fun onGetAutoLoginSuccess(response: BaseResponse)
    fun onGetAutoLoginFailure(message: String)
}
package com.tipklemoa.tipkle.src.login

import com.tipklemoa.tipkle.config.BaseResponse
import com.tipklemoa.tipkle.src.login.model.KakaoLoginResponse
import com.tipklemoa.tipkle.src.login.model.KakaoRegisterResponse
import com.tipklemoa.tipkle.src.login.model.PostKakaoLoginRequest
import com.tipklemoa.tipkle.src.login.model.PostKakaoRegisterRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface LoginRetrofitInterface {

//    카카오 로그인 검증
    @POST("/login/kakao")
    fun postKakaoLogin(@Body params: PostKakaoLoginRequest): Call<KakaoLoginResponse>

    //카카오 회원가입
    @POST("/signup/kakao")
    fun postKakaoRegister(@Body params: PostKakaoRegisterRequest): Call<KakaoRegisterResponse>

    //카카오 자동로그인 (jwt 검증)
    @GET("/auto-login")
    fun getAutoLogin(): Call<BaseResponse>
}
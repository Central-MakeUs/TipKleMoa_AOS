package com.tipklemoa.tipkle.src.login

import com.tipklemoa.tipkle.src.login.model.KakaoLoginResponse
import com.tipklemoa.tipkle.src.login.model.PostKakaoLoginRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginRetrofitInterface {

//    카카오 로그인 검증
    @POST("/login/kakao")
    fun postKakaoLogin(@Body params: PostKakaoLoginRequest): Call<KakaoLoginResponse>

}
package com.tipklemoa.tipkle.src.login

import com.tipklemoa.tipkle.config.ApplicationClass
import com.tipklemoa.tipkle.src.login.model.KakaoLoginResponse
import com.tipklemoa.tipkle.src.login.model.PostKakaoLoginRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginService(val view: LoginActivityView) {
    fun tryPostKakaoLogin(postKakaoLoginRequest: PostKakaoLoginRequest){
       val loginRetrofitInterface = ApplicationClass.sRetrofit.create(LoginRetrofitInterface::class.java)
       loginRetrofitInterface.postKakaoLogin(postKakaoLoginRequest).enqueue(object: Callback<KakaoLoginResponse> {
           override fun onResponse(call: Call<KakaoLoginResponse>, response: Response<KakaoLoginResponse>) {
               view.onPostKakaoLoginSuccess(response.body() as KakaoLoginResponse)
           }

           override fun onFailure(call: Call<KakaoLoginResponse>, t: Throwable) {
               view.onPostKakaoLoginFailure(t.message ?: "통신 오류")
           }

       })
    }
//
//    fun tryPostKakaoRegister(
//        nickname:RequestBody, profileImg: MultipartBody.Part?, email:RequestBody,
//        access_token:RequestBody, kakaoImg: RequestBody?
//    ){
//        val loginRetrofitInterface = ApplicationClass.sRetrofit.create(LoginRetrofitInterface::class.java)
//        loginRetrofitInterface.postKakaoRegister(nickname, profileImg, email, access_token, kakaoImg).enqueue(object: Callback<KakaoRegisterResponse> {
//            override fun onResponse(call: Call<KakaoRegisterResponse>, response: Response<KakaoRegisterResponse>) {
//                view.onPostKakaoRegisterSuccess(response.body() as KakaoRegisterResponse)
//            }
//
//            override fun onFailure(call: Call<KakaoRegisterResponse>, t: Throwable) {
//                view.onPostKakaoRegisterFailure(t.message ?: "통신 오류")
//            }
//
//        })
//    }
//
//    fun tryPostKakaoLogin(postKakaoLoginRequest: PostKakaoLoginRequest){
//        val loginRetrofitInterface = ApplicationClass.sRetrofit.create(LoginRetrofitInterface::class.java)
//        loginRetrofitInterface.postKakaoLogin(postKakaoLoginRequest).enqueue(object: Callback<KakaoLoginResponse> {
//            override fun onResponse(call: Call<KakaoLoginResponse>, response: Response<KakaoLoginResponse>) {
//                view.onPostKakaoLoginSuccess(response.body() as KakaoLoginResponse)
//            }
//
//            override fun onFailure(call: Call<KakaoLoginResponse>, t: Throwable) {
//                view.onPostKakaoLoginFailure(t.message ?: "통신 오류")
//            }
//
//        })
//    }
}
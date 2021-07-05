package com.tipklemoa.tipkle.src.login

import com.tipklemoa.tipkle.config.ApplicationClass
import com.tipklemoa.tipkle.config.BaseResponse
import com.tipklemoa.tipkle.src.login.model.KakaoLoginResponse
import com.tipklemoa.tipkle.src.login.model.KakaoRegisterResponse
import com.tipklemoa.tipkle.src.login.model.PostKakaoLoginRequest
import com.tipklemoa.tipkle.src.login.model.PostKakaoRegisterRequest
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

    fun tryPostKakaoRegister(postKakaoRegisterRequest: PostKakaoRegisterRequest){
        val loginRetrofitInterface = ApplicationClass.sRetrofit.create(LoginRetrofitInterface::class.java)
        loginRetrofitInterface.postKakaoRegister(postKakaoRegisterRequest).enqueue(object: Callback<KakaoRegisterResponse> {
            override fun onResponse(call: Call<KakaoRegisterResponse>, response: Response<KakaoRegisterResponse>) {
                view.onPostKakaoRegisterSuccess(response.body() as KakaoRegisterResponse)
            }

            override fun onFailure(call: Call<KakaoRegisterResponse>, t: Throwable) {
                view.onPostKakaoRegisterFailure(t.message ?: "통신 오류")
            }

        })
    }
//
    fun tryGetAutoLogin(){
        val loginRetrofitInterface = ApplicationClass.sRetrofit.create(LoginRetrofitInterface::class.java)
        loginRetrofitInterface.getAutoLogin().enqueue(object: Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                view.onGetAutoLoginSuccess(response.body() as BaseResponse)
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                view.onGetAutoLoginFailure(t.message ?: "통신 오류")
            }

        })
    }
}
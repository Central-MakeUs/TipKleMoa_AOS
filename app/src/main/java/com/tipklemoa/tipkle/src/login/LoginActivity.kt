package com.tipklemoa.tipkle.src.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.tipklemoa.tipkle.config.BaseActivity
import com.tipklemoa.tipkle.databinding.ActivityLoginBinding
import com.tipklemoa.tipkle.src.home.MainActivity
import com.tipklemoa.tipkle.src.login.model.KakaoLoginResponse
import com.tipklemoa.tipkle.src.login.model.PostKakaoLoginRequest

class LoginActivity : BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate),
    LoginActivityView {

    private var accessToken:String?=null
    var email:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 로그인 공통 callback 구성
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                accessToken = null
                Log.e("kakao", "로그인 실패", error)
            }
            else if (token != null) {
                accessToken = token.accessToken

                Log.i("kakao", "로그인 성공 ${token.accessToken}")

                showLoadingDialog(this)
                val postKakaoLoginRequest = PostKakaoLoginRequest(accessToken!!)
                LoginService(this).tryPostKakaoLogin(postKakaoLoginRequest = postKakaoLoginRequest)
            }
        }

        binding.btnKakaoLogin.setOnClickListener {
            // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
                UserApiClient.instance.loginWithKakaoTalk(this, callback = callback)
            } else {
                UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
            }
        }

    }

    override fun onPostKakaoLoginSuccess(response: KakaoLoginResponse) {
        //멤버 아님 -> 회원가입
        val intent = Intent(this, RegisterWithNickNameActivity::class.java)
        intent.putExtra("accessToken", accessToken)
        if (response.result.isMember=='N'){ //회원아님
            startActivity(intent)
        }
        else{ //회원임
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    override fun onPostKakaoLoginFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)
    }
}
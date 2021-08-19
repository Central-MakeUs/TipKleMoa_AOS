package com.tipklemoa.tipkle.src.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.tipklemoa.tipkle.config.ApplicationClass
import com.tipklemoa.tipkle.config.BaseActivity
import com.tipklemoa.tipkle.config.BaseResponse
import com.tipklemoa.tipkle.databinding.ActivityLoginBinding
import com.tipklemoa.tipkle.src.main.MainActivity
import com.tipklemoa.tipkle.src.login.model.KakaoLoginResponse
import com.tipklemoa.tipkle.src.login.model.KakaoRegisterResponse
import com.tipklemoa.tipkle.src.login.model.PostKakaoLoginRequest

class LoginActivity : BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate),
    LoginActivityView {

    private var accessToken:String?=null
    val editor = ApplicationClass.sSharedPreferences.edit()
    var fcmToken:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!isNetworkConnected()){
            showCustomToast("네트워크 연결을 확인해주세요!")
        }

        getToken()

        // 로그인 공통 callback 구성
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                accessToken = null
                showCustomToast("카카오 로그인 실패! 다시 시도해주세요")
                Log.e("kakao", "로그인 실패", error)
            }
            else if (token != null) {
                accessToken = token.accessToken

                Log.i("kakao", "로그인 성공 ${token.accessToken}")

                if (fcmToken!=null){
                    showLoadingDialog(this)
                    val postKakaoLoginRequest = PostKakaoLoginRequest(accessToken!!, fcmToken!!)
                    LoginService(this).tryPostKakaoLogin(postKakaoLoginRequest = postKakaoLoginRequest)
                }
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
        dismissLoadingDialog()
        //멤버 아님 -> 회원가입
        if (response.result.isMember=='N'){ //회원아님
            val intent = Intent(this, RegisterWithNickNameActivity::class.java)
            intent.putExtra("accessToken", accessToken)
            intent.putExtra("fcmToken", fcmToken)
            startActivity(intent)
        }
        else{ //회원임
            editor.putString(ApplicationClass.X_ACCESS_TOKEN, response.result.jwt)
            editor.apply()
            this.finish()
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun getToken() {
        //토큰값을 받아옵니다.
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("확인", "Fetching FCM registration token failed", task.exception)
                fcmToken = null

                return@OnCompleteListener
            }

            // Get new FCM registration token
            fcmToken = task.result

            // Log and toast
            Log.d("확인", fcmToken.toString())
        })
    }

    override fun onPostKakaoLoginFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)
    }

    override fun onPostKakaoRegisterSuccess(response: KakaoRegisterResponse) {
        TODO("Not yet implemented")
    }

    override fun onPostKakaoRegisterFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onGetAutoLoginSuccess(response: BaseResponse) {

    }

    override fun onGetAutoLoginFailure(message: String) {
    }
}
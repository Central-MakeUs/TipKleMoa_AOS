package com.tipklemoa.tipkle.src.splash

import android.content.Intent
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Base64
import android.util.Log
import android.view.WindowManager
import androidx.annotation.RequiresApi
import com.tipklemoa.tipkle.config.ApplicationClass
import com.tipklemoa.tipkle.config.BaseActivity
import com.tipklemoa.tipkle.config.BaseResponse
import com.tipklemoa.tipkle.databinding.ActivitySplashBinding
import com.tipklemoa.tipkle.src.main.MainActivity
import com.tipklemoa.tipkle.src.login.LoginActivity
import com.tipklemoa.tipkle.src.login.LoginActivityView
import com.tipklemoa.tipkle.src.login.LoginService
import com.tipklemoa.tipkle.src.login.model.KakaoLoginResponse
import com.tipklemoa.tipkle.src.login.model.KakaoRegisterResponse
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class SplashActivity : BaseActivity<ActivitySplashBinding>(ActivitySplashBinding::inflate), LoginActivityView {
    //var postId = 0

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        if (intent.extras!=null){
//            postId = intent.extras!!.getInt("postId")
//            Log.d("postId", postId.toString())
//        }
        //postId = intent.extras.getInt("")

        // Hide the status bar.
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

//        프로젝트 해시키 알아볼때 쓰는 코드
        try {
            val info = packageManager.getPackageInfo(
                packageName, PackageManager.GET_SIGNATURES
            )
            for (signature in info.signatures) {
                val md: MessageDigest = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.e(
                    "MY KEY HASH:",
                    Base64.encodeToString(md.digest(), Base64.DEFAULT)
                )
            }
        } catch (e: PackageManager.NameNotFoundException) {
        } catch (e: NoSuchAlgorithmException) {
        }
    }

    override fun onPostKakaoLoginSuccess(response: KakaoLoginResponse) {
        TODO("Not yet implemented")
    }

    override fun onPostKakaoLoginFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onPostKakaoRegisterSuccess(response: KakaoRegisterResponse) {
        TODO("Not yet implemented")
    }

    override fun onPostKakaoRegisterFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onGetAutoLoginSuccess(response: BaseResponse) {
        dismissLoadingDialog()
        if (response.code==1000){ //JWT 토큰 검증 성공 -> 메인 액티비티로
            Handler(Looper.getMainLooper()).postDelayed({
                Log.d("postId", intent.getIntExtra("postId", 0).toString())
                val toMain = Intent(this, MainActivity::class.java)
                toMain.putExtra("postId", intent.getIntExtra("postId", 0))
                startActivity(toMain)
                finish()
            }, 2000)
        }
        else{ //실패 -> 로그인 화면으로
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }, 2000)
        }
    }

    override fun onGetAutoLoginFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)
    }
}
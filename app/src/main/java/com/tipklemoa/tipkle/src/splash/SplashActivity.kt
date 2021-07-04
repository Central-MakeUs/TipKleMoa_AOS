package com.tipklemoa.tipkle.src.splash

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Base64
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.annotation.RequiresApi
import com.tipklemoa.tipkle.src.login.LoginActivity
import com.tipklemoa.tipkle.config.BaseActivity
import com.tipklemoa.tipkle.databinding.ActivitySplashBinding
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class SplashActivity : BaseActivity<ActivitySplashBinding>(ActivitySplashBinding::inflate) {
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Hide the status bar.
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)

////        프로젝트 해시키 알아볼때 쓰는 코드
//        try {
//            val info = packageManager.getPackageInfo(
//                packageName, PackageManager.GET_SIGNATURES
//            )
//            for (signature in info.signatures) {
//                val md: MessageDigest = MessageDigest.getInstance("SHA")
//                md.update(signature.toByteArray())
//                Log.e(
//                    "MY KEY HASH:",
//                    Base64.encodeToString(md.digest(), Base64.DEFAULT)
//                )
//            }
//        } catch (e: PackageManager.NameNotFoundException) {
//        } catch (e: NoSuchAlgorithmException) {
//        }

//        if(ApplicationClass.sSharedPreferences.getString(ApplicationClass.X_ACCESS_TOKEN, "na") == "na"){
//            Handler(Looper.getMainLooper()).postDelayed({
//                startActivity(Intent(this, LoginActivity::class.java))
//                finish()
//            }, 2000)
//        }else{
//            Handler(Looper.getMainLooper()).postDelayed({
//                startActivity(Intent(this, MainActivity::class.java))
//                finish()
//            }, 2000)
//        }

        Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }, 2000)
    }
}
package com.tipklemoa.tipkle.config

import com.tipklemoa.tipkle.config.ApplicationClass.Companion.X_ACCESS_TOKEN
import com.tipklemoa.tipkle.config.ApplicationClass.Companion.kakaoToken
import com.tipklemoa.tipkle.config.ApplicationClass.Companion.sSharedPreferences
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class XAccessTokenInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder: Request.Builder = chain.request().newBuilder()
        val jwtToken: String? = sSharedPreferences.getString(X_ACCESS_TOKEN, null)
        var kakaoToken:String? = sSharedPreferences.getString(kakaoToken, null)

        if (kakaoToken!=null){
            builder.addHeader("access_token", kakaoToken)
        }
        if (jwtToken != null) {
            builder.addHeader("access-token", jwtToken)
        }
        return chain.proceed(builder.build())
    }
}
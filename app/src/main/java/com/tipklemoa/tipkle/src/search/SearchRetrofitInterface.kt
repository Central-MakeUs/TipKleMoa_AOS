package com.tipklemoa.tipkle.src.search

import com.tipklemoa.tipkle.config.BaseResponse
import com.tipklemoa.tipkle.src.home.model.*
import com.tipklemoa.tipkle.src.login.model.KakaoLoginResponse
import com.tipklemoa.tipkle.src.login.model.KakaoRegisterResponse
import com.tipklemoa.tipkle.src.login.model.PostKakaoLoginRequest
import com.tipklemoa.tipkle.src.login.model.PostKakaoRegisterRequest
import com.tipklemoa.tipkle.src.search.model.KeywordResponse
import retrofit2.Call
import retrofit2.http.*

interface SearchRetrofitInterface {

//  검색어 조회
    @GET("/search")
    fun getKeyword(@Query("order") order:String): Call<KeywordResponse>
}
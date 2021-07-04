package com.tipklemoa.tipkle.src.login.model

import com.google.gson.annotations.SerializedName
import com.tipklemoa.tipkle.config.BaseResponse

data class KakaoLoginResponse(@SerializedName("result") val result: ResultKakaoLogin): BaseResponse()
package com.tipklemoa.tipkle.src.mypage.model

import com.google.gson.annotations.SerializedName
import com.tipklemoa.tipkle.config.BaseResponse

data class MyPageResponse(@SerializedName("result") val result: ResultMyPage): BaseResponse()
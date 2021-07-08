package com.tipklemoa.tipkle.src.home.model

import com.google.gson.annotations.SerializedName
import com.tipklemoa.tipkle.config.BaseResponse

data class BannerResponse(@SerializedName("result") val result: List<ResultBanner>): BaseResponse()
package com.tipklemoa.tipkle.src.home.model

import com.google.gson.annotations.SerializedName
import com.tipklemoa.tipkle.config.BaseResponse

data class LookAroundResponse(@SerializedName("result") val result: List<ResultLookAround>): BaseResponse()
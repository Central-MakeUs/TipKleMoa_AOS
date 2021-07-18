package com.tipklemoa.tipkle.src.model

import com.google.gson.annotations.SerializedName
import com.tipklemoa.tipkle.config.BaseResponse

data class NewTipResponse(@SerializedName("result") val result: ResultNewTip): BaseResponse()
package com.tipklemoa.tipkle.src.search.model

import com.google.gson.annotations.SerializedName
import com.tipklemoa.tipkle.config.BaseResponse

data class KeywordResponse(@SerializedName("result") val result: List<ResultKeyword>): BaseResponse()
package com.tipklemoa.tipkle.src.model

import com.google.gson.annotations.SerializedName
import com.tipklemoa.tipkle.config.BaseResponse

data class DetailFeedResponse(@SerializedName("result") val result: List<ResultDetailFeed>): BaseResponse()
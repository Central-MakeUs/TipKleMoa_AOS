package com.tipklemoa.tipkle.src.search.model

import com.google.gson.annotations.SerializedName
import com.tipklemoa.tipkle.config.BaseResponse

data class SearchResponse(@SerializedName("result") val result: List<ResultSearch>): BaseResponse()
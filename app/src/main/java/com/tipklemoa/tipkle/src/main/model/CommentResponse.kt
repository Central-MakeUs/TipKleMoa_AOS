package com.tipklemoa.tipkle.src.main.model

import com.google.gson.annotations.SerializedName
import com.tipklemoa.tipkle.config.BaseResponse

data class CommentResponse(@SerializedName("result") val result: List<ResultComment>): BaseResponse()
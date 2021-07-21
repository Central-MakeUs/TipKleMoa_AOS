package com.tipklemoa.tipkle.src.tipkle.model

import com.google.gson.annotations.SerializedName
import com.tipklemoa.tipkle.config.BaseResponse

data class MakeFolderResponse(@SerializedName("result") val result: List<ResultMakeFolder>): BaseResponse()
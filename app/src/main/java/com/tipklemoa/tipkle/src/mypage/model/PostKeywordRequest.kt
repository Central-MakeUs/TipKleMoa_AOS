package com.tipklemoa.tipkle.src.mypage.model

import com.google.gson.annotations.SerializedName

data class PostKeywordRequest(
    @SerializedName("keyword") val keyword: String
)

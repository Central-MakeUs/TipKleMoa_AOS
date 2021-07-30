package com.tipklemoa.tipkle.src.mypage.model

import com.google.gson.annotations.SerializedName

data class ResultKeyword(@SerializedName("keywordId") val keywordId: Int,
                         @SerializedName("keyword") val keyword: String)
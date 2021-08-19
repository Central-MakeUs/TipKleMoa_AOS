package com.tipklemoa.tipkle.src.main.model

import com.google.gson.annotations.SerializedName

data class ResultComment(@SerializedName("commentId") val commentId: Int,
                         @SerializedName("userId") val userId: Int,
                         @SerializedName("nickName") val nickName: String,
                         @SerializedName("profileImgUrl") val profileImgUrl: String,
                         @SerializedName("content") val content: String,
                         @SerializedName("createdAt") val createdAt: String,
                         @SerializedName("isAuthor") val isAuthor: Char)
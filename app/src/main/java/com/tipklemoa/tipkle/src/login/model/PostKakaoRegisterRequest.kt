package com.tipklemoa.tipkle.src.login.model

import com.google.gson.annotations.SerializedName

data class PostKakaoRegisterRequest(
    @SerializedName("accessToken") val accessToken: String,
    @SerializedName("nickName") val nickName: String,
    @SerializedName("category") val category: MutableList<Int>,
    @SerializedName("fcmToken") val fcmToken: String
)

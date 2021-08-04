package com.tipklemoa.tipkle.src.login.model

import com.google.gson.annotations.SerializedName

data class PostKakaoLoginRequest(
    @SerializedName("accessToken") val accessToken: String,
    @SerializedName("fcmToken") val fcmToken: String
)

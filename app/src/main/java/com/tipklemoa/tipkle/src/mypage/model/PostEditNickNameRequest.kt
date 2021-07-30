package com.tipklemoa.tipkle.src.mypage.model

import com.google.gson.annotations.SerializedName

data class PostEditNickNameRequest(
    @SerializedName("nickName") val nickName: String
)

package com.tipklemoa.tipkle.src.mypage.model

import com.google.gson.annotations.SerializedName

data class PostEditProfileRequest(
    @SerializedName("imgUrl") val imgUrl: String?=null
)

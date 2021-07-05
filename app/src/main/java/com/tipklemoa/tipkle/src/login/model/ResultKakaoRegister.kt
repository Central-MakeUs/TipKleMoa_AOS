package com.tipklemoa.tipkle.src.login.model

import com.google.gson.annotations.SerializedName

data class ResultKakaoRegister(@SerializedName("userId") val userId: Int,
                               @SerializedName("jwt") val jwt: String
)
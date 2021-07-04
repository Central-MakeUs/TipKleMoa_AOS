package com.tipklemoa.tipkle.src.login.model

import com.google.gson.annotations.SerializedName

data class ResultKakaoLogin(@SerializedName("isMember") val isMember: Char,
                            @SerializedName("userId") val userId: Int,
                            @SerializedName("jwt") val jwt: String
)
package com.tipklemoa.tipkle.src.mypage.model

import com.google.gson.annotations.SerializedName

data class ResultMyPage(@SerializedName("level") val level: Int,
                        @SerializedName("levelName") val levelName: String,
                        @SerializedName("profileImgUrl") val profileImgUrl: String,
                        @SerializedName("nickName") val nickName: String,
                        @SerializedName("levelbar") val levelbar: Int,
                        @SerializedName("achievement") val achievement: String
)
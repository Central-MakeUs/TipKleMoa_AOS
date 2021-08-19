package com.tipklemoa.tipkle.src.main.model

import com.google.gson.annotations.SerializedName

data class PostStarRequest(
    @SerializedName("star") val star:Int)

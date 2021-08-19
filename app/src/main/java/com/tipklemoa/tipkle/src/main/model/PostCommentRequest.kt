package com.tipklemoa.tipkle.src.main.model

import com.google.gson.annotations.SerializedName

data class PostCommentRequest(
    @SerializedName("content") val content:String)

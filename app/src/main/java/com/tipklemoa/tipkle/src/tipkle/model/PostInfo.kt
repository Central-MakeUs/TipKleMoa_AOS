package com.tipklemoa.tipkle.src.tipkle.model

import com.google.gson.annotations.SerializedName

data class PostInfo(
    @SerializedName("postId") val postId:Int,
    @SerializedName("imgUrl") val imgUrl:String)

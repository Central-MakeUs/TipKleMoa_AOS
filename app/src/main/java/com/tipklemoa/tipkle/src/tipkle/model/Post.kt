package com.tipklemoa.tipkle.src.tipkle.model

import com.google.gson.annotations.SerializedName

data class Post(
    @SerializedName("postId") val postId:Int,
    @SerializedName("title") val title:String,
    @SerializedName("imgUrl") val imgUrl:String)

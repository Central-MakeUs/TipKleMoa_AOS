package com.tipklemoa.tipkle.src.home.model

import com.google.gson.annotations.SerializedName

data class ResultHomePreviewFeed(@SerializedName("postId") val postId: Int,
                                 @SerializedName("title") val title: String,
                                 @SerializedName("thumbnailUrl") val thumbnailUrl:String
)
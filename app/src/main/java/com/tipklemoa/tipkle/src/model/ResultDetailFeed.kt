package com.tipklemoa.tipkle.src.model

import com.google.gson.annotations.SerializedName

data class ResultDetailFeed(@SerializedName("postId") val postId: Int,
                            @SerializedName("userId") val userId: Int,
                            @SerializedName("nickName") val nickName: String,
                            @SerializedName("profileImgUrl") val profileImgUrl: String,
                            @SerializedName("whenText") val whenText: String,
                            @SerializedName("howText") val howText: String,
                            @SerializedName("description") val description: String?=null,
                            @SerializedName("score") val score: String,
                            @SerializedName("star") val star: Int,
                            @SerializedName("isStarred") val isStarred: Char,
                            @SerializedName("isBookMarked") val isBookMarked:Char,
                            @SerializedName("commentCount") val commentCount:Int,
                            @SerializedName("imgUrl") val imgUrl:List<String>
)
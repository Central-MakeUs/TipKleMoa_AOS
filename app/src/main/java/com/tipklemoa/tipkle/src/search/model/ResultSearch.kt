package com.tipklemoa.tipkle.src.search.model

import com.google.gson.annotations.SerializedName

data class ResultSearch(@SerializedName("postId") val postId: Int,
                        @SerializedName("userId") val userId: Int,
                        @SerializedName("nickName") val nickName: String,
                        @SerializedName("profileImgUrl") val profileImgUrl: String,
                        @SerializedName("whenText") val whenText: String,
                        @SerializedName("howText") val howText: String,
                        @SerializedName("description") val description: String,
                        @SerializedName("score") val score: String,
                        @SerializedName("star") val star: Int,
                        @SerializedName("createdAt") val createdAt: String,
                        @SerializedName("imgUrl") val imgUrl:List<String>
)
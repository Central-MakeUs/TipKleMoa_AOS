package com.tipklemoa.tipkle.src.tipkle.model

import com.google.gson.annotations.SerializedName

data class ResultFolderFeed(@SerializedName("folderName") val folderName:String,
                            @SerializedName("post") val post:List<Post>)
package com.tipklemoa.tipkle.src.tipkle.model

import com.google.gson.annotations.SerializedName

data class ResultTipFolder(@SerializedName("folderId") val folderId:Int,
                           @SerializedName("folderName") val folderName:String,
                           @SerializedName("postsInfo") val postsInfo:List<PostInfo>
                        )
package com.tipklemoa.tipkle.src.main.model

import com.google.gson.annotations.SerializedName

data class PostNewTipRequest(
    @SerializedName("category") val category: String,
    @SerializedName("whenText") val whenText:String,
    @SerializedName("howText") val howText:String,
    @SerializedName("description") val description:String,
    @SerializedName("img") val img:List<String>)

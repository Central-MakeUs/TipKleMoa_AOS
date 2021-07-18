package com.tipklemoa.tipkle.src.model

import com.google.gson.annotations.SerializedName
import java.util.ArrayList

data class PostNewTipRequest(
    @SerializedName("category") val category: String,
    @SerializedName("whenText") val whenText:String,
    @SerializedName("howText") val howText:String,
    @SerializedName("descrption") val descrption:String,
    @SerializedName("img") val img:List<String>)

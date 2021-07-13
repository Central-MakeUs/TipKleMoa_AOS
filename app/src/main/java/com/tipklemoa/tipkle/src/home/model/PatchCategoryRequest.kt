package com.tipklemoa.tipkle.src.home.model

import com.google.gson.annotations.SerializedName

data class PatchCategoryRequest(
    @SerializedName("category") val category: List<Int>
)

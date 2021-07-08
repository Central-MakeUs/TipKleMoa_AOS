package com.tipklemoa.tipkle.src.home.model

import com.google.gson.annotations.SerializedName

data class ResultCategoryList(@SerializedName("categoryId") val categoryId: Int,
                              @SerializedName("categoryName") val categoryName: String
)
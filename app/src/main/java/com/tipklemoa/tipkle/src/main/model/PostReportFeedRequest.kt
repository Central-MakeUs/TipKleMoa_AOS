package com.tipklemoa.tipkle.src.main.model

import com.google.gson.annotations.SerializedName

data class PostReportFeedRequest(@SerializedName("reason") val reason: String)

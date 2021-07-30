package com.tipklemoa.tipkle.src.model

import com.google.gson.annotations.SerializedName
import java.util.ArrayList

data class PostReportFeedRequest(@SerializedName("reason") val reason: String)

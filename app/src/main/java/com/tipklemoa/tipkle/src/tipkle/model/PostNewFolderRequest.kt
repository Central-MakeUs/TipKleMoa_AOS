package com.tipklemoa.tipkle.src.tipkle.model

import com.google.gson.annotations.SerializedName
import java.util.ArrayList

data class PostNewFolderRequest(@SerializedName("folderName") val folderName: String)

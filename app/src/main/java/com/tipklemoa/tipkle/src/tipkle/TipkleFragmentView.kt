package com.tipklemoa.tipkle.src.tipkle

import com.tipklemoa.tipkle.config.BaseResponse
import com.tipklemoa.tipkle.src.home.model.BannerResponse
import com.tipklemoa.tipkle.src.home.model.HomePreviewFeedResponse
import com.tipklemoa.tipkle.src.home.model.LookAroundResponse
import com.tipklemoa.tipkle.src.tipkle.model.MakeFolderResponse
import com.tipklemoa.tipkle.src.tipkle.model.TipFolderResponse

interface TipkleFragmentView {
    fun onGetTipFolderListSuccess(response: TipFolderResponse)
    fun onGetTipFolderListFailure(message: String)

    fun onPostFolderSuccess(response: MakeFolderResponse)
    fun onPostFolderFailure(message: String)
//
//    fun onGetHomePreviewFeedSuccess(response: HomePreviewFeedResponse)
//    fun onGetHomePreviewFeedFailure(message: String)
//
//    fun onPatchCategorySuccess(response: BaseResponse)
//    fun onPatchCategoryFailure(message: String)
//
//    fun onGetLookAroundFeedSuccess(response: LookAroundResponse)
//    fun onGetLookAroundFeedFailure(message: String)
}
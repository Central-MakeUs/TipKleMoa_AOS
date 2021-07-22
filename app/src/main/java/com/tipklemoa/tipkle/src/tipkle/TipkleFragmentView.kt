package com.tipklemoa.tipkle.src.tipkle

import com.tipklemoa.tipkle.config.BaseResponse
import com.tipklemoa.tipkle.src.tipkle.model.FolderFeedResponse
import com.tipklemoa.tipkle.src.tipkle.model.MakeFolderResponse
import com.tipklemoa.tipkle.src.tipkle.model.TipFolderResponse

interface TipkleFragmentView {
    fun onGetTipFolderListSuccess(response: TipFolderResponse)
    fun onGetTipFolderListFailure(message: String)

    fun onPostFolderSuccess(response: MakeFolderResponse)
    fun onPostFolderFailure(message: String)

    fun onGetFolderFeedSuccess(response: FolderFeedResponse)
    fun onGetFolderFeedFailure(message: String)

    fun onDeleteFolderSuccess(response: BaseResponse)
    fun onDeleteFolderFailure(message: String)
//
//    fun onGetLookAroundFeedSuccess(response: LookAroundResponse)
//    fun onGetLookAroundFeedFailure(message: String)
}
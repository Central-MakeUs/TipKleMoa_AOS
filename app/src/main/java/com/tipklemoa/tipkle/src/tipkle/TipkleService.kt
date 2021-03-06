package com.tipklemoa.tipkle.src.tipkle

import com.tipklemoa.tipkle.config.ApplicationClass
import com.tipklemoa.tipkle.config.BaseResponse
import com.tipklemoa.tipkle.src.home.model.*
import com.tipklemoa.tipkle.src.tipkle.model.FolderFeedResponse
import com.tipklemoa.tipkle.src.tipkle.model.MakeFolderResponse
import com.tipklemoa.tipkle.src.tipkle.model.PostNewFolderRequest
import com.tipklemoa.tipkle.src.tipkle.model.TipFolderResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TipkleService(val view: TipkleFragmentView) {

    fun tryGetFolderList(){
        val tipkleRetrofitInterface = ApplicationClass.sRetrofit.create(TipkleRetrofitInterface::class.java)
        tipkleRetrofitInterface.getTipkleFolders().enqueue(object: Callback<TipFolderResponse> {
            override fun onResponse(call: Call<TipFolderResponse>, response: Response<TipFolderResponse>) {
                view.onGetTipFolderListSuccess(response.body() as TipFolderResponse)
            }

            override fun onFailure(call: Call<TipFolderResponse>, t: Throwable) {
                view.onGetTipFolderListFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryMakeFolder(postNewFolderRequest: PostNewFolderRequest){
        val tipkleRetrofitInterface = ApplicationClass.sRetrofit.create(TipkleRetrofitInterface::class.java)
        tipkleRetrofitInterface.postFolders(postNewFolderRequest).enqueue(object: Callback<MakeFolderResponse> {
            override fun onResponse(call: Call<MakeFolderResponse>, response: Response<MakeFolderResponse>) {
                view.onPostFolderSuccess(response.body() as MakeFolderResponse)
            }

            override fun onFailure(call: Call<MakeFolderResponse>, t: Throwable) {
                view.onPostFolderFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryGetFolderFeed(folderId:Int){
        val tipkleRetrofitInterface = ApplicationClass.sRetrofit.create(TipkleRetrofitInterface::class.java)
        tipkleRetrofitInterface.getFolderFeed(folderId).enqueue(object: Callback<FolderFeedResponse> {
            override fun onResponse(call: Call<FolderFeedResponse>, response: Response<FolderFeedResponse>) {
                view.onGetFolderFeedSuccess(response.body() as FolderFeedResponse)
            }

            override fun onFailure(call: Call<FolderFeedResponse>, t: Throwable) {
                view.onGetFolderFeedFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryDeleteFolder(folderId:Int){
       val tipkleRetrofitInterface = ApplicationClass.sRetrofit.create(TipkleRetrofitInterface::class.java)
        tipkleRetrofitInterface.deleteFolder(folderId).enqueue(object: Callback<BaseResponse> {
           override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
               view.onDeleteFolderSuccess(response.body() as BaseResponse)
           }

           override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
               view.onDeleteFolderFailure(t.message ?: "통신 오류")
           }

       })
    }
//
//    fun tryGetPickedCategoryList(){
//        val homeRetrofitInterface = ApplicationClass.sRetrofit.create(TipkleRetrofitInterface::class.java)
//    homeRetrofitInterface.getPickedCategoryList().enqueue(object: Callback<CategoryListResponse> {
//            override fun onResponse(call: Call<CategoryListResponse>, response: Response<CategoryListResponse>) {
//                view.onGetPickedCategoryListSuccess(response.body() as CategoryListResponse)
//            }
//
//            override fun onFailure(call: Call<CategoryListResponse>, t: Throwable) {
//                view.onGetPickedCategoryListFailure(t.message ?: "통신 오류")
//            }
//        })
//    }
}
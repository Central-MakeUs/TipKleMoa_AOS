package com.tipklemoa.tipkle.src

import com.tipklemoa.tipkle.config.ApplicationClass
import com.tipklemoa.tipkle.config.BaseResponse
import com.tipklemoa.tipkle.src.home.model.*
import com.tipklemoa.tipkle.src.model.DetailFeedResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainService(val view: MainView) {

    fun tryHomePreviewFeed(postId:Int){
        val mainRetrofitInterface = ApplicationClass.sRetrofit.create(MainRetrofitInterface::class.java)
        mainRetrofitInterface.getFeedDetail(postId).enqueue(object: Callback<DetailFeedResponse> {
            override fun onResponse(call: Call<DetailFeedResponse>, response: Response<DetailFeedResponse>) {
                view.onGetFeedDetailSuccess(response.body() as DetailFeedResponse)
            }

            override fun onFailure(call: Call<DetailFeedResponse>, t: Throwable) {
                view.onGetFeedDetailFailure(t.message ?: "통신 오류")
            }
        })
    }

//    fun tryLookAroundFeed(categoryName:String, order:String, search:String?=null){
//        val homeRetrofitInterface = ApplicationClass.sRetrofit.create(HomeRetrofitInterface::class.java)
//        homeRetrofitInterface.getLookAroundFeed(categoryName, order, search).enqueue(object: Callback<LookAroundResponse> {
//            override fun onResponse(call: Call<LookAroundResponse>, response: Response<LookAroundResponse>) {
//                view.onGetLookAroundFeedSuccess(response.body() as LookAroundResponse)
//            }
//
//            override fun onFailure(call: Call<LookAroundResponse>, t: Throwable) {
//                view.onGetLookAroundFeedFailure(t.message ?: "통신 오류")
//            }
//        })
//    }
//
//    fun tryPatchCategory(patchCategoryRequest: PatchCategoryRequest){
//       val loginRetrofitInterface = ApplicationClass.sRetrofit.create(HomeRetrofitInterface::class.java)
//       loginRetrofitInterface.patchCategory(patchCategoryRequest).enqueue(object: Callback<BaseResponse> {
//           override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
//               view.onPatchCategorySuccess(response.body() as BaseResponse)
//           }
//
//           override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
//               view.onPatchCategoryFailure(t.message ?: "통신 오류")
//           }
//
//       })
//    }
//
//    fun tryGetPickedCategoryList(){
//        val homeRetrofitInterface = ApplicationClass.sRetrofit.create(HomeRetrofitInterface::class.java)
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
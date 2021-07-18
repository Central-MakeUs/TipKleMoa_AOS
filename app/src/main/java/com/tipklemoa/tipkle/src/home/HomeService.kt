package com.tipklemoa.tipkle.src.home

import com.tipklemoa.tipkle.config.ApplicationClass
import com.tipklemoa.tipkle.config.BaseResponse
import com.tipklemoa.tipkle.src.home.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeService(val view: HomeFragmentView) {

    fun tryGetBanner(){
        val homeRetrofitInterface = ApplicationClass.sRetrofit.create(HomeRetrofitInterface::class.java)
        homeRetrofitInterface.getBanners().enqueue(object: Callback<BannerResponse> {
            override fun onResponse(call: Call<BannerResponse>, response: Response<BannerResponse>) {
                view.onGetBannerSuccess(response.body() as BannerResponse)
            }

            override fun onFailure(call: Call<BannerResponse>, t: Throwable) {
                view.onGetBannerFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryHomePreviewFeed(categoryName:String, order:String){
        val homeRetrofitInterface = ApplicationClass.sRetrofit.create(HomeRetrofitInterface::class.java)
        homeRetrofitInterface.getPreviewFeed(categoryName, order).enqueue(object: Callback<HomePreviewFeedResponse> {
            override fun onResponse(call: Call<HomePreviewFeedResponse>, response: Response<HomePreviewFeedResponse>) {
                view.onGetHomePreviewFeedSuccess(response.body() as HomePreviewFeedResponse)
            }

            override fun onFailure(call: Call<HomePreviewFeedResponse>, t: Throwable) {
                view.onGetHomePreviewFeedFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryLookAroundFeed(categoryName:String, order:String, search:String?=null, page:Int){
        val homeRetrofitInterface = ApplicationClass.sRetrofit.create(HomeRetrofitInterface::class.java)
        homeRetrofitInterface.getLookAroundFeed(categoryName, order, search, page, 5).enqueue(object: Callback<LookAroundResponse> {
            override fun onResponse(call: Call<LookAroundResponse>, response: Response<LookAroundResponse>) {
                view.onGetLookAroundFeedSuccess(response.body() as LookAroundResponse)
            }

            override fun onFailure(call: Call<LookAroundResponse>, t: Throwable) {
                view.onGetLookAroundFeedFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryPatchCategory(patchCategoryRequest: PatchCategoryRequest){
       val loginRetrofitInterface = ApplicationClass.sRetrofit.create(HomeRetrofitInterface::class.java)
       loginRetrofitInterface.patchCategory(patchCategoryRequest).enqueue(object: Callback<BaseResponse> {
           override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
               view.onPatchCategorySuccess(response.body() as BaseResponse)
           }

           override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
               view.onPatchCategoryFailure(t.message ?: "통신 오류")
           }

       })
    }

    fun tryGetPickedCategoryList(){
        val homeRetrofitInterface = ApplicationClass.sRetrofit.create(HomeRetrofitInterface::class.java)
    homeRetrofitInterface.getPickedCategoryList().enqueue(object: Callback<CategoryListResponse> {
            override fun onResponse(call: Call<CategoryListResponse>, response: Response<CategoryListResponse>) {
                view.onGetPickedCategoryListSuccess(response.body() as CategoryListResponse)
            }

            override fun onFailure(call: Call<CategoryListResponse>, t: Throwable) {
                view.onGetPickedCategoryListFailure(t.message ?: "통신 오류")
            }
        })
    }
}
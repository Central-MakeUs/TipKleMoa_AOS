package com.tipklemoa.tipkle.src.mypage

import com.tipklemoa.tipkle.config.ApplicationClass
import com.tipklemoa.tipkle.config.BaseResponse
import com.tipklemoa.tipkle.src.mypage.model.MyPageResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyPageService(val view: MyPageView) {

    fun tryGetMyPage(){
        val myPageRetrofitInterface = ApplicationClass.sRetrofit.create(MyPageRetrofitInterface::class.java)
        myPageRetrofitInterface.getFeedDetail().enqueue(object: Callback<MyPageResponse> {
            override fun onResponse(call: Call<MyPageResponse>, response: Response<MyPageResponse>) {
                view.onGetMyPageSuccess(response.body() as MyPageResponse)
            }

            override fun onFailure(call: Call<MyPageResponse>, t: Throwable) {
                view.onGetMyPageFailure(t.message ?: "통신 오류")
            }
        })
    }

    //로그아웃
    fun tryLogout(){
        val mainRetrofitInterface = ApplicationClass.sRetrofit.create(MyPageRetrofitInterface::class.java)
        mainRetrofitInterface.patchLogout().enqueue(object: Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                view.onLogoutSuccess(response.body() as BaseResponse)
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                view.onLogoutFailure(t.message ?: "통신 오류")
            }
        })
    }
//
    fun tryDeleteUsers(){
        val mainRetrofitInterface = ApplicationClass.sRetrofit.create(MyPageRetrofitInterface::class.java)
        mainRetrofitInterface.deleteUser().enqueue(object: Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                view.onDeleteUserSuccess(response.body() as BaseResponse)
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                view.onDeleteUserFailure(t.message ?: "통신 오류")
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
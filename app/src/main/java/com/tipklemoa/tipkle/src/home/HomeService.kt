package com.tipklemoa.tipkle.src.home

import com.tipklemoa.tipkle.config.ApplicationClass
import com.tipklemoa.tipkle.config.BaseResponse
import com.tipklemoa.tipkle.src.home.model.BannerResponse
import com.tipklemoa.tipkle.src.home.model.CategoryListResponse
import com.tipklemoa.tipkle.src.login.model.KakaoLoginResponse
import com.tipklemoa.tipkle.src.login.model.KakaoRegisterResponse
import com.tipklemoa.tipkle.src.login.model.PostKakaoLoginRequest
import com.tipklemoa.tipkle.src.login.model.PostKakaoRegisterRequest
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
//    fun tryPostKakaoLogin(postKakaoLoginRequest: PostKakaoLoginRequest){
//       val loginRetrofitInterface = ApplicationClass.sRetrofit.create(HomeRetrofitInterface::class.java)
//       loginRetrofitInterface.postKakaoLogin(postKakaoLoginRequest).enqueue(object: Callback<KakaoLoginResponse> {
//           override fun onResponse(call: Call<KakaoLoginResponse>, response: Response<KakaoLoginResponse>) {
//               view.onPostKakaoLoginSuccess(response.body() as KakaoLoginResponse)
//           }
//
//           override fun onFailure(call: Call<KakaoLoginResponse>, t: Throwable) {
//               view.onPostKakaoLoginFailure(t.message ?: "통신 오류")
//           }
//
//       })
//    }
//
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
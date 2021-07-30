package com.tipklemoa.tipkle.src.mypage

import com.tipklemoa.tipkle.config.ApplicationClass
import com.tipklemoa.tipkle.config.BaseResponse
import com.tipklemoa.tipkle.src.mypage.model.*
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

    fun tryPatchProfile(patchProfileRequest: PostEditProfileRequest){
        val myPageRetrofitInterface = ApplicationClass.sRetrofit.create(MyPageRetrofitInterface::class.java)
        myPageRetrofitInterface.patchProfileImage(patchProfileRequest).enqueue(object: Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                view.onPatchProfileImgSuccess(response.body() as BaseResponse)
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                view.onPatchProfileImgFailure(t.message ?: "통신 오류")
            }

        })
    }
    fun tryPatchNickName(patchNickNameRequest: PostEditNickNameRequest){
       val myPageRetrofitInterface = ApplicationClass.sRetrofit.create(MyPageRetrofitInterface::class.java)
    myPageRetrofitInterface.patchUserNickName(patchNickNameRequest).enqueue(object: Callback<BaseResponse> {
           override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
               view.onPatchNickNameSuccess(response.body() as BaseResponse)
           }

           override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
               view.onPatchNickNameFailure(t.message ?: "통신 오류")
           }

       })
    }

    fun tryGetKeywords(){
        val myPageRetrofitInterface = ApplicationClass.sRetrofit.create(MyPageRetrofitInterface::class.java)
        myPageRetrofitInterface.getKeywords().enqueue(object: Callback<KeywordResponse> {
            override fun onResponse(call: Call<KeywordResponse>, response: Response<KeywordResponse>) {
                view.onGetKeywordSuccess(response.body() as KeywordResponse)
            }

            override fun onFailure(call: Call<KeywordResponse>, t: Throwable) {
                view.onGetKeywordFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryPostKeyword(postKeywordRequest : PostKeywordRequest){
        val myPageRetrofitInterface = ApplicationClass.sRetrofit.create(MyPageRetrofitInterface::class.java)
        myPageRetrofitInterface.postKeyword(postKeywordRequest).enqueue(object: Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                view.onPostKeywordSuccess(response.body() as BaseResponse)
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                view.onPostKeywordFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryDeleteKeyword(keywordId:Int){
        val myPageRetrofitInterface = ApplicationClass.sRetrofit.create(MyPageRetrofitInterface::class.java)
        myPageRetrofitInterface.deleteKeyword(keywordId).enqueue(object: Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                view.onDeleteKeywordSuccess(response.body() as BaseResponse)
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                view.onDeleteKeywordFailure(t.message ?: "통신 오류")
            }
        })
    }
}
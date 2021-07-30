package com.tipklemoa.tipkle.src.mypage

import android.os.Bundle
import com.tipklemoa.tipkle.config.BaseActivity
import com.tipklemoa.tipkle.config.BaseResponse
import com.tipklemoa.tipkle.databinding.ActivityKeywordBinding
import com.tipklemoa.tipkle.src.mypage.model.KeywordResponse
import com.tipklemoa.tipkle.src.mypage.model.MyPageResponse

class KeywordActivity : BaseActivity<ActivityKeywordBinding>(ActivityKeywordBinding::inflate), MyPageView{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.btnKeywordBack.setOnClickListener {
            finish()
        }

        showLoadingDialog(this)
        MyPageService(this).tryGetKeywords()

    }

    override fun onGetMyPageSuccess(response: MyPageResponse) {
        TODO("Not yet implemented")
    }

    override fun onGetMyPageFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onLogoutSuccess(response: BaseResponse) {
        TODO("Not yet implemented")
    }

    override fun onLogoutFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onDeleteUserSuccess(response: BaseResponse) {
        TODO("Not yet implemented")
    }

    override fun onDeleteUserFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onPatchProfileImgSuccess(response: BaseResponse) {
        TODO("Not yet implemented")
    }

    override fun onPatchProfileImgFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onPatchNickNameSuccess(response: BaseResponse) {
        TODO("Not yet implemented")
    }

    override fun onPatchNickNameFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onGetKeywordSuccess(response: KeywordResponse) {
        dismissLoadingDialog()
        val adapter = KeywordAdapter(this, response.result)
        binding.rvKeyword.adapter = adapter
    }

    override fun onGetKeywordFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)
    }

    override fun onPostKeywordSuccess(response: BaseResponse) {
        TODO("Not yet implemented")
    }

    override fun onPostKeywordFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onDeleteKeywordSuccess(response: BaseResponse) {
        TODO("Not yet implemented")
    }

    override fun onDeleteKeywordFailure(message: String) {
        TODO("Not yet implemented")
    }
}
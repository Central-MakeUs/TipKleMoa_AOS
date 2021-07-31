package com.tipklemoa.tipkle.src.mypage

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.tipklemoa.tipkle.R
import com.tipklemoa.tipkle.config.BaseActivity
import com.tipklemoa.tipkle.config.BaseResponse
import com.tipklemoa.tipkle.databinding.ActivityKeywordBinding
import com.tipklemoa.tipkle.src.KeywordAlertDialogFragment
import com.tipklemoa.tipkle.src.mypage.model.KeywordResponse
import com.tipklemoa.tipkle.src.mypage.model.MyPageResponse
import com.google.android.flexbox.JustifyContent

import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap

import com.google.android.flexbox.FlexboxLayoutManager

class KeywordActivity : BaseActivity<ActivityKeywordBinding>(ActivityKeywordBinding::inflate), MyPageView{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.btnKeywordBack.setOnClickListener {
            finish()
        }

        binding.edtKeyword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (binding.edtKeyword.text.isNullOrEmpty()) {
                    binding.tvCompleteKeyword.isEnabled = false
                    binding.tvCompleteKeyword.setTextColor(
                        resources.getColor(
                            R.color.DBGray
                        )
                    )
                } else {
                    binding.tvCompleteKeyword.isEnabled = true
                    binding.tvCompleteKeyword.setTextColor(
                        resources.getColor(
                            R.color.mint
                        )
                    )
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        binding.tvCompleteKeyword.setOnClickListener {
            if (binding.edtKeyword.text.length<2){
                val keywordAlert = KeywordAlertDialogFragment()
                keywordAlert.show(supportFragmentManager, keywordAlert.tag)
            }
            else{ //키워드 등록

            }
        }
    }

    override fun onResume() {
        super.onResume()

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
        binding.tvKeywordNum.text = response.result.size.toString()
        val adapTer = KeywordAdapter(this, response.result)
        val layoutManager = FlexboxLayoutManager(this)
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.flexWrap = FlexWrap.WRAP
        layoutManager.justifyContent = JustifyContent.FLEX_START
        binding.rvKeyword.layoutManager = layoutManager
        binding.rvKeyword.adapter = adapTer

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
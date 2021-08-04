package com.tipklemoa.tipkle.src.mypage

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.tipklemoa.tipkle.R
import com.tipklemoa.tipkle.config.BaseActivity
import com.tipklemoa.tipkle.config.BaseResponse
import com.tipklemoa.tipkle.databinding.ActivityKeywordBinding
import com.tipklemoa.tipkle.src.mypage.model.KeywordResponse
import com.tipklemoa.tipkle.src.mypage.model.MyPageResponse
import com.tipklemoa.tipkle.src.mypage.model.PostKeywordRequest


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
                            com.tipklemoa.tipkle.R.color.DBGray
                        )
                    )
                } else {
                    binding.tvCompleteKeyword.isEnabled = true
                    binding.tvCompleteKeyword.setTextColor(
                        resources.getColor(
                            com.tipklemoa.tipkle.R.color.mint
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
                val bundle = Bundle()
                bundle.putString("what", "length")
                keywordAlert.arguments = bundle
                keywordAlert.show(supportFragmentManager, keywordAlert.tag)
            }
            else{ //키워드 등록
                if (binding.tvKeywordNum.text=="5"){
                    val keywordAlert = KeywordAlertDialogFragment()
                    val bundle = Bundle()
                    bundle.putString("what", "num")
                    keywordAlert.arguments = bundle
                    keywordAlert.show(supportFragmentManager, keywordAlert.tag)
                }
                else{
                    showLoadingDialog(this)
                    val postKeywordRequest = PostKeywordRequest(binding.edtKeyword.text.toString().trim())
                    MyPageService(this).tryPostKeyword(postKeywordRequest)
                }
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
        binding.chipGroup.removeAllViews()
        binding.tvKeywordNum.text = response.result.size.toString()

        for (i in response.result){
            val chip = Chip(this)
            chip.text = i.keyword
            chip.setTextColor(resources.getColor(R.color.black))
            chip.closeIcon = resources.getDrawable(R.drawable.ic_x)
            chip.isCloseIconVisible = true
            //chip.
            chip.setTextAppearance(R.style.ChipTextAppearance)
            //val chipDrawable = ChipDrawable.createFromResource(this, R.drawable.keyword_mint)
            //chip.setChipDrawable(chipDrawable)

            binding.chipGroup.addView(chip)
/*            val keywordLayout = layoutInflater.inflate(
                R.layout.layout_keyword_item,
                binding.chipGroup,false)*/
//            val keywordText = keywordLayout.findViewById<TextView>(R.id.tvKeyword)
//            val button = keywordLayout.findViewById<ImageView>(R.id.btnKeywordX)
//            keywordText.text = i.keyword
//            button.setOnClickListener {
//                showLoadingDialog(this)
//                MyPageService(this).tryDeleteKeyword(i.keywordId)
//            }
//            binding.flowLayout.addView(keywordLayout)
        }
    }

    override fun onGetKeywordFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)
    }

    override fun onPostKeywordSuccess(response: BaseResponse) {
        dismissLoadingDialog()
        showCustomToast("키워드 등록 완료!")
        showLoadingDialog(this)
        MyPageService(this).tryGetKeywords()
    }

    override fun onPostKeywordFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)
    }

    override fun onDeleteKeywordSuccess(response: BaseResponse) {
        dismissLoadingDialog()
        showCustomToast("키워드 삭제 완료!")
        showLoadingDialog(this)
        MyPageService(this).tryGetKeywords()
    }

    override fun onDeleteKeywordFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)
    }
}
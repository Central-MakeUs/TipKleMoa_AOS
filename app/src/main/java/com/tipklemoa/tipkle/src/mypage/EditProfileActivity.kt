package com.tipklemoa.tipkle.src.mypage

import android.content.res.ColorStateList
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.tipklemoa.tipkle.R
import com.tipklemoa.tipkle.config.BaseActivity
import com.tipklemoa.tipkle.config.BaseResponse
import com.tipklemoa.tipkle.databinding.ActivityEditProfileBinding
import com.tipklemoa.tipkle.src.DeleteOrReportBottomSheet
import com.tipklemoa.tipkle.src.MainService
import com.tipklemoa.tipkle.src.mypage.model.KeywordResponse
import com.tipklemoa.tipkle.src.mypage.model.MyPageResponse
import com.tipklemoa.tipkle.src.mypage.model.PostEditNickNameRequest

class EditProfileActivity : BaseActivity<ActivityEditProfileBinding>(ActivityEditProfileBinding::inflate), MyPageView{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.edtNickNameEdit.setText(intent.getStringExtra("nickName"))

        binding.edtNickNameEdit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.edtNickNameEdit.backgroundTintList = ColorStateList.valueOf(resources.getColor(
                    R.color.mint))
                binding.tvProfileAlert.text = "프로필 사진과 닉네임을 입력해주세요."
                binding.tvProfileAlert.setTextColor(resources.getColor(
                    R.color.DBGray))
            }

            override fun afterTextChanged(s: Editable?) {
                if (binding.edtNickNameEdit.text.isNullOrEmpty()) {
                    binding.tvProfileAlert.text = "닉네임을 입력해주세요!"
                    binding.tvProfileAlert.setTextColor(resources.getColor(
                        R.color.textRed))
                    binding.edtNickNameEdit.backgroundTintList = ColorStateList.valueOf(resources.getColor(
                        R.color.textRed))
                    binding.tvProfileAlert.text = "닉네임을 입력해주세요!"
                    binding.tvCompleteEditProfile.isEnabled = false
                    binding.tvCompleteEditProfile.setTextColor(resources.getColor(
                        R.color.DBGray))
                }
                else{
                    binding.edtNickNameEdit.backgroundTintList = ColorStateList.valueOf(resources.getColor(
                        R.color.DBGray))
                    binding.tvProfileAlert.text = "프로필 사진과 닉네임을 입력해주세요."
                    binding.tvCompleteEditProfile.isEnabled = true
                    binding.tvCompleteEditProfile.setTextColor(resources.getColor(
                        R.color.black))
                }
            }

        })

        binding.btnReportUserBack.setOnClickListener {
            finish()
        }

        binding.imageView29.setOnClickListener {
            val editProfileBottomSheet = EditProfileBottomSheet()
            editProfileBottomSheet.show(supportFragmentManager, editProfileBottomSheet.tag)
        }

        binding.tvCompleteEditProfile.setOnClickListener {
            if (binding.edtNickNameEdit.text.isNotEmpty()){
                showLoadingDialog(this)
                var patchNickNameRequest = PostEditNickNameRequest(binding.edtNickNameEdit.text.toString().trim())
                MyPageService(this).tryPatchNickName(patchNickNameRequest)
            }
        }

        supportFragmentManager
            .setFragmentResultListener("takePic", this) { requestKey, bundle ->
                // We use a String here, but any type that can be put in a Bundle is supported
                val result = bundle.getString("takePic")
                // Do something with the result
                if (result=="ok"){
                    Glide
                        .with(this)
                        .load(bundle.getString("uri"))
                        .circleCrop()
                        .error(R.drawable.ic_img_profile)
                        .into(binding.imgEditProfile)
                }
            }
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
        dismissLoadingDialog()
        showCustomToast("닉네임 수정 완료!")
        finish()
    }

    override fun onPatchNickNameFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)
    }

    override fun onGetKeywordSuccess(response: KeywordResponse) {
        TODO("Not yet implemented")
    }

    override fun onGetKeywordFailure(message: String) {
        TODO("Not yet implemented")
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
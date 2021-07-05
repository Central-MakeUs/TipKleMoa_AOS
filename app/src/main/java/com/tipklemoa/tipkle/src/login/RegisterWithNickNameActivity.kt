package com.tipklemoa.tipkle.src.login

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import com.tipklemoa.tipkle.R
import com.tipklemoa.tipkle.config.ApplicationClass
import com.tipklemoa.tipkle.config.BaseActivity
import com.tipklemoa.tipkle.config.BaseResponse
import com.tipklemoa.tipkle.databinding.ActivityRegisterWithNickNameBinding
import com.tipklemoa.tipkle.src.login.model.KakaoLoginResponse
import com.tipklemoa.tipkle.src.login.model.KakaoRegisterResponse

class RegisterWithNickNameActivity : BaseActivity<ActivityRegisterWithNickNameBinding>(ActivityRegisterWithNickNameBinding::inflate),
    LoginActivityView {

    var is_agree_flag = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("accessToken", intent.getStringExtra("accessToken").toString())

        binding.btnAgreePrivacy.setOnClickListener {
            if (!is_agree_flag){
                //동의 안된경우에 눌렀을때 -> 버튼 색 바뀜
                binding.btnAgreePrivacy.setBackgroundResource(R.drawable.check_box_color)
                is_agree_flag = true

                if (!binding.edtNickName.text.isNullOrEmpty()){
                    binding.imgRightArrow.backgroundTintList = ColorStateList.valueOf(Color.BLACK)
                    binding.tvLoginNext.setTextColor(resources.getColor(R.color.black))
                    binding.loNext.isClickable = true
                }
                else {
                    binding.imgRightArrow.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.DBGray))
                    binding.tvLoginNext.setTextColor(resources.getColor(R.color.DBGray))
                    binding.loNext.isClickable = false
                }
            }
            else{ //동의 된 상태에서 눌렀을 때
                is_agree_flag = false
                binding.btnAgreePrivacy.setBackgroundResource(R.drawable.check_box_line)
            }
        }

        binding.edtNickName.setOnFocusChangeListener { v, hasFocus ->
            if (!binding.edtNickName.text.isNullOrEmpty() && is_agree_flag){
                binding.imgRightArrow.backgroundTintList = ColorStateList.valueOf(Color.BLACK)
                binding.tvLoginNext.setTextColor(resources.getColor(R.color.black))
                binding.loNext.isClickable = true
            }
            else if (binding.edtNickName.text.isNullOrEmpty()){
                binding.imgRightArrow.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.DBGray))
                binding.tvLoginNext.setTextColor(resources.getColor(R.color.DBGray))
                binding.loNext.isClickable = false
            }
        }

        binding.loNext.setOnClickListener {
            val toCatIntent = Intent(this, ChooseCategoryActivity::class.java)
            toCatIntent.putExtra("nickName", binding.edtNickName.text.toString())
            toCatIntent.putExtra("accessToken", intent.getStringExtra("accessToken"))
            startActivity(toCatIntent)
        }
    }

    override fun onPostKakaoLoginSuccess(response: KakaoLoginResponse) {

    }

    override fun onPostKakaoLoginFailure(message: String) {

    }

    override fun onPostKakaoRegisterSuccess(response: KakaoRegisterResponse) {
        TODO("Not yet implemented")
    }

    override fun onPostKakaoRegisterFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onGetAutoLoginSuccess(response: BaseResponse) {
        TODO("Not yet implemented")
    }

    override fun onGetAutoLoginFailure(message: String) {
        TODO("Not yet implemented")
    }
}
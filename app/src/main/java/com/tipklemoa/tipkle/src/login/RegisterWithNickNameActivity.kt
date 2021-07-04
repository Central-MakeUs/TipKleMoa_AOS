package com.tipklemoa.tipkle.src.login

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import com.tipklemoa.tipkle.R
import com.tipklemoa.tipkle.config.BaseActivity
import com.tipklemoa.tipkle.databinding.ActivityRegisterWithNickNameBinding
import com.tipklemoa.tipkle.src.login.model.KakaoLoginResponse

class RegisterWithNickNameActivity : BaseActivity<ActivityRegisterWithNickNameBinding>(ActivityRegisterWithNickNameBinding::inflate),
    LoginActivityView {

    var is_agree_flag = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.btnAgreePrivacy.setOnClickListener {
            if (!is_agree_flag){
                //동의 안된경우에 눌렀을때 -> 버튼 색 바뀜
                binding.btnAgreePrivacy.setBackgroundResource(R.drawable.check_box_color)
                binding.tvLoginNext.setTextColor(getColor(R.color.black))
                binding.imgRightArrow.backgroundTintList = ColorStateList.valueOf(Color.BLACK)

                is_agree_flag = true
            }
            else{ //동의 된 상태에서 눌렀을 때
                is_agree_flag = false

                binding.btnAgreePrivacy.setBackgroundResource(R.drawable.check_box_line)
                binding.tvLoginNext.setTextColor(getColor(R.color.DBGray))
                binding.imgRightArrow.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.DBGray))
            }
        }
    }

    override fun onPostKakaoLoginSuccess(response: KakaoLoginResponse) {
        TODO("Not yet implemented")
    }

    override fun onPostKakaoLoginFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)
    }
}
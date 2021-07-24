package com.tipklemoa.tipkle.src.login

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.core.widget.addTextChangedListener
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

        binding.btnRegisterNickNameBack.setOnClickListener {
            finish()
        }

        binding.tvPrivacy.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://tipkle.shop/%ED%8C%81%EB%81%8C_%EA%B0%9C%EC%9D%B8%EC%A0%95%EB%B3%B4%EC%B2%98%EB%A6%AC%EB%B0%A9%EC%B9%A8.html"))
            startActivity(intent)
        }

        binding.btnAgreePrivacy.setOnClickListener {
            if (!is_agree_flag){
                //동의 안된경우에 눌렀을때 -> 버튼 색 바뀜
                binding.btnAgreePrivacy.setBackgroundResource(R.drawable.check_box_color)
                is_agree_flag = true
            }
            else{ //동의 된 상태에서 눌렀을 때
                is_agree_flag = false
                binding.btnAgreePrivacy.setBackgroundResource(R.drawable.check_box_line)
            }
            activateButton()
        }

        binding.edtNickName.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                activateButton()
            }

            override fun afterTextChanged(s: Editable?) {
                activateButton()
            }

        })

        binding.loNext.setOnClickListener {
            val toCatIntent = Intent(this, ChooseCategoryActivity::class.java)
            toCatIntent.putExtra("nickName", binding.edtNickName.text.toString())
            toCatIntent.putExtra("accessToken", intent.getStringExtra("accessToken"))
            startActivity(toCatIntent)
        }
    }

    private fun activateButton() {
        //버튼 활성화
        if (is_agree_flag && binding.edtNickName.text!!.isNotEmpty()){
            binding.imgRightArrow.backgroundTintList = ColorStateList.valueOf(Color.BLACK)
            binding.tvLoginNext.setTextColor(resources.getColor(R.color.black))
            binding.loNext.isClickable = true
        }
        else{
            binding.imgRightArrow.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.DBGray))
            binding.tvLoginNext.setTextColor(resources.getColor(R.color.DBGray))
            binding.loNext.isClickable = false
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
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
import com.tipklemoa.tipkle.databinding.ActivityChooseCategoryBinding
import com.tipklemoa.tipkle.src.MainActivity
import com.tipklemoa.tipkle.src.login.model.KakaoLoginResponse
import com.tipklemoa.tipkle.src.login.model.KakaoRegisterResponse
import com.tipklemoa.tipkle.src.login.model.PostKakaoRegisterRequest

class ChooseCategoryActivity : BaseActivity<ActivityChooseCategoryBinding>(ActivityChooseCategoryBinding::inflate),
LoginActivityView {

    private val categoryList = Array(6){0}
    private val pickedCategoryList = mutableListOf<Int>()
    private var pickedNum = 0
    private val editor = ApplicationClass.sSharedPreferences.edit()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.btnChooseCategoryBack.setOnClickListener {
            finish()
        }

        binding.loComplete.setOnClickListener {
            val nickName = intent.getStringExtra("nickName")
            val accessToken = intent.getStringExtra("accessToken")

            for (i in categoryList){
                if (i!=0){
                    pickedCategoryList.add(i)
                }
            }

            if (nickName!= null && accessToken!=null){
                showLoadingDialog(this)
                val postKakaoRegisterRequest = PostKakaoRegisterRequest(accessToken, nickName,
                    pickedCategoryList)
                LoginService(this).tryPostKakaoRegister(postKakaoRegisterRequest)
            }

            Log.d("hello", "왜안돼")
        }

        binding.btnClean.setOnClickListener {
             if (categoryList[0]==0) { //선택 x -> 선택
                 categoryList[0] = 1
                 binding.btnClean.setBackgroundResource(R.drawable.ic_button_cleaning_mint)
                 pickedNum++
             }
             else{  //선택 o -> 선택  xloComplete
                 categoryList[0] = 0
                 binding.btnClean.setBackgroundResource(R.drawable.ic_button_cleaning_gray)
                 pickedNum--
             }
            binding.tvPickedCategoryNum.text = pickedNum.toString()
            checkFour()
        }

        binding.btnCook.setOnClickListener {
            if (categoryList[1]==0){ //선택 x -> 선택
                categoryList[1] = 2
                binding.btnCook.setBackgroundResource(R.drawable.ic_button_cooking_mint)
                pickedNum++
            }
            else {
                categoryList[1] = 0
                binding.btnCook.setBackgroundResource(R.drawable.ic_button_cooking_gray)
                pickedNum--
            }
            binding.tvPickedCategoryNum.text = pickedNum.toString()
            checkFour()

        }

        binding.btnBeauty.setOnClickListener {
            if (categoryList[2]==0) {
                categoryList[2] = 3
                pickedNum++
                binding.btnBeauty.setBackgroundResource(R.drawable.ic_button_beauty_mint)
            }
            else {
                categoryList[2] = 0
                binding.btnBeauty.setBackgroundResource(R.drawable.ic_button_beauty_gray)
                pickedNum--
            }
            binding.tvPickedCategoryNum.text = pickedNum.toString()
            checkFour()

        }

        binding.btnJachi.setOnClickListener {
            if (categoryList[3]==0) {
                categoryList[3] = 4
                binding.btnJachi.setBackgroundResource(R.drawable.ic_button_jachi_mint)
                pickedNum++
            }
            else {
                categoryList[3] = 0
                binding.btnJachi.setBackgroundResource(R.drawable.ic_button_jachi_gray)
                pickedNum--
            }
            binding.tvPickedCategoryNum.text = pickedNum.toString()
            checkFour()

        }

        binding.btnTrip.setOnClickListener {
            if (categoryList[4]==0) {
                categoryList[4] = 5
                binding.btnTrip.setBackgroundResource(R.drawable.ic_button_trip_mint)
                pickedNum++
            }
            else {
                categoryList[4] = 0
                binding.btnTrip.setBackgroundResource(R.drawable.ic_button_trip_gray)
                pickedNum--
            }
            binding.tvPickedCategoryNum.text = pickedNum.toString()
            checkFour()

        }

        binding.btnUniv.setOnClickListener {
            if (categoryList[5]==0) {
                categoryList[5] = 6
                binding.btnUniv.setBackgroundResource(R.drawable.ic_button_uni_mint)
                pickedNum++
            }
            else {
                categoryList[5] = 0
                binding.btnUniv.setBackgroundResource(R.drawable.ic_button_uni_gray)
                pickedNum--
            }
            binding.tvPickedCategoryNum.text = pickedNum.toString()
            checkFour()
        }
    }

    fun checkFour() {
        if (pickedNum>=4){
            binding.tvComplete.setTextColor(resources.getColor(R.color.black))
            binding.imgComplete.backgroundTintList = ColorStateList.valueOf(Color.BLACK)
            binding.loComplete.isClickable = true
        }
        else{
            binding.tvComplete.setTextColor(resources.getColor(R.color.DBGray))
            binding.imgComplete.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.DBGray))
            binding.loComplete.isClickable = false
        }
    }

    override fun onPostKakaoLoginSuccess(response: KakaoLoginResponse) {
        TODO("Not yet implemented")
    }

    override fun onPostKakaoLoginFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onPostKakaoRegisterSuccess(response: KakaoRegisterResponse) {
        dismissLoadingDialog()
        editor.putString(ApplicationClass.X_ACCESS_TOKEN, response.result.jwt)
        editor.apply()

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onPostKakaoRegisterFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)
    }

    override fun onGetAutoLoginSuccess(response: BaseResponse) {
        TODO("Not yet implemented")
    }

    override fun onGetAutoLoginFailure(message: String) {
        TODO("Not yet implemented")
    }
}
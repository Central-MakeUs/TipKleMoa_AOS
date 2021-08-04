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

        binding.linearLayout5.isEnabled = true
        binding.linearLayout5.isClickable = true

        if (!isNetworkConnected()){
            Log.d("네트워크", "연결끊김")
            showCustomToast("네트워크 연결을 확인해주세요!")
        }

        binding.loComplete.setOnClickListener {
            if (!isNetworkConnected()){
                Log.d("네트워크", "연결끊김")
                showCustomToast("네트워크 연결을 확인해주세요!")
            }
            else{
                val nickName = intent.getStringExtra("nickName")
                val accessToken = intent.getStringExtra("accessToken")
                val fcmToken = intent.getStringExtra("fcmToken")

                for (i in categoryList){
                    if (i!=0){
                        pickedCategoryList.add(i)
                    }
                }

                if (nickName!= null && accessToken!=null && fcmToken!=null){
                    showLoadingDialog(this)
                    val postKakaoRegisterRequest = PostKakaoRegisterRequest(accessToken, nickName,
                        pickedCategoryList, fcmToken)
                    LoginService(this).tryPostKakaoRegister(postKakaoRegisterRequest)
                }
            }
        }

        binding.layoutClean.setOnClickListener {
             if (categoryList[0]==0) { //선택 x -> 선택
                 categoryList[0] = 1
                 binding.btnClean.setBackgroundResource(R.drawable.register_button_check)
                 binding.tvClean.setTextColor(resources.getColor(R.color.white))
                 pickedNum++
             }
             else{  //선택 o -> 선택  xloComplete
                 categoryList[0] = 0
                 binding.btnClean.setBackgroundResource(R.drawable.register_button_noncheck)
                 binding.tvClean.setTextColor(resources.getColor(R.color.gray97))
                 pickedNum--
             }
            binding.tvPickedCategoryNum.text = pickedNum.toString()
            checkFour()
        }

        binding.layoutCook.setOnClickListener {
            if (categoryList[1]==0){ //선택 x -> 선택
                categoryList[1] = 2
                binding.btnCook.setBackgroundResource(R.drawable.register_button_check)
                binding.tvCook.setTextColor(resources.getColor(R.color.white))
                pickedNum++
            }
            else {
                categoryList[1] = 0
                binding.btnCook.setBackgroundResource(R.drawable.register_button_noncheck)
                binding.tvCook.setTextColor(resources.getColor(R.color.gray97))
                pickedNum--
            }
            binding.tvPickedCategoryNum.text = pickedNum.toString()
            checkFour()

        }

        binding.layoutBeauty.setOnClickListener {
            if (categoryList[2]==0) {
                categoryList[2] = 3
                pickedNum++
                binding.btnBeauty.setBackgroundResource(R.drawable.register_button_check)
                binding.tvBeauty.setTextColor(resources.getColor(R.color.white))
            }
            else {
                categoryList[2] = 0
                binding.btnBeauty.setBackgroundResource(R.drawable.register_button_noncheck)
                binding.tvBeauty.setTextColor(resources.getColor(R.color.gray97))
                pickedNum--
            }
            binding.tvPickedCategoryNum.text = pickedNum.toString()
            checkFour()

        }

        binding.layoutJachi.setOnClickListener {
            if (categoryList[3]==0) {
                categoryList[3] = 4
                binding.btnJachi.setBackgroundResource(R.drawable.register_button_check)
                binding.tvJachi.setTextColor(resources.getColor(R.color.white))
                pickedNum++
            }
            else {
                categoryList[3] = 0
                binding.btnJachi.setBackgroundResource(R.drawable.register_button_noncheck)
                binding.tvJachi.setTextColor(resources.getColor(R.color.gray97))
                pickedNum--
            }
            binding.tvPickedCategoryNum.text = pickedNum.toString()
            checkFour()

        }

        binding.layoutTrip.setOnClickListener {
            if (categoryList[4]==0) {
                categoryList[4] = 5
                binding.btnTrip.setBackgroundResource(R.drawable.register_button_check)
                binding.tvTrip.setTextColor(resources.getColor(R.color.white))
                pickedNum++
            }
            else {
                categoryList[4] = 0
                binding.btnTrip.setBackgroundResource(R.drawable.register_button_noncheck)
                binding.tvTrip.setTextColor(resources.getColor(R.color.gray97))
                pickedNum--
            }
            binding.tvPickedCategoryNum.text = pickedNum.toString()
            checkFour()

        }

        binding.layoutUniv.setOnClickListener {
            if (categoryList[5]==0) {
                categoryList[5] = 6
                binding.btnUniv.setBackgroundResource(R.drawable.register_button_check)
                binding.tvUniv.setTextColor(resources.getColor(R.color.white))
                pickedNum++
            }
            else {
                categoryList[5] = 0
                binding.btnUniv.setBackgroundResource(R.drawable.register_button_noncheck)
                binding.tvUniv.setTextColor(resources.getColor(R.color.gray97))
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
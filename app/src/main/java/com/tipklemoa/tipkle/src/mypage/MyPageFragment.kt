package com.tipklemoa.tipkle.src.mypage

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.tipklemoa.tipkle.R
import com.tipklemoa.tipkle.config.ApplicationClass
import com.tipklemoa.tipkle.config.BaseFragment
import com.tipklemoa.tipkle.config.BaseResponse
import com.tipklemoa.tipkle.databinding.FragmentHomeBinding
import com.tipklemoa.tipkle.databinding.FragmentMypageBinding
import com.tipklemoa.tipkle.src.ReallyDeleteDialog
import com.tipklemoa.tipkle.src.login.LoginActivity
import com.tipklemoa.tipkle.src.mypage.model.MyPageResponse

class MyPageFragment : BaseFragment<FragmentMypageBinding>(
    FragmentMypageBinding::bind,
    R.layout.fragment_mypage
), MyPageView{

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.layoutLogout.setOnClickListener {
            val dialog = ReallyLogoutDialog()
            dialog.show(parentFragmentManager, dialog.tag)
        }

        binding.layoutWithdraw.setOnClickListener {
            val dialog = ReallyWithdrawDialog()
            dialog.show(parentFragmentManager, dialog.tag)
        }
    }

    override fun onResume() {
        super.onResume()

        showLoadingDialog(requireContext())
        MyPageService(this).tryGetMyPage()
    }

    override fun onGetMyPageSuccess(response: MyPageResponse) {
        dismissLoadingDialog()
        binding.tvLevelName.text = "Lv. "+response.result.levelName

        var requestOptions = RequestOptions()
        requestOptions = requestOptions.transform(CenterCrop(), RoundedCorners(8))

        Glide
            .with(this)
            .load(response.result.profileImgUrl)
            .apply(requestOptions)
            .into(binding.imgMyPageProfile) //멤버 프로필

        binding.tvMyPageNickName.text = response.result.nickName //닉네임

        binding.tvMyPageAchieve.text = response.result.achievement

        var levelImage = 0

        if (response.result.level==1){
            if (response.result.levelbar==0){
                levelImage = R.drawable.ic_lv1_0_
            }
            else if (response.result.levelbar==1){
                levelImage = R.drawable.ic_lv1_20_
            }
            else if (response.result.levelbar==2){
                levelImage = R.drawable.ic_lv1_40_
            }
            else if (response.result.levelbar==3){
                levelImage = R.drawable.ic_lv1_60_
            }
            else if (response.result.levelbar==4){
                levelImage = R.drawable.ic_lv1_80_
            }
        }
        else if (response.result.level==2){
            if (response.result.levelbar==0){
                levelImage = R.drawable.ic_lv2_0_
            }
            else if (response.result.levelbar==1){
                levelImage = R.drawable.ic_lv2_20_
            }
            else if (response.result.levelbar==2){
                levelImage = R.drawable.ic_lv2_40_
            }
            else if (response.result.levelbar==3){
                levelImage = R.drawable.ic_lv2_60_
            }
            else if (response.result.levelbar==4){
                levelImage = R.drawable.ic_lv2_80_
            }
        }
        else if (response.result.level==3){
            if (response.result.levelbar==0){
                levelImage = R.drawable.ic_lv3_0_
            }
            else if (response.result.levelbar==1){
                levelImage = R.drawable.ic_lv3_20_
            }
            else if (response.result.levelbar==2){
                levelImage = R.drawable.ic_lv3_40_
            }
            else if (response.result.levelbar==3){
                levelImage = R.drawable.ic_lv3_60_
            }
            else if (response.result.levelbar==4){
                levelImage = R.drawable.ic_lv3_80_
            }
            else if (response.result.levelbar==5){
                levelImage = R.drawable.ic_lv3_100_
            }
        }
        Glide
            .with(this)
            .load(levelImage)
            .into(binding.imgMyPageGauge) //게이지
    }

    override fun onGetMyPageFailure(message: String) {

    }

    override fun onLogoutSuccess(response: BaseResponse) {

    }

    override fun onLogoutFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)
    }

    override fun onDeleteUserSuccess(response: BaseResponse) {
        dismissLoadingDialog()
        val editor = ApplicationClass.sSharedPreferences.edit()
        editor.remove(ApplicationClass.X_ACCESS_TOKEN)
        editor.apply()

        val intent = Intent(activity, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        activity?.finish()
    }

    override fun onDeleteUserFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)
    }

}
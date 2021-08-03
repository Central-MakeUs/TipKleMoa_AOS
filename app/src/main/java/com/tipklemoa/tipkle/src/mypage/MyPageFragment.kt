package com.tipklemoa.tipkle.src.mypage

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.tipklemoa.tipkle.R
import com.tipklemoa.tipkle.config.ApplicationClass
import com.tipklemoa.tipkle.config.BaseFragment
import com.tipklemoa.tipkle.config.BaseResponse
import com.tipklemoa.tipkle.databinding.FragmentMypageBinding
import com.tipklemoa.tipkle.src.login.LoginActivity
import com.tipklemoa.tipkle.src.mypage.model.KeywordResponse
import com.tipklemoa.tipkle.src.mypage.model.MyPageResponse

class MyPageFragment : BaseFragment<FragmentMypageBinding>(
    FragmentMypageBinding::bind,
    R.layout.fragment_mypage
), MyPageView{

    var profileImg:String?=null

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

        binding.layoutNickname.setOnClickListener {
            val intent = Intent(requireContext(), EditProfileActivity::class.java)
            intent.putExtra("nickName", binding.tvMyPageNickName.text.toString())
            intent.putExtra("profileImg", profileImg)

            startActivity(intent)
        }

        binding.layoutKeyword.setOnClickListener {
            val intent = Intent(requireContext(), KeywordActivity::class.java)
            //intent.putExtra("nickName", binding.tvMyPageNickName.text.toString())
            startActivity(intent)
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

        profileImg = response.result.profileImgUrl

        Glide
            .with(this)
            .load(response.result.profileImgUrl)
            .circleCrop()
            .error(R.drawable.ic_img_profile)
            .into(binding.imgMyPageProfile) //멤버 프로필

        binding.tvMyPageNickName.text = response.result.nickName //닉네임

        val word = response.result.achievement.split("/")
        binding.tvMyPageAchieveFront.text = word[0]
        binding.tvMyPageAchieveAfter.text = "/"+word[1]

        var levelImage = 0

        when (response.result.level) {
            1 -> {
                when (response.result.levelbar) {
                    0 -> {
                        levelImage = R.drawable.ic_lv1_0_
                    }
                    1 -> {
                        levelImage = R.drawable.ic_lv1_20_
                    }
                    2 -> {
                        levelImage = R.drawable.ic_lv1_40_
                    }
                    3 -> {
                        levelImage = R.drawable.ic_lv1_60_
                    }
                    4 -> {
                        levelImage = R.drawable.ic_lv1_80_
                    }
                }
            }
            2 -> {
                when (response.result.levelbar) {
                    0 -> {
                        levelImage = R.drawable.ic_lv2_0_
                    }
                    1 -> {
                        levelImage = R.drawable.ic_lv2_20_
                    }
                    2 -> {
                        levelImage = R.drawable.ic_lv2_40_
                    }
                    3 -> {
                        levelImage = R.drawable.ic_lv2_60_
                    }
                    4 -> {
                        levelImage = R.drawable.ic_lv2_80_
                    }
                }
            }
            3 -> {
                when (response.result.levelbar) {
                    0 -> {
                        levelImage = R.drawable.ic_lv3_0_
                    }
                    1 -> {
                        levelImage = R.drawable.ic_lv3_20_
                    }
                    2 -> {
                        levelImage = R.drawable.ic_lv3_40_
                    }
                    3 -> {
                        levelImage = R.drawable.ic_lv3_60_
                    }
                    4 -> {
                        levelImage = R.drawable.ic_lv3_80_
                    }
                    5 -> {
                        levelImage = R.drawable.ic_lv3_100_
                    }
                }
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
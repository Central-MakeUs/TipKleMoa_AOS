package com.tipklemoa.tipkle.src.mypage

import android.content.res.ColorStateList
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.tipklemoa.tipkle.R
import com.tipklemoa.tipkle.config.BaseActivity
import com.tipklemoa.tipkle.config.BaseResponse
import com.tipklemoa.tipkle.databinding.ActivityEditProfileBinding
import com.tipklemoa.tipkle.src.DeleteOrReportBottomSheet
import com.tipklemoa.tipkle.src.MainService
import com.tipklemoa.tipkle.src.model.PostNewTipRequest
import com.tipklemoa.tipkle.src.mypage.model.KeywordResponse
import com.tipklemoa.tipkle.src.mypage.model.MyPageResponse
import com.tipklemoa.tipkle.src.mypage.model.PostEditNickNameRequest
import com.tipklemoa.tipkle.src.mypage.model.PostEditProfileRequest
import java.text.SimpleDateFormat
import java.util.*

class EditProfileActivity : BaseActivity<ActivityEditProfileBinding>(ActivityEditProfileBinding::inflate), MyPageView{
    var storage: FirebaseStorage? = null //파이어베이스하
    var imageUri:String?=null
    var originalImageUrl:String?=null
    var originalNick:String?=null
    var editedNick = false
    var editedProfile = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.edtNickNameEdit.setText(intent.getStringExtra("nickName"))
        originalNick = intent.getStringExtra("nickName")
        originalImageUrl = intent.getStringExtra("profileImg")
        imageUri = intent.getStringExtra("profileImg")
        storage = FirebaseStorage.getInstance()

        Glide
            .with(this)
            .load(intent.getStringExtra("profileImg"))
            .circleCrop()
            .into(binding.imgEditProfile) //멤버 프로필

        if (binding.edtNickNameEdit.text.toString().trim().isNotEmpty()){
            binding.tvCompleteEditProfile.isEnabled = true
            binding.tvCompleteEditProfile.setTextColor(resources.getColor(
                R.color.black))
        }
        else{
            binding.tvCompleteEditProfile.isEnabled = false
            binding.tvCompleteEditProfile.setTextColor(resources.getColor(
                R.color.DBGray))
        }

        binding.edtNickNameEdit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (binding.edtNickNameEdit.text.toString().trim().isEmpty()) {
                    binding.tvProfileAlert.text = "닉네임을 입력해주세요!"
                    binding.tvProfileAlert.setTextColor(resources.getColor(
                        R.color.textRed))
                    binding.edtNickNameEdit.backgroundTintList = ColorStateList.valueOf(resources.getColor(
                        R.color.textRed))
                    binding.tvCompleteEditProfile.isEnabled = false
                    binding.tvCompleteEditProfile.setTextColor(resources.getColor(
                        R.color.DBGray))
                }
                else{
                    binding.edtNickNameEdit.backgroundTintList = ColorStateList.valueOf(resources.getColor(
                        R.color.mint))
                    binding.tvProfileAlert.text = "프로필 사진과 닉네임을 입력해주세요."
                    binding.tvProfileAlert.setTextColor(resources.getColor(
                        R.color.DBGray))
                    binding.tvCompleteEditProfile.isEnabled = true
                    binding.tvCompleteEditProfile.setTextColor(resources.getColor(
                        R.color.black))
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

        binding.btnReportUserBack.setOnClickListener {
            finish()
        }

        binding.imageView29.setOnClickListener {
            val editProfileBottomSheet = EditProfileBottomSheet()
            editProfileBottomSheet.show(supportFragmentManager, editProfileBottomSheet.tag)
        }

        supportFragmentManager
            .setFragmentResultListener("takePic", this) { requestKey, bundle ->
                // We use a String here, but any type that can be put in a Bundle is supported
                val result = bundle.getString("takePic")
                imageUri = bundle.getString("uri")
                // Do something with the result
                if (result=="ok"){
                    Glide
                        .with(this)
                        .load(imageUri)
                        .circleCrop()
                        .error(R.drawable.ic_img_profile)
                        .into(binding.imgEditProfile)
                }
            }

        binding.tvCompleteEditProfile.setOnClickListener {
            if (!isNetworkConnected()) {
                showCustomToast("네트워크 연결을 확인해주세요!")
            } else {
                if (binding.edtNickNameEdit.text.toString()
                        .trim() == originalNick && intent.getStringExtra("profileImg") != imageUri
                ) { //프사만 바꿨을 때
                    Log.d("확인", "프사바꿈")
                    if (imageUri != null) { //프사 있음
                        Log.d("확인", "프사바꿈")
                        imageUploadFirebase()
                    } else { //프사없음
                        editedProfile = true
                        showLoadingDialog(this)
                        val profileRequest = PostEditProfileRequest()
                        MyPageService(this).tryPatchProfile(profileRequest)
                    }
                }
                else if (binding.edtNickNameEdit.text.toString()
                        .trim() != originalNick && intent.getStringExtra("profileImg") == imageUri
                ) { //닉네임만 바꿨을때

                    val patchNickNameRequest =
                        PostEditNickNameRequest(binding.edtNickNameEdit.text.toString().trim())
                    MyPageService(this).tryPatchNickName(patchNickNameRequest)
                }
                else if (binding.edtNickNameEdit.text.toString()
                        .trim() != originalNick && intent.getStringExtra("profileImg") != imageUri
                ) { //둘다 바꿈
                    if (imageUri != null) { //프사 있음
                        Log.d("확인", "프사바꿈")
                        imageUploadFirebase()
                    } else { //프사없음
                        editedProfile = true
                        showLoadingDialog(this)
                        val profileRequest = PostEditProfileRequest()
                        MyPageService(this).tryPatchProfile(profileRequest)
                    }
                    val patchNickNameRequest =
                        PostEditNickNameRequest(binding.edtNickNameEdit.text.toString().trim())
                    MyPageService(this).tryPatchNickName(patchNickNameRequest)
                }
                else if (binding.edtNickNameEdit.text.toString()
                        .trim() == originalNick && intent.getStringExtra("profileImg") == imageUri
                ) { //둘다 변경 x -> 그냥 종료
                    finish()
                }
            }
        }

//        binding.tvCompleteEditProfile.setOnClickListener {
//            if (uri!=null) imageUploadFirebase()
//            else {
//                showLoadingDialog(this)
//                val postProfileRequest = PostEditProfileRequest(null)
//                MyPageService(this).tryPatchProfile(postProfileRequest)
//            }
//        }
    }

    private fun imageUploadFirebase(){
        showLoadingDialog(this)
        editedProfile = true

        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFile = "Image_"+timestamp+"_.jpg"
        val storageRef = storage?.reference?.child("media")?.child(imageFile)

        storageRef?.putFile(Uri.parse(imageUri))?.addOnSuccessListener {
                Log.d("photo", "파이어베이스 업로드완료")
                storageRef.downloadUrl.addOnSuccessListener { uri->
                    imageUri = uri.toString()
                    val postProfileRequest = PostEditProfileRequest(imageUri)
                    MyPageService(this).tryPatchProfile(postProfileRequest)
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
        dismissLoadingDialog()
        showCustomToast("프로필 사진 수정 완료")
        if (editedProfile) finish()
    }

    override fun onPatchProfileImgFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)
    }

    override fun onPatchNickNameSuccess(response: BaseResponse) {
        showCustomToast("닉네임 수정 완료")
        editedNick = true
        if (!editedProfile && editedNick) finish() //닉네임만 수정했을때 바로 종료
    }

    override fun onPatchNickNameFailure(message: String) {
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
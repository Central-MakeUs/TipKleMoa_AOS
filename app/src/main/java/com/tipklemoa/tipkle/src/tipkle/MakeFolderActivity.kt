package com.tipklemoa.tipkle.src.tipkle

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import com.tipklemoa.tipkle.R
import com.tipklemoa.tipkle.config.BaseActivity
import com.tipklemoa.tipkle.databinding.ActivityFeedDetailBinding
import com.tipklemoa.tipkle.databinding.ActivityMakeFolderBinding
import com.tipklemoa.tipkle.src.tipkle.model.MakeFolderResponse
import com.tipklemoa.tipkle.src.tipkle.model.PostNewFolderRequest
import com.tipklemoa.tipkle.src.tipkle.model.TipFolderResponse

class MakeFolderActivity : BaseActivity<ActivityMakeFolderBinding>(ActivityMakeFolderBinding::inflate),
    TipkleFragmentView{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.btnNewFolderX.setOnClickListener {
            finish()
        }

        binding.btnCompleteNewFolder.setOnClickListener {
            if (binding.edtNewFolder.text.isNullOrEmpty()){
                binding.edtNewFolder.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.textRed))
                binding.tvNewTipAlert.visibility = View.VISIBLE
                binding.tvNewTipAlert.text = "폴더명을 입력해주세요."
            }else{
                binding.edtNewFolder.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.mint))
                binding.tvNewTipAlert.visibility = View.INVISIBLE
                var postNewFolderRequest = PostNewFolderRequest(binding.edtNewFolder.text.toString())
                showLoadingDialog(this)
                TipkleService(this).tryMakeFolder(postNewFolderRequest)
            }
        }

        binding.edtNewFolder.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus){
                binding.edtNewFolder.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.mint))
                binding.tvNewFolderTextNum.text = binding.edtNewFolder.text.length.toString()
                if (binding.edtNewFolder.text.length>=20){
                    binding.edtNewFolder.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.textRed))
                    binding.tvNewTipAlert.visibility = View.VISIBLE
                    binding.tvNewTipAlert.text = "20자 이하로 입력해주세요."
                }
                else{
                    binding.edtNewFolder.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.mint))
                    binding.tvNewTipAlert.visibility = View.INVISIBLE
                }
            }
        }

    }

    override fun onGetTipFolderListSuccess(response: TipFolderResponse) {
        TODO("Not yet implemented")
    }

    override fun onGetTipFolderListFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onPostFolderSuccess(response: MakeFolderResponse) {
        dismissLoadingDialog()
        showCustomToast("폴더 등록 완료!")
        finish()
    }

    override fun onPostFolderFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)
    }

}
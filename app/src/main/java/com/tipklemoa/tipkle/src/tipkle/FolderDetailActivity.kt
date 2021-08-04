package com.tipklemoa.tipkle.src.tipkle

import android.net.ConnectivityManager
import android.net.Network
import android.os.Bundle
import android.util.Log
import android.view.View
import com.tipklemoa.tipkle.config.BaseActivity
import com.tipklemoa.tipkle.config.BaseResponse
import com.tipklemoa.tipkle.databinding.ActivityFolderDetailBinding
import com.tipklemoa.tipkle.src.DeleteOrReportBottomSheet
import com.tipklemoa.tipkle.src.tipkle.model.FolderFeedResponse
import com.tipklemoa.tipkle.src.tipkle.model.MakeFolderResponse
import com.tipklemoa.tipkle.src.tipkle.model.TipFolderResponse

class FolderDetailActivity : BaseActivity<ActivityFolderDetailBinding>(ActivityFolderDetailBinding::inflate), TipkleFragmentView{
    lateinit var networkCallback : ConnectivityManager.NetworkCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val folderId = intent.getIntExtra("folderId", 0)
        if (!isNetworkConnected()){
            showCustomToast("네트워크 연결을 확인해주세요!")
        }

        networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                if (folderId!=0) {
                    showLoadingDialog(this@FolderDetailActivity)
                    TipkleService(this@FolderDetailActivity).tryGetFolderFeed(folderId)
                }
            }

            override fun onLost(network: Network) {
                // 네트워크가 끊길 때 호출됩니다.

            }
        }

        binding.btnFolderDetailBack.setOnClickListener {
            finish()
        }

        binding.btnFolderDelete.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("folderId", folderId)
            val editOrDeleteBottomSheet = DeleteOrReportBottomSheet()
            editOrDeleteBottomSheet.arguments = bundle
            editOrDeleteBottomSheet.show(supportFragmentManager, editOrDeleteBottomSheet.tag)
        }

        supportFragmentManager
            .setFragmentResultListener("delete", this) { requestKey, bundle ->
                // We use a String here, but any type that can be put in a Bundle is supported
                val result = bundle.getString("delete_ok")
                Log.d("result", result.toString())
                // Do something with the result
                if (result=="ok"){
                    this.finish()
                }
            }
    }

    override fun onStop() {
        super.onStop()

        terminateNetworkCallback(networkCallback)
    }

    override fun onResume() {
        super.onResume()

        registerNetworkCallback(networkCallback)
    }

    override fun onGetTipFolderListSuccess(response: TipFolderResponse) {
        TODO("Not yet implemented")
    }

    override fun onGetTipFolderListFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onPostFolderSuccess(response: MakeFolderResponse) {
        TODO("Not yet implemented")
    }

    override fun onPostFolderFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onGetFolderFeedSuccess(response: FolderFeedResponse) {
        dismissLoadingDialog()
        binding.tvFolderDetailName.text =response.result.folderName
        if (response.result.post.isNullOrEmpty()){
            binding.layoutFolderdetailEmpty.visibility = View.VISIBLE
        }
        else{
            binding.layoutFolderdetailEmpty.visibility = View.INVISIBLE
            val adapter = TipkleFolderFeedAdapter(this, response.result.post)
            binding.rvFolderDetail.adapter = adapter
        }
    }

    override fun onGetFolderFeedFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)
    }

    override fun onDeleteFolderSuccess(response: BaseResponse) {
        TODO("Not yet implemented")
    }

    override fun onDeleteFolderFailure(message: String) {
        TODO("Not yet implemented")
    }
}
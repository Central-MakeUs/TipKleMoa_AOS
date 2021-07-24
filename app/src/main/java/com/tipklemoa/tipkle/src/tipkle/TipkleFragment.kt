package com.tipklemoa.tipkle.src.tipkle

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.tipklemoa.tipkle.R
import com.tipklemoa.tipkle.config.BaseFragment
import com.tipklemoa.tipkle.config.BaseResponse
import com.tipklemoa.tipkle.databinding.FragmentHomeBinding
import com.tipklemoa.tipkle.databinding.FragmentTipkleBinding
import com.tipklemoa.tipkle.src.tipkle.model.FolderFeedResponse
import com.tipklemoa.tipkle.src.tipkle.model.MakeFolderResponse
import com.tipklemoa.tipkle.src.tipkle.model.TipFolderResponse

class TipkleFragment : BaseFragment<FragmentTipkleBinding>(
    FragmentTipkleBinding::bind,
    R.layout.fragment_tipkle
), TipkleFragmentView {

    var folderAdapter:TipkleFolderAdapter?=null
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.btnNewTipFolder.setOnClickListener {
            val intent = Intent(requireContext(), MakeFolderActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()

        showLoadingDialog(requireContext())
        TipkleService(this).tryGetFolderList()
    }

    override fun onGetTipFolderListSuccess(response: TipFolderResponse) {
        dismissLoadingDialog()
        if (response.result.isNullOrEmpty()){
            binding.rvTipFolder.visibility = View.INVISIBLE
            binding.layoutEmptyfolder.visibility = View.VISIBLE
        }
        else{
            binding.rvTipFolder.visibility = View.VISIBLE
            binding.layoutEmptyfolder.visibility = View.INVISIBLE
            folderAdapter = TipkleFolderAdapter(requireContext(), response.result)
            binding.rvTipFolder.adapter = folderAdapter
        }
    }

    override fun onGetTipFolderListFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)
    }

    override fun onPostFolderSuccess(response: MakeFolderResponse) {
        TODO("Not yet implemented")
    }

    override fun onPostFolderFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onGetFolderFeedSuccess(response: FolderFeedResponse) {
        TODO("Not yet implemented")
    }

    override fun onGetFolderFeedFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onDeleteFolderSuccess(response: BaseResponse) {
        TODO("Not yet implemented")
    }

    override fun onDeleteFolderFailure(message: String) {
        TODO("Not yet implemented")
    }
}
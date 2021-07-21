package com.tipklemoa.tipkle.src.tipkle

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.tipklemoa.tipkle.R
import com.tipklemoa.tipkle.config.BaseFragment
import com.tipklemoa.tipkle.databinding.FragmentHomeBinding
import com.tipklemoa.tipkle.databinding.FragmentTipkleBinding
import com.tipklemoa.tipkle.src.tipkle.model.MakeFolderResponse
import com.tipklemoa.tipkle.src.tipkle.model.TipFolderResponse

class TipkleFragment : BaseFragment<FragmentTipkleBinding>(
    FragmentTipkleBinding::bind,
    R.layout.fragment_tipkle
), TipkleFragmentView {
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        showLoadingDialog(requireContext())
        TipkleService(this).tryGetFolderList()

        binding.btnNewTipFolder.setOnClickListener {
            val intent = Intent(requireContext(), MakeFolderActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onGetTipFolderListSuccess(response: TipFolderResponse) {
        dismissLoadingDialog()
        if (response.result.isNullOrEmpty()){
            binding.layoutEmptyfolder.visibility = View.VISIBLE
        }
        else{
            binding.layoutEmptyfolder.visibility = View.INVISIBLE
            val folderAdapter = TipkleFolderAdapter(requireContext(), response.result)
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
}
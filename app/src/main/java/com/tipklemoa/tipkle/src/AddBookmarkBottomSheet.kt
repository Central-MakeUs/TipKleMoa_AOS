package com.tipklemoa.tipkle.src

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tipklemoa.tipkle.config.BaseResponse
import com.tipklemoa.tipkle.databinding.LayoutDetailBottomsheetBinding
import com.tipklemoa.tipkle.src.tipkle.TipkleFragmentView
import com.tipklemoa.tipkle.src.tipkle.TipkleService
import com.tipklemoa.tipkle.src.tipkle.model.FolderFeedResponse
import com.tipklemoa.tipkle.src.tipkle.model.MakeFolderResponse
import com.tipklemoa.tipkle.src.tipkle.model.TipFolderResponse
import com.tipklemoa.tipkle.util.LoadingDialog

class AddBookmarkBottomSheet: BottomSheetDialogFragment(), TipkleFragmentView{
    private lateinit var binding: LayoutDetailBottomsheetBinding
    var postId = 0
    var folderId = 0
    lateinit var mLoadingDialog: LoadingDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        postId = arguments?.getInt("postId")!!
        folderId = arguments?.getInt("folderId")!!

        Log.d("postId", postId.toString())

        binding = LayoutDetailBottomsheetBinding.inflate(inflater, container, false)
        binding.constraintLayout2.setOnClickListener {
            if (postId!=0){
                val dialog = ReallyDeleteDialog()
                val bundle = Bundle()
                bundle.putInt("postId", postId)
                dialog.arguments = bundle
                dialog.show(parentFragmentManager, dialog.tag)
                this.dismiss()
            }
            else if (folderId!=0){
                showLoadingDialog(requireContext())
                TipkleService(this).tryDeleteFolder(folderId)
            }
        }

        return binding.root
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
        TODO("Not yet implemented")
    }

    override fun onGetFolderFeedFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onDeleteFolderSuccess(response: BaseResponse) {
        dismissLoadingDialog()
        Toast.makeText(requireContext(), "폴더 삭제 완료", Toast.LENGTH_SHORT).show()
        val bundle = bundleOf("delete_ok" to "ok")
        setFragmentResult("delete", bundle)
        this.dismiss()
    }

    override fun onDeleteFolderFailure(message: String) {
        dismissLoadingDialog()
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    fun showLoadingDialog(context: Context) {
        mLoadingDialog = LoadingDialog(context)
        mLoadingDialog.show()
    }

    fun dismissLoadingDialog() {
        if (mLoadingDialog.isShowing) {
            mLoadingDialog.dismiss()
        }
    }
}
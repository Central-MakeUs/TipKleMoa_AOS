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
import com.tipklemoa.tipkle.R
import com.tipklemoa.tipkle.config.BaseResponse
import com.tipklemoa.tipkle.databinding.LayoutDetailBottomsheetBinding
import com.tipklemoa.tipkle.src.model.CommentResponse
import com.tipklemoa.tipkle.src.model.DetailFeedResponse
import com.tipklemoa.tipkle.src.model.NewTipResponse
import com.tipklemoa.tipkle.src.tipkle.TipkleFragmentView
import com.tipklemoa.tipkle.src.tipkle.TipkleService
import com.tipklemoa.tipkle.src.tipkle.model.FolderFeedResponse
import com.tipklemoa.tipkle.src.tipkle.model.MakeFolderResponse
import com.tipklemoa.tipkle.src.tipkle.model.TipFolderResponse
import com.tipklemoa.tipkle.util.LoadingDialog

class DeleteOrReportBottomSheet: BottomSheetDialogFragment(), TipkleFragmentView, MainView {
    private lateinit var binding: LayoutDetailBottomsheetBinding
    var postId = 0
    var folderId = 0
    var commentId = 0
    var what = ""
    lateinit var mLoadingDialog: LoadingDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        postId = arguments?.getInt("postId")!!
        folderId = arguments?.getInt("folderId")!!
        commentId = arguments?.getInt("commentId")!!
        what = arguments?.getString("what")!!

        Log.d("postId", postId.toString())

        binding = LayoutDetailBottomsheetBinding.inflate(inflater, container, false)

        if (what!=""){
            if (what=="delete"){
                binding.tvDetailBottomSheetText.text = "삭제하기"
                binding.imgDetail.setBackgroundResource(R.drawable.ic_trash)
            }
            else if (what=="report"){
                binding.tvDetailBottomSheetText.text = "신고하기"
                binding.imgDetail.setBackgroundResource(R.drawable.ic_siren)
            }
        }

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
            else if (commentId!=0){
                if (what=="delete"){
                    val dialog = ReallyDeleteDialog()
                    val bundle = Bundle()
                    bundle.putInt("commentId", commentId)
                    bundle.putString("what", what)

                    dialog.arguments = bundle
                    dialog.show(parentFragmentManager, dialog.tag)
                    this.dismiss()
                }
            }
        }

        binding.btnDetaliBottomCancel.setOnClickListener {
            dismiss()
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

    override fun onGetFeedDetailSuccess(response: DetailFeedResponse) {
        TODO("Not yet implemented")
    }

    override fun onGetFeedDetailFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onDeleteFeedSuccess(response: BaseResponse) {
        TODO("Not yet implemented")
    }

    override fun onDeleteFeedFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onPostSuccess(response: NewTipResponse) {
        TODO("Not yet implemented")
    }

    override fun onPostFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onPostBookMarkSuccess(response: BaseResponse) {
        TODO("Not yet implemented")
    }

    override fun onPostBookMarkFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onDeleteBookmarkSuccess(response: BaseResponse) {
        TODO("Not yet implemented")
    }

    override fun onDeleteBookmarkFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onPostStarSuccess(response: BaseResponse) {
        TODO("Not yet implemented")
    }

    override fun onPostStarFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onGetCommentSuccess(response: CommentResponse) {
        TODO("Not yet implemented")
    }

    override fun onGetCommentFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onPostCommentSuccess(response: BaseResponse) {
        TODO("Not yet implemented")
    }

    override fun onPostCommentFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onDeleteCommentSuccess(response: BaseResponse) {
        dismissLoadingDialog()
        Toast.makeText(requireContext(), "댓글 삭제 완료", Toast.LENGTH_SHORT).show()
    }

    override fun onDeleteCommentFailure(message: String) {
        dismissLoadingDialog()
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
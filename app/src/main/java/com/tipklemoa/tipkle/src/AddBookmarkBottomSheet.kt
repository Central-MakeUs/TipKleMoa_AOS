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
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tipklemoa.tipkle.config.ApplicationClass
import com.tipklemoa.tipkle.config.BaseResponse
import com.tipklemoa.tipkle.databinding.LayoutAddbookmarkBottomsheetBinding
import com.tipklemoa.tipkle.databinding.LayoutBookmarkFolderListBinding
import com.tipklemoa.tipkle.databinding.LayoutDetailBottomsheetBinding
import com.tipklemoa.tipkle.src.model.DetailFeedResponse
import com.tipklemoa.tipkle.src.model.NewTipResponse
import com.tipklemoa.tipkle.src.model.PostAddBookMarkRequest
import com.tipklemoa.tipkle.src.tipkle.TipkleFragmentView
import com.tipklemoa.tipkle.src.tipkle.TipkleService
import com.tipklemoa.tipkle.src.tipkle.model.FolderFeedResponse
import com.tipklemoa.tipkle.src.tipkle.model.MakeFolderResponse
import com.tipklemoa.tipkle.src.tipkle.model.TipFolderResponse
import com.tipklemoa.tipkle.util.LoadingDialog

class AddBookmarkBottomSheet: BottomSheetDialogFragment(), MainView, TipkleFragmentView{
    private lateinit var binding: LayoutAddbookmarkBottomsheetBinding
    var postId = 0
    var folderId = 0
    lateinit var mLoadingDialog: LoadingDialog
    var editor = ApplicationClass.sSharedPreferences.edit()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        postId = arguments?.getInt("postId")!!

        binding = LayoutAddbookmarkBottomsheetBinding.inflate(inflater, container, false)
        showLoadingDialog(requireContext())
        TipkleService(this).tryGetFolderList()

        return binding.root
    }

    private val onClicked = object: BookMarkFolderAdapter.OnItemClickListener {
        override fun onClicked(folderId: Int) {
            showLoadingDialog(requireContext())
            val postAddBookMarkRequest = PostAddBookMarkRequest(postId)
            MainService(this@AddBookmarkBottomSheet).tryPostBookMark(folderId, postAddBookMarkRequest)
        }
    }

    override fun onGetTipFolderListSuccess(response: TipFolderResponse) {
        dismissLoadingDialog()
        val layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvAddBookMark.layoutManager = layoutManager
        val bookmarkAdapter = BookMarkFolderAdapter(requireContext(), response.result)
        bookmarkAdapter.setOnItemClickListener(onClicked)
        binding.rvAddBookMark.adapter = bookmarkAdapter
    }

    override fun onGetTipFolderListFailure(message: String) {
        dismissLoadingDialog()
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
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

    }

    override fun onDeleteFolderFailure(message: String) {

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
        dismissLoadingDialog()
        editor.putBoolean("isBookMarked", true)
        editor.apply()

        Toast.makeText(requireContext(), "폴더 저장 완료", Toast.LENGTH_SHORT).show()
        this.dismiss()
    }

    override fun onPostBookMarkFailure(message: String) {
        dismissLoadingDialog()
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDeleteBookmarkSuccess(response: BaseResponse) {
        TODO("Not yet implemented")
    }

    override fun onDeleteBookmarkFailure(message: String) {
        TODO("Not yet implemented")
    }
}
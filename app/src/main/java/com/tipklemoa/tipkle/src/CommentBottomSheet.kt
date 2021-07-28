package com.tipklemoa.tipkle.src

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tipklemoa.tipkle.config.ApplicationClass
import com.tipklemoa.tipkle.config.BaseResponse
import com.tipklemoa.tipkle.databinding.LayoutCommentBottomsheetBinding
import com.tipklemoa.tipkle.src.model.CommentResponse
import com.tipklemoa.tipkle.src.model.DetailFeedResponse
import com.tipklemoa.tipkle.src.model.NewTipResponse
import com.tipklemoa.tipkle.util.LoadingDialog
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.tipklemoa.tipkle.src.model.PostCommentRequest


class CommentBottomSheet: BottomSheetDialogFragment(), MainView{
    private lateinit var binding: LayoutCommentBottomsheetBinding
    var postId = 0
    var folderId = 0
    lateinit var commentAdapter:CommentAdapter
    lateinit var mLoadingDialog: LoadingDialog
    var editor = ApplicationClass.sSharedPreferences.edit()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        postId = arguments?.getInt("postId")!!

        binding = LayoutCommentBottomsheetBinding.inflate(inflater, container, false)
        showLoadingDialog(requireContext())
        MainService(this).tryGetComments(postId)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //var mBehavior = BottomSheetBehavior.from(view.parent as View)
        val layoutParams = view.layoutParams
        val height =
            (resources.displayMetrics.heightPixels * 0.75).toInt() //60% of screen height

        layoutParams.height = height
        view.layoutParams = layoutParams

        binding.btnComment.setOnClickListener {
            if (binding.edtComment.text.isNotEmpty()) {
                val postCommentRequest = PostCommentRequest(binding.edtComment.text.toString())
                Log.d("test" ,binding.edtComment.text.toString())
                MainService(this).tryPostComment(postId, postCommentRequest)
            }
        }
    }

//    private val onClicked = object: BookMarkFolderAdapter.OnItemClickListener {
//        override fun onClicked(folderId: Int) {
//            showLoadingDialog(requireContext())
//            val postAddBookMarkRequest = PostAddBookMarkRequest(postId)
//            MainService(this@CommentBottomSheet).tryPostBookMark(folderId, postAddBookMarkRequest)
//        }
//    }

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

    }

    override fun onPostBookMarkFailure(message: String) {

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
        dismissLoadingDialog()
        commentAdapter = CommentAdapter(requireContext(), response.result)
        binding.tvCommentNum.text = response.result.size.toString()
        binding.rvComment.adapter = commentAdapter
    }

    override fun onGetCommentFailure(message: String) {
        dismissLoadingDialog()
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onPostCommentSuccess(response: BaseResponse) {
        dismissLoadingDialog()
        Toast.makeText(requireContext(), "댓글 등록이 완료되었습니다", Toast.LENGTH_SHORT).show()
        commentAdapter.notifyItemInserted(0)
    }

    override fun onPostCommentFailure(message: String) {
        dismissLoadingDialog()
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDeleteCommentSuccess(response: BaseResponse) {
        TODO("Not yet implemented")
    }

    override fun onDeleteCommentFailure(message: String) {
        TODO("Not yet implemented")
    }
}
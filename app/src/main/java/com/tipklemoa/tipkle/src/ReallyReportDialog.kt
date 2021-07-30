package com.tipklemoa.tipkle.src

import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.tipklemoa.tipkle.config.BaseResponse
import com.tipklemoa.tipkle.databinding.LayoutDetailReallyDeleteDialogBinding
import com.tipklemoa.tipkle.databinding.LayoutDetailReallyReportDialogBinding
import com.tipklemoa.tipkle.src.model.CommentResponse
import com.tipklemoa.tipkle.src.model.DetailFeedResponse
import com.tipklemoa.tipkle.src.model.NewTipResponse
import com.tipklemoa.tipkle.src.model.PostReportFeedRequest
import com.tipklemoa.tipkle.util.LoadingDialog

class ReallyReportDialog : DialogFragment(), MainView {
    var windowManager: WindowManager? = null
    var display: Display? = null
    var size: Point? = null
    var color = 0
    var postId = 0
    var commentId = 0
    var what = ""

    lateinit var mLoadingDialog: LoadingDialog
    private lateinit var binding: LayoutDetailReallyReportDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //false로 설정해 주면 화면밖 혹은 뒤로가기 버튼시 다이얼로그라 dismiss 되지 않는다.
        isCancelable = false
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LayoutDetailReallyReportDialogBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        postId = arguments?.getInt("postId")!!
        commentId = arguments?.getInt("commentId")!!
        what = arguments?.getString("what")!!

        Log.d("확인", postId.toString())

        if (what=="report"){
            if (postId!=0) binding.tvDialogMent.text = "이 게시글은 더이상 보이지 않습니다.\n게시글을 신고하시겠습니까?"
            else if (commentId!=0) binding.tvDialogMent.text = "이 댓글은 더이상 보이지 않습니다.\n댓글을 신고하시겠습니까?"
        }


        binding.btnReportConfirm.setOnClickListener {
            showLoadingDialog(requireContext())
            if (postId!=0){
                val postReportFeedRequest = PostReportFeedRequest("신고")
                MainService(this).tryPostReportFeed(postId, postReportFeedRequest)
            }
            else if (commentId!=0){
                val postReportComment = PostReportFeedRequest("신고")
                MainService(this).tryPostReportComment(commentId, postReportComment)
            }
        }

        binding.btnReportCancel.setOnClickListener {
            this.dismiss()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        windowManager = activity?.getSystemService(Context.WINDOW_SERVICE) as? WindowManager
        display = windowManager!!.defaultDisplay
        size = Point()
        display!!.getSize(size)
    }

    override fun onResume() {
        super.onResume()

        var params: ViewGroup.LayoutParams? = dialog?.window?.attributes
        val deviceWidth = size!!.x
       // val deviceHeight = size!!.y

        params?.width = (deviceWidth*0.82).toInt()
        //params?.height = (deviceHeight*0.17).toInt()

        dialog?.window?.attributes = params as WindowManager.LayoutParams
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
        dismissLoadingDialog()
        val bundle = bundleOf("delete_ok" to "ok")
        setFragmentResult("delete", bundle)
        this.dismiss()
    }

    override fun onDeleteFeedFailure(message: String) {
        dismissLoadingDialog()
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
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
        val bundle = bundleOf("deleteComment_ok" to "ok")
        setFragmentResult("deleteComment", bundle)
        dismiss()
    }

    override fun onDeleteCommentFailure(message: String) {
        dismissLoadingDialog()
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onPostFeedReportSuccess(response: BaseResponse) {
        dismissLoadingDialog()
        val bundle = bundleOf("report_ok" to "ok")
        setFragmentResult("report", bundle)
        dismiss()
    }

    override fun onPostFeedReportFailure(message: String) {
        dismissLoadingDialog()
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onPostCommentReportSuccess(response: BaseResponse) {
        dismissLoadingDialog()
        val bundle = bundleOf("report_ok" to "ok")
        setFragmentResult("report", bundle)
        dismiss()
    }

    override fun onPostCommentReportFailure(message: String) {
        dismissLoadingDialog()
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
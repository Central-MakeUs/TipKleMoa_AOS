package com.tipklemoa.tipkle.src.main

import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.tipklemoa.tipkle.config.BaseResponse
import com.tipklemoa.tipkle.databinding.LayoutStarDialogBinding
import com.tipklemoa.tipkle.src.main.model.CommentResponse
import com.tipklemoa.tipkle.src.main.model.DetailFeedResponse
import com.tipklemoa.tipkle.src.main.model.NewTipResponse
import com.tipklemoa.tipkle.src.main.model.PostStarRequest
import com.tipklemoa.tipkle.util.LoadingDialog

class StarDialog : DialogFragment(), MainView {
    var windowManager: WindowManager? = null
    var display: Display? = null
    var size: Point? = null
    var color = 0
    var postId = 0

    lateinit var mLoadingDialog: LoadingDialog
    private lateinit var binding: LayoutStarDialogBinding

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
        binding = LayoutStarDialogBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        postId = arguments?.getInt("postId")!!

        binding.btnDeleteConfirm.setOnClickListener {
            if (!isNetworkConnected()){
                Toast.makeText(requireContext(), "네트워크 연결을 확인해주세요", Toast.LENGTH_SHORT).show()
            }
            else {
                if (binding.postRatingBar.rating.toInt()==0){
                    Toast.makeText(requireContext(), "별점으로 0점은 줄 수 없습니다", Toast.LENGTH_SHORT).show()
                }
                else{
                    showLoadingDialog(requireContext())
                    Log.d("확인", binding.postRatingBar.rating.toString())
                    val postStarRequest = PostStarRequest(binding.postRatingBar.rating.toInt())
                    MainService(this).tryPostStar(postId, postStarRequest)
                }
            }
        }

        binding.btnDeleteCancel.setOnClickListener {
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

        val params: ViewGroup.LayoutParams? = dialog?.window?.attributes
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
        dismissLoadingDialog()
        val bundle = bundleOf("star_ok" to "ok")
        setFragmentResult("star", bundle)
        this.dismiss()
    }

    override fun onPostStarFailure(message: String) {
        dismissLoadingDialog()
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
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
        TODO("Not yet implemented")
    }

    override fun onDeleteCommentFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onPostFeedReportSuccess(response: BaseResponse) {
        TODO("Not yet implemented")
    }

    override fun onPostFeedReportFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onPostCommentReportSuccess(response: BaseResponse) {
        TODO("Not yet implemented")
    }

    override fun onPostCommentReportFailure(message: String) {
        TODO("Not yet implemented")
    }

    fun isNetworkConnected(): Boolean {
        val cm = requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnected
    }
}
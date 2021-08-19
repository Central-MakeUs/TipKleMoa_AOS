package com.tipklemoa.tipkle.src.main

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.setFragmentResultListener
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tipklemoa.tipkle.config.ApplicationClass
import com.tipklemoa.tipkle.config.BaseResponse
import com.tipklemoa.tipkle.databinding.LayoutCommentBottomsheetBinding
import com.tipklemoa.tipkle.src.main.model.CommentResponse
import com.tipklemoa.tipkle.src.main.model.DetailFeedResponse
import com.tipklemoa.tipkle.src.main.model.NewTipResponse
import com.tipklemoa.tipkle.util.LoadingDialog
import com.tipklemoa.tipkle.src.main.model.PostCommentRequest

class CommentBottomSheet: BottomSheetDialogFragment(), MainView {
    private lateinit var binding: LayoutCommentBottomsheetBinding
    var postId = 0
    var folderId = 0

    var commentAdapter: CommentAdapter?=null
    lateinit var mLoadingDialog: LoadingDialog
    var editor = ApplicationClass.sSharedPreferences.edit()
    lateinit var networkCallback : ConnectivityManager.NetworkCallback

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        if (!isNetworkConnected()){
            Toast.makeText(requireContext(), "네트워크 연결을 확인해주세요", Toast.LENGTH_SHORT).show()
        }

        postId = arguments?.getInt("postId")!!
        binding = LayoutCommentBottomsheetBinding.inflate(inflater, container, false)

        networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                showLoadingDialog(requireContext())
                MainService(this@CommentBottomSheet).tryGetComments(postId)
            }

            override fun onLost(network: Network) {
                // 네트워크가 끊길 때 호출됩니다.

            }
        }

        return binding.root
    }

//    override fun onResume() {
//        super.onResume()
//
//        showLoadingDialog(requireContext())
//        MainService(this).tryGetComments(postId)
//    }

    private val onClicked = object: CommentAdapter.OnItemClickListener {
        override fun onClicked(commentId: Int, isAuthor: Char) {
            val bundle = Bundle()
            bundle.putInt("commentId", commentId)

            Log.d("test", isAuthor.toString())
            Log.d("test", commentId.toString())

            if (isAuthor=='Y') bundle.putString("what", "delete")
            else bundle.putString("what", "report")

            if (!isNetworkConnected()){
                Toast.makeText(requireContext(), "네트워크 연결을 확인해주세요", Toast.LENGTH_SHORT).show()
            }
            else{
                val editOrDeleteBottomSheet = DeleteOrReportBottomSheet()
                editOrDeleteBottomSheet.arguments = bundle
                editOrDeleteBottomSheet.show(parentFragmentManager, editOrDeleteBottomSheet.tag)
            }
        }
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
            if (binding.edtComment.text.toString().trim().isNotEmpty()) {
                val postCommentRequest = PostCommentRequest(binding.edtComment.text.toString().trim())
                Log.d("test" ,binding.edtComment.text.toString())
                MainService(this).tryPostComment(postId, postCommentRequest)
            }
        }

        setFragmentResultListener("deleteComment"){ key, bundle ->
            if (bundle.getString("deleteComment_ok")=="ok"){
                Toast.makeText(requireContext(), "댓글 삭제가 완료되었습니다", Toast.LENGTH_SHORT).show()
                showLoadingDialog(requireContext())
                MainService(this).tryGetComments(postId)
            }
        }
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

    override fun onStop() {
        super.onStop()

        terminateNetworkCallback(networkCallback)
    }

    override fun onResume() {
        super.onResume()

        registerNetworkCallback(networkCallback)
    }

    override fun onGetFeedDetailSuccess(response: DetailFeedResponse) {

    }

    override fun onGetFeedDetailFailure(message: String) {

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
        commentAdapter!!.setOnItemClickListener(onClicked)

        binding.tvCommentNum.text = response.result.size.toString()
        binding.rvComment.adapter = commentAdapter
        binding.rvComment.layoutManager?.scrollToPosition(response.result.size-1)
    }

    override fun onGetCommentFailure(message: String) {
        dismissLoadingDialog()
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onPostCommentSuccess(response: BaseResponse) {
        dismissLoadingDialog()
        binding.edtComment.text.clear()
        Toast.makeText(requireContext(), "댓글 등록이 완료되었습니다", Toast.LENGTH_SHORT).show()
        showLoadingDialog(requireContext())
        MainService(this).tryGetComments(postId)
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

    // 콜백을 등록하는 함수
    fun registerNetworkCallback(networkCallback: ConnectivityManager.NetworkCallback) {
        val connectivityManager = requireContext().getSystemService(ConnectivityManager::class.java)
        val networkRequest = NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .build()
        connectivityManager?.registerNetworkCallback(networkRequest, networkCallback)
    }

    // 콜백을 해제하는 함수
    fun terminateNetworkCallback(networkCallback: ConnectivityManager.NetworkCallback) {
        val connectivityManager = requireContext().getSystemService(ConnectivityManager::class.java)
        connectivityManager?.unregisterNetworkCallback(networkCallback)
    }
}
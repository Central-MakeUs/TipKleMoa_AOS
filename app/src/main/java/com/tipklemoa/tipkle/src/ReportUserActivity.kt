package com.tipklemoa.tipkle.src

import android.os.Bundle
import com.tipklemoa.tipkle.config.BaseActivity
import com.tipklemoa.tipkle.config.BaseResponse
import com.tipklemoa.tipkle.databinding.ActivityReportUserBinding
import com.tipklemoa.tipkle.src.model.CommentResponse
import com.tipklemoa.tipkle.src.model.DetailFeedResponse
import com.tipklemoa.tipkle.src.model.NewTipResponse

class ReportUserActivity : BaseActivity<ActivityReportUserBinding>(ActivityReportUserBinding::inflate), MainView {

    var postId = 0
    var commentId = 0
    var what = ""
    var reason = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        postId = intent.getIntExtra("postId", 0)
        commentId = intent.getIntExtra("commentId", 0)
        what = intent.getStringExtra("what").toString()

        if (postId!=0){
            binding.tvReportWhat.text = "게시글 신고"
            binding.tvReportWhatReason.text = "게시글을 신고하는 이유를 선택해주세요."
        }
        else if (commentId!=0){
            binding.tvReportWhat.text = "댓글 신고"
            binding.tvReportWhatReason.text = "댓글을 신고하는 이유를 선택해주세요."
        }

        binding.btnReportUserBack.setOnClickListener {
            finish()
        }

        binding.layoutLie.setOnClickListener {
            val bundle = Bundle()
            reason = "거짓 정보"
            if (postId!=0){
                bundle.putInt("postId", postId)
            }
            else if (commentId!=0){
                bundle.putInt("commentId", commentId)
            }
            bundle.putString("what", "report")
            bundle.putString("reason", reason)

            val reallyReportDialog = ReallyReportDialog()
            reallyReportDialog.arguments = bundle
            reallyReportDialog.show(supportFragmentManager, reallyReportDialog.tag)
        }

        binding.layoutAd.setOnClickListener {
            val bundle = Bundle()
            reason = "광고 및 홍보성 내용"
            if (postId!=0){
                bundle.putInt("postId", postId)
            }
            else if (commentId!=0){
                bundle.putInt("commentId", commentId)
            }
            bundle.putString("what", "report")
            bundle.putString("reason", reason)

            val reallyReportDialog = ReallyReportDialog()
            reallyReportDialog.arguments = bundle
            reallyReportDialog.show(supportFragmentManager, reallyReportDialog.tag)
        }

        binding.layoutAbuse.setOnClickListener {
            val bundle = Bundle()
            reason = "욕설, 비방 등 "
            if (postId!=0){
                bundle.putInt("postId", postId)
            }
            else if (commentId!=0){
                bundle.putInt("commentId", commentId)
            }
            bundle.putString("what", "report")
            bundle.putString("reason", reason)

            val reallyReportDialog = ReallyReportDialog()
            reallyReportDialog.arguments = bundle
            reallyReportDialog.show(supportFragmentManager, reallyReportDialog.tag)
        }

        binding.layoutCopyright.setOnClickListener {
            val bundle = Bundle()
            reason = "저작권 침해"
            if (postId!=0){
                bundle.putInt("postId", postId)
            }
            else if (commentId!=0){
                bundle.putInt("commentId", commentId)
            }
            bundle.putString("what", "report")
            bundle.putString("reason", reason)

            val reallyReportDialog = ReallyReportDialog()
            reallyReportDialog.arguments = bundle
            reallyReportDialog.show(supportFragmentManager, reallyReportDialog.tag)
        }

        binding.layoutEtc.setOnClickListener {
            val bundle = Bundle()
            reason = "기타"
            if (postId!=0){
                bundle.putInt("postId", postId)
            }
            else if (commentId!=0){
                bundle.putInt("commentId", commentId)
            }
            bundle.putString("what", "report")
            bundle.putString("reason", reason)

            val reallyReportDialog = ReallyReportDialog()
            reallyReportDialog.arguments = bundle
            reallyReportDialog.show(supportFragmentManager, reallyReportDialog.tag)
        }

        supportFragmentManager
            .setFragmentResultListener("report", this) { requestKey, bundle ->
                // We use a String here, but any type that can be put in a Bundle is supported
                val result = bundle.getString("report_ok")
                // Do something with the result
                if (result=="ok"){
                    showCustomToast("신고가 완료되었습니다")
                    finish()
                }
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

}
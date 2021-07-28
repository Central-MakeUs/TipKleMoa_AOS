package com.tipklemoa.tipkle.src

import android.os.Bundle
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.tipklemoa.tipkle.R
import com.tipklemoa.tipkle.config.ApplicationClass
import com.tipklemoa.tipkle.config.BaseActivity
import com.tipklemoa.tipkle.config.BaseResponse
import com.tipklemoa.tipkle.databinding.ActivityFeedDetailBinding
import com.tipklemoa.tipkle.src.model.CommentResponse
import com.tipklemoa.tipkle.src.model.DetailFeedResponse
import com.tipklemoa.tipkle.src.model.NewTipResponse
import com.google.android.material.bottomsheet.BottomSheetBehavior
import android.widget.FrameLayout
import com.google.android.material.bottomsheet.BottomSheetDialog
import android.util.DisplayMetrics

class FeedDetailActivity : BaseActivity<ActivityFeedDetailBinding>(ActivityFeedDetailBinding::inflate), MainView {
    var postId = 0
    var isBookMarked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        postId = intent.getIntExtra("postId", 0)
        isBookMarked = intent.getBooleanExtra("isBookMarked", false)

        Log.d("postId", postId.toString())

        showLoadingDialog(this)
        MainService(this).tryGetFeedDetail(postId)
    }

    override fun onGetFeedDetailSuccess(response: DetailFeedResponse) {
        dismissLoadingDialog()
        binding.btnDetailBack.setOnClickListener {
            finish()
        }

        val pagerAdapter = FeedDetailFragmentStateAdapter(this)
        for (element in response.result.imgUrl){
            pagerAdapter.addFragment(FeedDetailFragment(element))
        }

        binding.viewPagerDetail.adapter = pagerAdapter
        binding.detailCircleIndicator.setViewPager2(binding.viewPagerDetail)

        Glide
            .with(this)
            .load(response.result.profileImgUrl)
            .into(binding.imgDetailFeedProfile) // 프로필

        binding.tvDetailNickName.text = response.result.nickName //닉네임
        binding.tvDetailWhen.text = response.result.whenText
        binding.tvDetailHow.text = response.result.howText
        binding.tvDetailText.text = response.result.description
        binding.ratingBarDetail.rating = response.result.star.toFloat()
        binding.tvDetailFloat.text = response.result.score
        binding.tvDetailStar.text = response.result.score

        if (response.result.isStarred=='Y'){
            binding.btnDetailStar.setBackgroundResource(R.drawable.ic_mint_start)
        }
        else{
            binding.btnDetailStar.setBackgroundResource(R.drawable.ic_empty_star)
        }

        binding.btnDetailStar.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("postId", postId)
            val starDialog = StarDialog()
            starDialog.arguments = bundle
            starDialog.show(supportFragmentManager, starDialog.tag)
        }
// Use this to programmatically apply behavior attributes
        binding.btnDetailComent.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("postId", postId)

            val commentBottomSheet = CommentBottomSheet()
            commentBottomSheet.arguments = bundle
            commentBottomSheet.show(supportFragmentManager, commentBottomSheet.tag)
        }

        isBookMarked = if (response.result.isBookMarked=='Y'){
            binding.btnDetailAddBookMark.setBackgroundResource(R.drawable.ic_icon_bookmark_full)
            true
        } else{
            binding.btnDetailAddBookMark.setBackgroundResource(R.drawable.ic_bookmark_line)
            false
        }

        if (response.result.isAuthor=='Y'){
            binding.btnDetailEdit.visibility = View.VISIBLE
            binding.btnDetailEdit.setOnClickListener {
                val bundle = Bundle()
                bundle.putInt("postId", postId)
                val editOrDeleteBottomSheet = EditOrDeleteBottomSheet()
                editOrDeleteBottomSheet.arguments = bundle
                editOrDeleteBottomSheet.show(supportFragmentManager, editOrDeleteBottomSheet.tag)
            }
        }

        binding.tvCommentCount.text = response.result.commentCount.toString()

        binding.btnDetailAddBookMark.setOnClickListener {
            if (isBookMarked){ //취소
                showLoadingDialog(this)
                MainService(this).tryDeleteBookMark(postId)
                isBookMarked = false
                binding.btnDetailAddBookMark.setBackgroundResource(R.drawable.ic_bookmark_line)
            }
            else{ //저장
                val bundle = Bundle()
                bundle.putInt("postId", postId)
                val addBookmarkBottomSheet = AddBookmarkBottomSheet()
                addBookmarkBottomSheet.arguments = bundle
                addBookmarkBottomSheet.show(supportFragmentManager, addBookmarkBottomSheet.tag)
                isBookMarked = true
            }
        }

        supportFragmentManager
            .setFragmentResultListener("delete", this) { requestKey, bundle ->
                // We use a String here, but any type that can be put in a Bundle is supported
                val result = bundle.getString("delete_ok")
                Log.d("result", result.toString())
                binding.btnDetailAddBookMark.setBackgroundResource(R.drawable.ic_detail_bookmark_empty)
                // Do something with the result
                if (result=="ok"){
                    this.finish()
                }
            }

        supportFragmentManager
            .setFragmentResultListener("addBookMark", this) { requestKey, bundle ->
                // We use a String here, but any type that can be put in a Bundle is supported
                val result = bundle.getString("addBookMark_ok")
                Log.d("result", result.toString())
                // Do something with the result
                if (result=="ok"){
                    binding.btnDetailAddBookMark.setBackgroundResource(R.drawable.ic_icon_bookmark_full)
                    isBookMarked = true
                }
            }

        supportFragmentManager
            .setFragmentResultListener("star", this) { requestKey, bundle ->
                // We use a String here, but any type that can be put in a Bundle is supported
                val result = bundle.getString("star_ok")
                Log.d("result", result.toString())
                // Do something with the result
                if (result=="ok"){
                    showLoadingDialog(this)
                    MainService(this).tryGetFeedDetail(postId)
                }
            }
    }

    override fun onGetFeedDetailFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)
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
        dismissLoadingDialog()
        showCustomToast("북마크 해제 완료")
    }

    override fun onDeleteBookmarkFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)
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
}
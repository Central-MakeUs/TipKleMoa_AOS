package com.tipklemoa.tipkle.src

import android.os.Bundle
import com.bumptech.glide.Glide
import com.tipklemoa.tipkle.R
import com.tipklemoa.tipkle.config.BaseActivity
import com.tipklemoa.tipkle.databinding.ActivityFeedDetailBinding
import com.tipklemoa.tipkle.src.model.DetailFeedResponse

class FeedDetailActivity : BaseActivity<ActivityFeedDetailBinding>(ActivityFeedDetailBinding::inflate), MainView {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val postId = intent.getIntExtra("postId", 0)
        showLoadingDialog(this)
        MainService(this).tryHomePreviewFeed(postId)
    }

    override fun onGetFeedDetailSuccess(response: DetailFeedResponse) {
        dismissLoadingDialog()
        binding.btnDetailBack.setOnClickListener {
            this.finish()
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

        if (response.result.isStarred=='Y'){
            binding.btnDetailStar.setBackgroundResource(R.drawable.ic_mint_start)
        }
        else{
            binding.btnDetailStar.setBackgroundResource(R.drawable.ic_empty_star)
        }

        binding.tvCommentCount.text = response.result.commentCount.toString()
    }

    override fun onGetFeedDetailFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)
    }
}
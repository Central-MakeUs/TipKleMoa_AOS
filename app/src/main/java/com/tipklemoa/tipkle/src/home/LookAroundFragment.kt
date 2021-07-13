package com.tipklemoa.tipkle.src.home

import android.os.Bundle
import android.util.Log
import com.tipklemoa.tipkle.R
import com.tipklemoa.tipkle.config.ApplicationClass
import com.tipklemoa.tipkle.config.BaseFragment
import com.tipklemoa.tipkle.config.BaseResponse
import com.tipklemoa.tipkle.databinding.FragmentHomeBinding
import com.tipklemoa.tipkle.databinding.FragmentLookAroundBinding
import com.tipklemoa.tipkle.src.home.model.BannerResponse
import com.tipklemoa.tipkle.src.home.model.CategoryListResponse
import com.tipklemoa.tipkle.src.home.model.HomePreviewFeedResponse
import com.tipklemoa.tipkle.src.home.model.LookAroundResponse

class LookAroundFragment : BaseFragment<FragmentLookAroundBinding>(
    FragmentLookAroundBinding::bind,
    R.layout.fragment_look_around
), HomeFragmentView{

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val clickedCatName = ApplicationClass.sSharedPreferences.getString("clickedCatName", null)
        if (clickedCatName != null) {
            showLoadingDialog(requireContext())
            HomeService(this).tryLookAroundFeed(clickedCatName, "recent")
        }

        binding.tvLookAroundRecent.setOnClickListener {
            binding.tvLookAroundRecent.setTextColor(resources.getColor(R.color.black))
            binding.tvLookAroundPopular.setTextColor(resources.getColor(R.color.DBGray))
            showLoadingDialog(requireContext())
            if (clickedCatName != null) {
                HomeService(this).tryLookAroundFeed(clickedCatName, "recent")
            }
        }

        binding.tvLookAroundPopular.setOnClickListener {
            binding.tvLookAroundPopular.setTextColor(resources.getColor(R.color.black))
            binding.tvLookAroundRecent.setTextColor(resources.getColor(R.color.DBGray))
            showLoadingDialog(requireContext())
            if (clickedCatName != null) {
                HomeService(this).tryLookAroundFeed(clickedCatName, "popular")
            }
        }
    }

    override fun onGetPickedCategoryListSuccess(response: CategoryListResponse) {
        TODO("Not yet implemented")
    }

    override fun onGetPickedCategoryListFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onGetBannerSuccess(response: BannerResponse) {
        TODO("Not yet implemented")
    }

    override fun onGetBannerFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onGetHomePreviewFeedSuccess(response: HomePreviewFeedResponse) {
        TODO("Not yet implemented")
    }

    override fun onGetHomePreviewFeedFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onPatchCategorySuccess(response: BaseResponse) {
        TODO("Not yet implemented")
    }

    override fun onPatchCategoryFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onGetLookAroundFeedSuccess(response: LookAroundResponse) {
        dismissLoadingDialog()
        var feedAdapter = LookAroundFeedAdapter(requireContext(), response.result)
        binding.rvLookAroundFeed.adapter = feedAdapter
    }

    override fun onGetLookAroundFeedFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)
    }
}
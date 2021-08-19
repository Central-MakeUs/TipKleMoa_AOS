package com.tipklemoa.tipkle.src.home

import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.tipklemoa.tipkle.src.main.PagerFragmentStateAdapter
import com.tipklemoa.tipkle.R
import com.tipklemoa.tipkle.config.BaseFragment
import com.tipklemoa.tipkle.config.BaseResponse
import com.tipklemoa.tipkle.databinding.ViewpagerHomeTabBinding
import com.tipklemoa.tipkle.src.home.model.BannerResponse
import com.tipklemoa.tipkle.src.home.model.CategoryListResponse
import com.tipklemoa.tipkle.src.home.model.HomePreviewFeedResponse
import com.tipklemoa.tipkle.src.home.model.LookAroundResponse

class HomeTopViewPagerFragment : BaseFragment<ViewpagerHomeTabBinding>(ViewpagerHomeTabBinding::bind,
    R.layout.viewpager_home_tab), HomeFragmentView {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.btnSettingCategory.setOnClickListener {
            if (!isNetworkConnected()){
                showCustomToast("네트워크 연결을 확인해주세요!")
            }
            else{
                val homeEditCategoryBottomSheet = HomeEditCategoryBottomSheet()
                homeEditCategoryBottomSheet.show(parentFragmentManager, homeEditCategoryBottomSheet.tag)
            }
        }

        val pagerAdapter = PagerFragmentStateAdapter(requireActivity())
        // 2개의 Fragment Add
        //pagerAdapter.addFragment(TodayTipFragment())
        pagerAdapter.addFragment(PickedTipFragment())
        // Adapter
        binding.vpInner.adapter = pagerAdapter

        binding.vpInner.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
            }
        })

        binding.vpInner.isUserInputEnabled = false //스와이프를 이용하여 페이지 넘김 가능 여부
    }

    override fun onGetPickedCategoryListSuccess(response: CategoryListResponse) {
        dismissLoadingDialog()

    }

    override fun onGetPickedCategoryListFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)
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
        TODO("Not yet implemented")
    }

    override fun onGetLookAroundFeedFailure(message: String) {
        TODO("Not yet implemented")
    }
}
package com.tipklemoa.tipkle.src.home

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.tabs.TabLayout
import com.tipklemoa.tipkle.R
import com.tipklemoa.tipkle.config.BaseFragment
import com.tipklemoa.tipkle.databinding.ViewpagerPickedTipTabBinding
import com.tipklemoa.tipkle.src.home.model.BannerResponse
import com.tipklemoa.tipkle.src.home.model.CategoryListResponse
import com.tipklemoa.tipkle.src.home.model.HomePreviewFeedResponse

class PickedTipViewPagerFragment : BaseFragment<ViewpagerPickedTipTabBinding>(ViewpagerPickedTipTabBinding::bind,
    R.layout.viewpager_picked_tip_tab), HomeFragmentView {

    var clickedCatName:String?=null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        showLoadingDialog(requireContext())
        HomeService(this).tryGetPickedCategoryList()
        HomeService(this).tryGetBanner()
    }

    override fun onGetPickedCategoryListSuccess(response: CategoryListResponse) {
        dismissLoadingDialog()
        for (e in response.result) {
            binding.pickedCatTab.addTab(binding.pickedCatTab.newTab().setText(e.categoryName))
        }
        showLoadingDialog(requireContext())

        //맨 첫번째로 선택한 카테고리의 최신순 띄워주기
        clickedCatName = response.result[0].categoryName
        HomeService(this).tryHomePreviewFeed(response.result[0].categoryName, "recent")

        //탭 클릭시 내용 바뀌는 부분
        binding.pickedCatTab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {

                //선택한 탭의 텍스트를 받아오는 부분
                clickedCatName = binding.pickedCatTab.getTabAt(binding.pickedCatTab.selectedTabPosition)?.text.toString()
                showLoadingDialog(requireContext())

                binding.pickedtiprecent.setTextColor(resources.getColor(R.color.black))
                binding.pickedtippopular.setTextColor(resources.getColor(R.color.DBGray))
                HomeService(this@PickedTipViewPagerFragment).tryHomePreviewFeed(clickedCatName!!, "recent")
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })

        binding.pickedtiprecent.setOnClickListener {
            binding.pickedtiprecent.setTextColor(resources.getColor(R.color.black))
            binding.pickedtippopular.setTextColor(resources.getColor(R.color.DBGray))
            showLoadingDialog(requireContext())
            HomeService(this).tryHomePreviewFeed(clickedCatName!!, "recent")
        }

        binding.pickedtippopular.setOnClickListener {
            binding.pickedtippopular.setTextColor(resources.getColor(R.color.black))
            binding.pickedtiprecent.setTextColor(resources.getColor(R.color.DBGray))
            showLoadingDialog(requireContext())
            HomeService(this).tryHomePreviewFeed(clickedCatName!!, "popular")
        }
    }

    override fun onGetPickedCategoryListFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)
    }

    override fun onGetBannerSuccess(response: BannerResponse) {
        val pagerAdapter = activity?.let { HomeBannerFragmentStateAdapter(it, response.result) }
        binding.pickedBannerViewPager.adapter = pagerAdapter
        binding.pickedIndicator.setViewPager2(binding.pickedBannerViewPager)
    }

    override fun onGetBannerFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)
    }

    override fun onGetHomePreviewFeedSuccess(response: HomePreviewFeedResponse) {
        dismissLoadingDialog()
        val feedadapter = HomeFeedAdapter(requireContext(), response.result)
        val gridLayoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvHomePreviewFeed.layoutManager = gridLayoutManager
        binding.rvHomePreviewFeed.adapter = feedadapter
    }

    override fun onGetHomePreviewFeedFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)
    }
}
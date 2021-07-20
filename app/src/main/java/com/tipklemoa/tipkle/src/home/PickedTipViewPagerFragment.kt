package com.tipklemoa.tipkle.src.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.tabs.TabLayout
import com.tipklemoa.tipkle.R
import com.tipklemoa.tipkle.config.ApplicationClass
import com.tipklemoa.tipkle.config.BaseFragment
import com.tipklemoa.tipkle.config.BaseResponse
import com.tipklemoa.tipkle.databinding.ViewpagerPickedTipTabBinding
import com.tipklemoa.tipkle.src.SelectPicActivity
import com.tipklemoa.tipkle.src.home.model.BannerResponse
import com.tipklemoa.tipkle.src.home.model.CategoryListResponse
import com.tipklemoa.tipkle.src.home.model.HomePreviewFeedResponse
import com.tipklemoa.tipkle.src.home.model.LookAroundResponse

class PickedTipViewPagerFragment : BaseFragment<ViewpagerPickedTipTabBinding>(ViewpagerPickedTipTabBinding::bind,
    R.layout.viewpager_picked_tip_tab), HomeFragmentView {

    var clickedCatName:String?=null
    var editor = ApplicationClass.sSharedPreferences.edit()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.pickedfloatting.setOnClickListener {
            startActivity(Intent(requireContext(), SelectPicActivity::class.java))
        }
        showLoadingDialog(requireContext())
        HomeService(this).tryGetPickedCategoryList()
        HomeService(this).tryGetBanner()

        setFragmentResultListener("editCat"){ key, bundle ->
            if (bundle.getString("editCat_ok")=="ok"){
                showLoadingDialog(requireContext())
                HomeService(this).tryGetPickedCategoryList()
            }
        }
    }

    override fun onGetPickedCategoryListSuccess(response: CategoryListResponse) {
        dismissLoadingDialog()
        binding.pickedCatTab.removeAllTabs()
        for (e in response.result) {
            binding.pickedCatTab.addTab(binding.pickedCatTab.newTab().setText(e.categoryName))
        }

        //맨 첫번째로 선택한 카테고리의 최신순 띄워주기
        //그리고 기기내 저장
        clickedCatName = response.result[0].categoryName
        editor.putString("clickedCatName", clickedCatName)
        editor.apply()

        HomeService(this).tryHomePreviewFeed(response.result[0].categoryName, "recent")

        //탭 클릭시 내용 바뀌는 부분
        binding.pickedCatTab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {

                //선택한 탭의 텍스트를 받아오는 부분
                clickedCatName = binding.pickedCatTab.getTabAt(binding.pickedCatTab.selectedTabPosition)?.text.toString()
                editor.putString("clickedCatName", clickedCatName)
                editor.apply()

                binding.pickedtiprecent.setTextColor(resources.getColor(R.color.black))
                binding.pickedtippopular.setTextColor(resources.getColor(R.color.DBGray))
                showLoadingDialog(requireContext())
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

        binding.pickedLookAround.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.homeinnerFrame, LookAroundFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onGetPickedCategoryListFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)
    }

    override fun onGetBannerSuccess(response: BannerResponse) {
        dismissLoadingDialog()
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
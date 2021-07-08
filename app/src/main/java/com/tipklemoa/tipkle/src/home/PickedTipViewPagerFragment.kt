package com.tipklemoa.tipkle.src.home

import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import com.tipklemoa.tipkle.R
import com.tipklemoa.tipkle.config.BaseFragment
import com.tipklemoa.tipkle.databinding.ViewpagerPickedTipTabBinding
import com.tipklemoa.tipkle.src.home.model.BannerResponse
import com.tipklemoa.tipkle.src.home.model.CategoryListResponse

class PickedTipViewPagerFragment : BaseFragment<ViewpagerPickedTipTabBinding>(ViewpagerPickedTipTabBinding::bind,
    R.layout.viewpager_picked_tip_tab), HomeFragmentView {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        showLoadingDialog(requireContext())
        HomeService(this).tryGetPickedCategoryList()

        binding.pickedCatTab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {

                //선택한 탭의 텍스트를 받아오는 부분
                //Log.d("hello", binding.pickedCatTab.getTabAt(binding.pickedCatTab.selectedTabPosition)?.text.toString())
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })

    }

    override fun onGetPickedCategoryListSuccess(response: CategoryListResponse) {
        dismissLoadingDialog()
        for (e in response.result) {
            binding.pickedCatTab.addTab(binding.pickedCatTab.newTab().setText(e.categoryName))
        }
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
}
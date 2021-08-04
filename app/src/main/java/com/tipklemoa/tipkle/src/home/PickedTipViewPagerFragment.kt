package com.tipklemoa.tipkle.src.home

import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
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
import com.tipklemoa.tipkle.src.MainService
import com.tipklemoa.tipkle.src.RegisterNewTipActivity
import com.tipklemoa.tipkle.src.SelectPicActivity
import com.tipklemoa.tipkle.src.home.model.BannerResponse
import com.tipklemoa.tipkle.src.home.model.CategoryListResponse
import com.tipklemoa.tipkle.src.home.model.HomePreviewFeedResponse
import com.tipklemoa.tipkle.src.home.model.LookAroundResponse

class PickedTipViewPagerFragment : BaseFragment<ViewpagerPickedTipTabBinding>(ViewpagerPickedTipTabBinding::bind,
    R.layout.viewpager_picked_tip_tab), HomeFragmentView {
    lateinit var networkCallback : ConnectivityManager.NetworkCallback

    var clickedCatName:String?=null
    var editor = ApplicationClass.sSharedPreferences.edit()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                showLoadingDialog(requireContext())
                HomeService(this@PickedTipViewPagerFragment).tryGetPickedCategoryList()
                HomeService(this@PickedTipViewPagerFragment).tryGetBanner()
            }

            override fun onLost(network: Network) {
                // 네트워크가 끊길 때 호출됩니다.

            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!isNetworkConnected()){
            showCustomToast("네트워크 연결을 확인해주세요!")
        }

        binding.mainRefresh.setOnRefreshListener {
            if (!isNetworkConnected()){
                showCustomToast("네트워크 연결을 확인해주세요!")
            }
            else{
                showLoadingDialog(requireContext())
                HomeService(this@PickedTipViewPagerFragment).tryGetPickedCategoryList()
                HomeService(this@PickedTipViewPagerFragment).tryGetBanner()
            }
            binding.mainRefresh.isRefreshing = false
        }

        binding.pickedfloatting.setOnClickListener {
            binding.pickedfloatting.compatElevation = 0F
            binding.pickedfloatting.rippleColor = resources.getColor(R.color.transparent)
            binding.pickedfloatting.animation = null

            startActivity(Intent(requireContext(), SelectPicActivity::class.java))
        }

        setFragmentResultListener("editCat"){ key, bundle ->
            if (bundle.getString("editCat_ok")=="ok"){
                if (!isNetworkConnected()){
                    showCustomToast("네트워크 연결을 확인해주세요!")
                }
                else{
                    showLoadingDialog(requireContext())
                    binding.pickedCatTab.removeAllTabs()
                    HomeService(this).tryGetPickedCategoryList()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        registerNetworkCallback(networkCallback)
    }

    override fun onStop() {
        super.onStop()

        terminateNetworkCallback(networkCallback)
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
                if (!isNetworkConnected()){
                    showCustomToast("네트워크 연결을 확인해주세요!")
                }
                else{
                    HomeService(this@PickedTipViewPagerFragment).tryHomePreviewFeed(clickedCatName!!, "recent")
                }
                //showLoadingDialog(requireContext())
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })

        binding.pickedtiprecent.setOnClickListener {
            binding.pickedtiprecent.setTextColor(resources.getColor(R.color.black))
            binding.pickedtippopular.setTextColor(resources.getColor(R.color.DBGray))
            if (!isNetworkConnected()){
                showCustomToast("네트워크 연결을 확인해주세요!")
            }
            else{
                showLoadingDialog(requireContext())
                HomeService(this).tryHomePreviewFeed(clickedCatName!!, "recent")
            }
        }

        binding.pickedtippopular.setOnClickListener {
            binding.pickedtippopular.setTextColor(resources.getColor(R.color.black))
            binding.pickedtiprecent.setTextColor(resources.getColor(R.color.DBGray))
            if (!isNetworkConnected()){
                showCustomToast("네트워크 연결을 확인해주세요!")
            }
            else{
                showLoadingDialog(requireContext())
                HomeService(this).tryHomePreviewFeed(clickedCatName!!, "popular")
            }
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
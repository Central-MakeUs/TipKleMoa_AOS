package com.tipklemoa.tipkle.src.home

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.tipklemoa.tipkle.R
import com.tipklemoa.tipkle.config.ApplicationClass
import com.tipklemoa.tipkle.config.BaseFragment
import com.tipklemoa.tipkle.config.BaseResponse
import com.tipklemoa.tipkle.databinding.FragmentHomeBinding
import com.tipklemoa.tipkle.databinding.FragmentLookAroundBinding
import com.tipklemoa.tipkle.src.MainActivity
import com.tipklemoa.tipkle.src.home.model.*

class LookAroundFragment : BaseFragment<FragmentLookAroundBinding>(
    FragmentLookAroundBinding::bind,
    R.layout.fragment_look_around
), HomeFragmentView{
    var clickedCatName = ApplicationClass.sSharedPreferences.getString("clickedCatName", null)

    private var lookaroundList = mutableListOf<ResultLookAround>()
    private var page = 1      // 현재 페이지
    var isFeedEnd = false

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

//        if (clickedCatName != null) {
//            showLoadingDialog(requireContext())
//            HomeService(this).tryLookAroundFeed(clickedCatName!!, "recent", null, page)
//        }

        binding.tvLookAroundRecent.setOnClickListener {
            binding.tvLookAroundRecent.setTextColor(resources.getColor(R.color.black))
            binding.tvLookAroundPopular.setTextColor(resources.getColor(R.color.DBGray))
            showLoadingDialog(requireContext())
            if (clickedCatName != null) {
                page = 1
                lookaroundList.clear()

                HomeService(this).tryLookAroundFeed(clickedCatName!!, "recent", null, page)
            }
        }

        binding.tvLookAroundPopular.setOnClickListener {
            binding.tvLookAroundPopular.setTextColor(resources.getColor(R.color.black))
            binding.tvLookAroundRecent.setTextColor(resources.getColor(R.color.DBGray))
            showLoadingDialog(requireContext())
            if (clickedCatName != null) {
                page = 1
                lookaroundList.clear()
                HomeService(this).tryLookAroundFeed(clickedCatName!!, "popular", null, page)
            }
        }

        binding.btnLookAroundBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.rvLookAroundFeed.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

//             direction:  양수일경우엔 오른쪽 스크롤, 음수일경우엔 왼쪽 스크롤
//                수평으로 더이상 스크롤이 안되면, 데이터를 더해서 불러옴
                if (!binding.rvLookAroundFeed.canScrollVertically(1)) {
                    if (!isFeedEnd) {
                        dismissLoadingDialog()
                        showLoadingDialog(requireContext())
                        if (clickedCatName != null) {
                            HomeService(this@LookAroundFragment).tryLookAroundFeed(
                                clickedCatName!!,
                                "popular",
                                null, ++page
                            )
                        }
                    }
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()

        page = 1
        lookaroundList.clear()
        isFeedEnd = false
        showLoadingDialog(requireContext())
        clickedCatName?.let { HomeService(this).tryLookAroundFeed(it, "recent", null, page) }
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
        Handler(Looper.getMainLooper()).postDelayed({
            dismissLoadingDialog()
        }, 1500)

        var feedAdapter = LookAroundFeedAdapter(requireContext(), response.result)
//      맨 처음(page=1) -> 데이터가 하나라도 있으면
        if (page == 1 && response.result.isNotEmpty()) {
            Log.d("case", "1")
            lookaroundList.addAll(response.result)
            feedAdapter = LookAroundFeedAdapter(requireContext(), lookaroundList)
            binding.rvLookAroundFeed.adapter = feedAdapter
        }
//      page=1부터 불러오고, 둥지가 있으면 추가해줘야함 ->
        else if (page != 1 && response.result.isNotEmpty()) {
            Log.d("case", "2")
            lookaroundList.addAll(response.result)
            feedAdapter.notifyItemInserted(lookaroundList.size - 1)
        }
//        페이지추가 끝
        else if (page != 1 && response.result.isNullOrEmpty()) {
            Log.d("case", "3")
            isFeedEnd = true
        }
    }

    override fun onGetLookAroundFeedFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)
    }
}
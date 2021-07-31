package com.tipklemoa.tipkle.src.home

import android.content.Context
import android.graphics.Point
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.FrameLayout
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tipklemoa.tipkle.R
import com.tipklemoa.tipkle.config.BaseResponse
import com.tipklemoa.tipkle.databinding.HomeBottomsheetLayoutBinding
import com.tipklemoa.tipkle.src.home.model.*
import com.tipklemoa.tipkle.util.LoadingDialog

class HomeEditCategoryBottomSheet: BottomSheetDialogFragment(), HomeFragmentView{
    private lateinit var mLoadingDialog: LoadingDialog
    private lateinit var binding: HomeBottomsheetLayoutBinding
    private var categoryBooleanList = Array(6){false}
    var pickedNum = 0
    var selectallflag = false
    private var pickedCategoryList = mutableListOf<Int>()
    var windowManager: WindowManager? = null
    var display: Display? = null
    var size: Point? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = HomeBottomsheetLayoutBinding.inflate(inflater, container, false)

        windowManager = activity?.getSystemService(Context.WINDOW_SERVICE) as? WindowManager
        display = windowManager!!.defaultDisplay
        size = Point()
        display!!.getSize(size)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomSheet = view.findViewById<View>(R.id.home_editCat_layout) as ConstraintLayout
        //bottomSheet.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
        val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        bottomSheetBehavior.peekHeight = 500
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        
        showLoadingDialog(requireContext())
        HomeService(this).tryGetPickedCategoryList()

        binding.btnAllCategory.setOnClickListener{
            if (!selectallflag){ //카테고리 전체가 선택이 되지 않았었던 경우
                binding.btnAllCategory.setBackgroundResource(R.drawable.check_box_color)
                binding.btnChooseClean.setBackgroundResource(R.drawable.check_box_color)
                binding.btnChooseCook.setBackgroundResource(R.drawable.check_box_color)
                binding.btnChooseJachi.setBackgroundResource(R.drawable.check_box_color)
                binding.btnChooseTrip.setBackgroundResource(R.drawable.check_box_color)
                binding.btnChooseUniv.setBackgroundResource(R.drawable.check_box_color)
                binding.btnChooseBeauty.setBackgroundResource(R.drawable.check_box_color)

                pickedCategoryList.clear()

                pickedCategoryList.add(1)
                pickedCategoryList.add(2)
                pickedCategoryList.add(3)
                pickedCategoryList.add(4)
                pickedCategoryList.add(5)
                pickedCategoryList.add(6)

                categoryBooleanList = Array(6){true}

                pickedNum = 6
                selectallflag = true
            }
            else{ //카테고리 전체 선택이 되어있었던 경우
                binding.btnAllCategory.setBackgroundResource(R.drawable.check_box_line)
                binding.btnChooseClean.setBackgroundResource(R.drawable.check_box_line)
                binding.btnChooseCook.setBackgroundResource(R.drawable.check_box_line)
                binding.btnChooseJachi.setBackgroundResource(R.drawable.check_box_line)
                binding.btnChooseTrip.setBackgroundResource(R.drawable.check_box_line)
                binding.btnChooseUniv.setBackgroundResource(R.drawable.check_box_line)
                binding.btnChooseBeauty.setBackgroundResource(R.drawable.check_box_line)

                pickedCategoryList.clear()
                categoryBooleanList = Array(6){false}

                pickedNum = 0
                selectallflag = false
            }
            checkFour()
        }

        binding.btnChooseClean.setOnClickListener {
            if (!categoryBooleanList[0]){
                binding.btnChooseClean.setBackgroundResource(R.drawable.check_box_color)
                pickedNum++
                pickedCategoryList.add(1)

                categoryBooleanList[0] = true
            } else {
                binding.btnChooseClean.setBackgroundResource(R.drawable.check_box_line)
                pickedNum--
                pickedCategoryList.remove(1)

                categoryBooleanList[0] = false
            }
            checkFour()
        }

        binding.btnChooseCook.setOnClickListener {
            if (!categoryBooleanList[1]){
                binding.btnChooseCook.setBackgroundResource(R.drawable.check_box_color)
                pickedNum++
                pickedCategoryList.add(2)

                categoryBooleanList[1] = true
            } else {
                binding.btnChooseCook.setBackgroundResource(R.drawable.check_box_line)
                pickedNum--
                pickedCategoryList.remove(2)

                categoryBooleanList[1] = false
            }
            checkFour()
        }

        binding.btnChooseBeauty.setOnClickListener {
            if (!categoryBooleanList[2]){
                binding.btnChooseBeauty.setBackgroundResource(R.drawable.check_box_color)
                pickedNum++
                pickedCategoryList.add(3)

                categoryBooleanList[2] = true
            } else {
                binding.btnChooseBeauty.setBackgroundResource(R.drawable.check_box_line)
                pickedNum--
                pickedCategoryList.remove(3)

                categoryBooleanList[2] = false
            }
            checkFour()
        }

        binding.btnChooseJachi.setOnClickListener {
            if (!categoryBooleanList[3]){
                binding.btnChooseJachi.setBackgroundResource(R.drawable.check_box_color)
                pickedNum++
                pickedCategoryList.add(4)

                categoryBooleanList[3] = true
            } else {
                binding.btnChooseJachi.setBackgroundResource(R.drawable.check_box_line)
                pickedNum--
                pickedCategoryList.remove(4)

                categoryBooleanList[3] = false
            }
            checkFour()
        }

        binding.btnChooseTrip.setOnClickListener {
            if (!categoryBooleanList[4]){
                binding.btnChooseTrip.setBackgroundResource(R.drawable.check_box_color)
                pickedNum++
                pickedCategoryList.add(5)

                categoryBooleanList[4] = true
            } else {
                binding.btnChooseTrip.setBackgroundResource(R.drawable.check_box_line)
                pickedNum--
                pickedCategoryList.remove(5)

                categoryBooleanList[4] = false
            }
            checkFour()
        }

        binding.btnChooseUniv.setOnClickListener {
            if (!categoryBooleanList[5]){
                binding.btnChooseUniv.setBackgroundResource(R.drawable.check_box_color)
                pickedNum++
                pickedCategoryList.add(6)

                categoryBooleanList[5] = true
            } else {
                binding.btnChooseUniv.setBackgroundResource(R.drawable.check_box_line)
                pickedNum--
                pickedCategoryList.remove(6)

                categoryBooleanList[5] = false
            }
            checkFour()
        }

        binding.btnCompleteEditCategory.setOnClickListener {
            showLoadingDialog(requireContext())
            pickedCategoryList.sort()
            var patchCategoryRequest = PatchCategoryRequest(pickedCategoryList)
            HomeService(this).tryPatchCategory(patchCategoryRequest)
            val bundle = bundleOf("editCat_ok" to "ok")
            // 요청키로 수신측의 리스너에 값을 전달
            setFragmentResult("editCat", bundle)
            this.dismiss()
        }
    }

    private fun checkFour() {
        if (pickedNum>=4){
            binding.btnCompleteEditCategory.isEnabled = true
            binding.btnCompleteEditCategory.setBackgroundColor(resources.getColor(R.color.mint))
        }
        else{
            binding.btnCompleteEditCategory.isEnabled = false
            binding.btnCompleteEditCategory.setBackgroundColor(resources.getColor(R.color.gray))
        }
        Log.d("pickedNum", pickedNum.toString())
    }

    private fun showLoadingDialog(context: Context) {
        mLoadingDialog = LoadingDialog(context)
        mLoadingDialog.show()
    }

    private fun dismissLoadingDialog() {
        if (mLoadingDialog.isShowing) {
            mLoadingDialog.dismiss()
        }
    }

//    override fun onResume() {
//        super.onResume()
//
//        val params: ViewGroup.LayoutParams? = dialog?.window?.attributes
//        val deviceHeight = size!!.y
//
//        params?.height = (deviceHeight*0.7).toInt()
//
//        dialog?.window?.attributes = params as WindowManager.LayoutParams
//    }

    override fun onGetPickedCategoryListSuccess(response: CategoryListResponse) {
        dismissLoadingDialog()
        for (i in response.result){
            when (i.categoryName) {
                "청소" -> {
                    binding.btnChooseClean.setBackgroundResource(R.drawable.check_box_color)
                    categoryBooleanList[0] = true
                    pickedCategoryList.add(1)
                    pickedNum++
                }
                "요리" -> {
                    binding.btnChooseCook.setBackgroundResource(R.drawable.check_box_color)
                    categoryBooleanList[1] = true
                    pickedCategoryList.add(2)
                    pickedNum++
                }
                "뷰티" -> {
                    binding.btnChooseBeauty.setBackgroundResource(R.drawable.check_box_color)
                    categoryBooleanList[2] = true
                    pickedCategoryList.add(3)

                    pickedNum++
                }
                "자취" -> {
                    binding.btnChooseJachi.setBackgroundResource(R.drawable.check_box_color)
                    categoryBooleanList[3] = true
                    pickedCategoryList.add(4)

                    pickedNum++
                }
                "여행" -> {
                    binding.btnChooseTrip.setBackgroundResource(R.drawable.check_box_color)
                    categoryBooleanList[4] = true
                    pickedCategoryList.add(5)

                    pickedNum++
                }
                "대학" -> {
                    binding.btnChooseUniv.setBackgroundResource(R.drawable.check_box_color)
                    categoryBooleanList[5] = true
                    pickedCategoryList.add(6)

                    pickedNum++
                }
            }
        }
        Log.d("pickedNum", pickedNum.toString())
        checkFour()
    }

    override fun onGetPickedCategoryListFailure(message: String) {
        dismissLoadingDialog()
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
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
        dismissLoadingDialog()
        this.dismiss()
    }

    override fun onPatchCategoryFailure(message: String) {
        dismissLoadingDialog()
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    override fun onGetLookAroundFeedSuccess(response: LookAroundResponse) {
        TODO("Not yet implemented")
    }

    override fun onGetLookAroundFeedFailure(message: String) {
        TODO("Not yet implemented")
    }
}
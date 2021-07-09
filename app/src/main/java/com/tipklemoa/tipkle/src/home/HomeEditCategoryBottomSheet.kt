package com.tipklemoa.tipkle.src.home

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tipklemoa.tipkle.R
import com.tipklemoa.tipkle.config.ApplicationClass
import com.tipklemoa.tipkle.databinding.HomeBottomsheetLayoutBinding
import com.tipklemoa.tipkle.util.LoadingDialog

class HomeEditCategoryBottomSheet: BottomSheetDialogFragment(){
    private lateinit var mLoadingDialog: LoadingDialog
    private lateinit var binding: HomeBottomsheetLayoutBinding
    private val categoryBooleanList = Array(6){false}
    var pickedNum = 0
    var selectallflag = false
    private var pickedCategoryList = mutableListOf<Int>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = HomeBottomsheetLayoutBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.btnAllCategory.setOnClickListener{
            if (!selectallflag){ //카테고리 전체가 선택이 되지 않았었던 경우
                binding.btnAllCategory.setBackgroundResource(R.drawable.check_box_color)
                binding.btnChooseClean.setBackgroundResource(R.drawable.check_box_color)
                binding.btnChooseCook.setBackgroundResource(R.drawable.check_box_color)
                binding.btnChooseJachi.setBackgroundResource(R.drawable.check_box_color)
                binding.btnChooseTrip.setBackgroundResource(R.drawable.check_box_color)
                binding.btnChooseUniv.setBackgroundResource(R.drawable.check_box_color)
                binding.btnChooseBeauty.setBackgroundResource(R.drawable.check_box_color)

                pickedCategoryList.add(1)
                pickedCategoryList.add(2)
                pickedCategoryList.add(3)
                pickedCategoryList.add(4)
                pickedCategoryList.add(5)
                pickedCategoryList.add(6)

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

        binding.btnChooseJachi.setOnClickListener {
            if (!categoryBooleanList[2]){
                binding.btnChooseJachi.setBackgroundResource(R.drawable.check_box_color)
                pickedNum++
                pickedCategoryList.add(3)

                categoryBooleanList[2] = true
            } else {
                binding.btnChooseJachi.setBackgroundResource(R.drawable.check_box_line)
                pickedNum--
                pickedCategoryList.remove(3)

                categoryBooleanList[2] = false
            }
            checkFour()
        }

        binding.btnChooseTrip.setOnClickListener {
            if (!categoryBooleanList[3]){
                binding.btnChooseTrip.setBackgroundResource(R.drawable.check_box_color)
                pickedNum++
                pickedCategoryList.add(4)

                categoryBooleanList[3] = true
            } else {
                binding.btnChooseTrip.setBackgroundResource(R.drawable.check_box_line)
                pickedNum--
                pickedCategoryList.remove(4)

                categoryBooleanList[3] = false
            }
            checkFour()
        }

        binding.btnChooseUniv.setOnClickListener {
            if (!categoryBooleanList[4]){
                binding.btnChooseUniv.setBackgroundResource(R.drawable.check_box_color)
                pickedNum++
                pickedCategoryList.add(5)

                categoryBooleanList[4] = true
            } else {
                binding.btnChooseUniv.setBackgroundResource(R.drawable.check_box_line)
                pickedNum--
                pickedCategoryList.remove(5)

                categoryBooleanList[4] = false
            }
            checkFour()
        }

        binding.btnChooseBeauty.setOnClickListener {
            if (!categoryBooleanList[5]){
                binding.btnChooseBeauty.setBackgroundResource(R.drawable.check_box_color)
                pickedNum++
                pickedCategoryList.add(6)

                categoryBooleanList[5] = true
            } else {
                binding.btnChooseBeauty.setBackgroundResource(R.drawable.check_box_line)
                pickedNum--
                pickedCategoryList.remove(6)

                categoryBooleanList[5] = false
            }
            checkFour()
        }

        binding.btnCompleteEditCategory.setOnClickListener {

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
}
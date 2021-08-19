package com.tipklemoa.tipkle.src.main

import android.content.Context
import android.graphics.Point
import android.os.Bundle
import android.view.*
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.tipklemoa.tipkle.databinding.ActivityRegisterNewTipBinding
import com.tipklemoa.tipkle.databinding.LayoutDialogPickcategoryBinding

class NewTipCategoryDialogFragment: DialogFragment(){
    private lateinit var binding: LayoutDialogPickcategoryBinding
    private lateinit var registerNewTipBinding: ActivityRegisterNewTipBinding
    var windowManager: WindowManager? = null
    var display: Display? = null
    var size: Point? = null
   // var editor = ApplicationClass.sSharedPreferences.edit()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = LayoutDialogPickcategoryBinding.inflate(inflater, container, false)
        registerNewTipBinding = ActivityRegisterNewTipBinding.inflate(inflater, container, false)

        windowManager = activity?.getSystemService(Context.WINDOW_SERVICE) as? WindowManager
        display = windowManager!!.defaultDisplay
        size = Point()
        display!!.getSize(size)

        var pickedCat = ""
        var bundle:Bundle?=null
        bundleOf("category" to pickedCat)

        binding.tvNewTipClean.setOnClickListener {
            pickedCat= "청소"
            bundle = bundleOf("category" to pickedCat)
            setFragmentResult("category", bundle!!)
            this.dismiss()
        }

        binding.tvNewTipCook.setOnClickListener {
            pickedCat = "요리"
            bundle = bundleOf("category" to pickedCat)
            setFragmentResult("category", bundle!!)
            this.dismiss()
        }

        binding.tvNewTipJachi.setOnClickListener {
            pickedCat = "자취"
            bundle = bundleOf("category" to pickedCat)
            setFragmentResult("category", bundle!!)
            this.dismiss()
        }

        binding.tvNewTipTrip.setOnClickListener {
            pickedCat = "여행"
            bundle = bundleOf("category" to pickedCat)
            setFragmentResult("category", bundle!!)
            this.dismiss()
        }

        binding.tvNewTipUniv.setOnClickListener {
            pickedCat = "자취"
            bundle = bundleOf("category" to pickedCat)
            setFragmentResult("category", bundle!!)
            this.dismiss()
        }

        binding.tvNewTipBeauty.setOnClickListener {
            pickedCat = "뷰티"
            bundle = bundleOf("category" to pickedCat)
            setFragmentResult("category", bundle!!)
            this.dismiss()
        }

        return binding.root
    }


    override fun onResume() {
        super.onResume()

        var params: ViewGroup.LayoutParams? = dialog?.window?.attributes
        val deviceWidth = size!!.x
        val deviceHeight = size!!.y

        params?.width = (deviceWidth*0.82).toInt()
        params?.height = (deviceHeight*0.72).toInt()

        dialog?.window?.attributes = params as WindowManager.LayoutParams
    }
}
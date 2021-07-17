package com.tipklemoa.tipkle.src

import android.content.Context
import android.graphics.Point
import android.os.Bundle
import android.view.*
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tipklemoa.tipkle.config.ApplicationClass
import com.tipklemoa.tipkle.databinding.LayoutDetailBottomsheetBinding
import com.tipklemoa.tipkle.databinding.LayoutDialogPickcategoryBinding
import com.tipklemoa.tipkle.databinding.LayoutNewtipDialogBinding
import com.tipklemoa.tipkle.util.LoadingDialog

class NewTipCategoryDialogFragment: BottomSheetDialogFragment(){
    private lateinit var mLoadingDialog: LoadingDialog
    private lateinit var binding: LayoutDialogPickcategoryBinding
    var windowManager: WindowManager? = null
    var display: Display? = null
    var size: Point? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = LayoutDialogPickcategoryBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        windowManager = activity?.getSystemService(Context.WINDOW_SERVICE) as? WindowManager
        display = windowManager!!.defaultDisplay
        size = Point()
        display!!.getSize(size)

        binding.tvNewTipBeauty.setOnClickListener {

        }
    }

    override fun onResume() {
        super.onResume()

        var params: ViewGroup.LayoutParams? = dialog?.window?.attributes
        val deviceWidth = size!!.x

        params?.width = (deviceWidth*0.82).toInt()

        dialog?.window?.attributes = params as WindowManager.LayoutParams
    }
}
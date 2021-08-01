package com.tipklemoa.tipkle.src

import android.content.Context
import android.graphics.Point
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tipklemoa.tipkle.config.ApplicationClass
import com.tipklemoa.tipkle.databinding.LayoutDetailBottomsheetBinding
import com.tipklemoa.tipkle.databinding.LayoutNewtipDialogBinding
import com.tipklemoa.tipkle.util.LoadingDialog

class KeywordAlertDialogFragment: DialogFragment(){
    private lateinit var binding: LayoutNewtipDialogBinding
    var windowManager: WindowManager? = null
    var display: Display? = null
    var size: Point? = null
    var what = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = LayoutNewtipDialogBinding.inflate(inflater, container, false)
        what = arguments?.getString("what").toString()

        windowManager = activity?.getSystemService(Context.WINDOW_SERVICE) as? WindowManager
        display = windowManager!!.defaultDisplay
        size = Point()
        display!!.getSize(size)

        if (what=="length"){
            binding.tvNewTipDialogInfo.text = "키워드를 2자 이상 10자 미만 작성해주세요."
        }
        else{
            binding.tvNewTipDialogInfo.text = "키워드를 5개 이상 등록할 수 없습니다."
        }

        binding.btnNewTipAlertOK.setOnClickListener {
            this.dismiss()
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        val params: ViewGroup.LayoutParams? = dialog?.window?.attributes
        val deviceWidth = size!!.x

        params?.width = (deviceWidth*0.82).toInt()

        dialog?.window?.attributes = params as WindowManager.LayoutParams
    }
}
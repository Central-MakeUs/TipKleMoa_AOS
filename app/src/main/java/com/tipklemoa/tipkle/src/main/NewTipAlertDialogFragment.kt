package com.tipklemoa.tipkle.src.main

import android.content.Context
import android.graphics.Point
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.DialogFragment
import com.tipklemoa.tipkle.databinding.LayoutNewtipDialogBinding

class NewTipAlertDialogFragment: DialogFragment(){
    private lateinit var binding: LayoutNewtipDialogBinding
    var windowManager: WindowManager? = null
    var display: Display? = null
    var size: Point? = null
    var dialogText:String?=null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = LayoutNewtipDialogBinding.inflate(inflater, container, false)
        dialogText = arguments?.getString("dialogText")
        Log.d("dialogText", dialogText.toString())

        windowManager = activity?.getSystemService(Context.WINDOW_SERVICE) as? WindowManager
        display = windowManager!!.defaultDisplay
        size = Point()
        display!!.getSize(size)

        binding.tvNewTipDialogInfo.text = dialogText

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
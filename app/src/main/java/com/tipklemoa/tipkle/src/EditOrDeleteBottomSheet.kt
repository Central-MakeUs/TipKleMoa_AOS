package com.tipklemoa.tipkle.src

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tipklemoa.tipkle.databinding.LayoutDetailBottomsheetBinding
import com.tipklemoa.tipkle.util.LoadingDialog

class EditOrDeleteBottomSheet: BottomSheetDialogFragment(){
    private lateinit var binding: LayoutDetailBottomsheetBinding
    var postId = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        postId = arguments?.getInt("postId")!!
        Log.d("postId", postId.toString())

        binding = LayoutDetailBottomsheetBinding.inflate(inflater, container, false)
        binding.constraintLayout2.setOnClickListener {
            val dialog = ReallyDeleteDialog()
            val bundle = Bundle()
            bundle.putInt("postId", postId)
            dialog.arguments = bundle
            dialog.show(parentFragmentManager, dialog.tag)
            this.dismiss()
        }

        return binding.root
    }
}
package com.tipklemoa.tipkle.src.home

import android.os.Bundle
import com.tipklemoa.tipkle.R
import com.tipklemoa.tipkle.config.BaseFragment
import com.tipklemoa.tipkle.databinding.FragmentPickedTipBinding

class PickedTipFragment : BaseFragment<FragmentPickedTipBinding>(
    FragmentPickedTipBinding::bind,
    R.layout.fragment_picked_tip
){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ViewPagerFragment를 fragmentFrame에 띄우기
        parentFragmentManager.beginTransaction().add(R.id.pickedinnerFrame, PickedTipViewPagerFragment()).commit()
    }
}
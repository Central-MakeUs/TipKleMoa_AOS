package com.tipklemoa.tipkle.src.home

import android.os.Bundle
import com.tipklemoa.tipkle.R
import com.tipklemoa.tipkle.config.BaseFragment
import com.tipklemoa.tipkle.databinding.FragmentHomeBinding
import com.tipklemoa.tipkle.src.ViewPagerFragment

class HomeFragment : BaseFragment<FragmentHomeBinding>(
    FragmentHomeBinding::bind,
    R.layout.fragment_home
){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ViewPagerFragment를 fragmentFrame에 띄우기
        childFragmentManager.beginTransaction().add(R.id.innerFrame, ViewPagerFragment()).commit()
    }
}
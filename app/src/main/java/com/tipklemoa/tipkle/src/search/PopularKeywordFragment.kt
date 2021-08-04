package com.tipklemoa.tipkle.src.search

import android.os.Bundle
import com.tipklemoa.tipkle.R
import com.tipklemoa.tipkle.config.BaseFragment
import com.tipklemoa.tipkle.databinding.FragmentPopularKeywordBinding
import com.tipklemoa.tipkle.databinding.FragmentTodayTipBinding

class PopularKeywordFragment : BaseFragment<FragmentPopularKeywordBinding>(
    FragmentPopularKeywordBinding::bind,
    R.layout.fragment_popular_keyword
){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ViewPagerFragment를 fragmentFrame에 띄우기
        parentFragmentManager.beginTransaction().add(R.id.popularFrame, PopularViewPagerFragment()).commit()
    }
}
package com.tipklemoa.tipkle.src

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.tipklemoa.tipkle.R
import com.tipklemoa.tipkle.config.BaseActivity
import com.tipklemoa.tipkle.databinding.ActivityMainBinding
import com.tipklemoa.tipkle.src.home.HomeFragment
import com.tipklemoa.tipkle.src.mypage.MyPageFragment
import com.tipklemoa.tipkle.src.search.SearchFragment
import com.tipklemoa.tipkle.src.tipkle.TipkleFragment

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initNavigationBar()
        if (intent.getIntExtra("postId", 0)!=0){
            val toFeedDetail = Intent(this, FeedDetailActivity::class.java)
            toFeedDetail.putExtra("postId", intent.getIntExtra("postId", 0))
            startActivity(toFeedDetail)
        }
    }

    private fun initNavigationBar() {

        binding.tabBottom.itemIconTintList = null

        binding.tabBottom.run {
            setOnNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.bottom_home -> {
                        changeFragment(HomeFragment())
                    }
                    R.id.bottom_tipkle -> {
                        changeFragment(TipkleFragment())
                    }
                    R.id.bottom_search -> {
                        changeFragment(SearchFragment())
                    }
                    R.id.bottom_mypage -> {
                        changeFragment(MyPageFragment())
                    }
                }
                true
            }
            selectedItemId = R.id.bottom_home
        }
    }
    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager .beginTransaction() .replace(R.id.outerFrame, fragment) .commit()
    }

//    fun changeTFragment(fragment: Fragment){
//        supportFragmentManager .beginTransaction().replace(R.id.main_frame, fragment).commit()
//    }
}
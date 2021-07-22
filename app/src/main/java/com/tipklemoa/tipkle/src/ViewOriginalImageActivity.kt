package com.tipklemoa.tipkle.src

import android.os.Bundle
import com.bumptech.glide.Glide
import com.tipklemoa.tipkle.config.BaseActivity
import com.tipklemoa.tipkle.databinding.ActivityViewOriginalImageBinding

class ViewOriginalImageActivity : BaseActivity<ActivityViewOriginalImageBinding>(
    ActivityViewOriginalImageBinding::inflate){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val image = intent.getStringExtra("image")

        Glide
            .with(this)
            .load(image)
            .into(binding.imgOriginalImage)

        binding.btnViewImageX.setOnClickListener {
            finish()
        }
    }
}
package com.eyepax.eyepax.ui.newsdetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.eyepax.eyepax.databinding.ActivityNewsDetailsBinding
import com.eyepax.eyepax.utils.*

class NewsDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        getBundleData()
        buttonAction()
    }

    private fun buttonAction() {
        binding.cardviewBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun getBundleData() {

        binding.txtTopic.text = intent.getStringExtra(BUNDLE_TITLE)
        binding.txtDate.text = intent.getStringExtra(BUNDLE_DATE)
        binding.txtAuthor.text = intent.getStringExtra(BUNDLE_AUTHOR)
        binding.txtDescription.text = intent.getStringExtra(BUNDLE_DESCRIPTION)
        Glide.with(this).load(intent.getStringExtra(BUNDLE_IMAGE)).centerCrop()
            .into(binding.imgImagen)
    }
}
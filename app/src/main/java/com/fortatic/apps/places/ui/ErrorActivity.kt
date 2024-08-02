package com.fortatic.apps.places.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fortatic.apps.places.databinding.ActivityErrorBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ErrorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityErrorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityErrorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.retryButton.setOnClickListener {
            binding.retryButton.loading = true
            restartApp()
        }
    }

    private fun restartApp() {
        val intent = packageManager.getLaunchIntentForPackage(packageName)
        intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }
}
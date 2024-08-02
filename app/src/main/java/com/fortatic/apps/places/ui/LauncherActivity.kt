package com.fortatic.apps.places.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LauncherActivity : AppCompatActivity() {

    private val launchViewModel: LaunchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val screenSplash = installSplashScreen()
        super.onCreate(savedInstanceState)
        screenSplash.setKeepOnScreenCondition { true }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launchViewModel.launchDestination.collect {
                    when (it) {
                        LaunchNavigationAction.MAIN -> navigateToMainScreen()
                        LaunchNavigationAction.FAILURE -> navigateToFailureScreen()
                    }
                }
            }
        }
    }

    private fun navigateToMainScreen() {
        val intent = Intent(this@LauncherActivity, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private fun navigateToFailureScreen() {
        val intent = Intent(this@LauncherActivity, ErrorActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}
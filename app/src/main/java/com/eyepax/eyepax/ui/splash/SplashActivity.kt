package com.eyepax.eyepax.ui.splash

import android.content.Intent
import android.os.Bundle
import com.eyepax.eyepax.databinding.ActivitySplashBinding
import com.eyepax.eyepax.ui.base.BaseActivity
import com.eyepax.eyepax.ui.login.LoginActivity
import com.eyepax.eyepax.ui.main.MainActivity
import com.eyepax.eyepax.ui.register.RegisterActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Timer
import kotlin.concurrent.schedule

class SplashActivity : BaseActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initValue(this.application)
        checkUserExist()
        observers()
    }

    private fun observers() {
        roomViewModel.getUser().observe(this) {

            Timer("splash", false).schedule(1000) {
                when {
                    it == null -> {
                        // register
                        goToRegisterActivity()
                    }
                    it.isLogged -> {
                        //dashboard
                        goToDashboardActivity()
                    }
                    else -> {
                        //login
                        goToLoginActivity()
                    }
                }
            }
        }
    }

    private fun goToLoginActivity() {
        startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
        finish()
    }

    private fun goToDashboardActivity() {
        startActivity(Intent(this@SplashActivity, MainActivity::class.java))
        finish()
    }

    private fun goToRegisterActivity() {
        startActivity(Intent(this@SplashActivity, RegisterActivity::class.java))
        finish()
    }

    private fun checkUserExist() {
        CoroutineScope(Dispatchers.IO).launch {
            roomViewModel.loadUser()
        }
    }
}
package com.eyepax.eyepax.ui.login

import android.content.Intent
import android.os.Bundle
import com.eyepax.eyepax.R
import com.eyepax.eyepax.databinding.ActivityLoginBinding
import com.eyepax.eyepax.ui.base.BaseActivity
import com.eyepax.eyepax.ui.main.MainActivity
import com.eyepax.eyepax.utils.SnackBars
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginActivity : BaseActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initValue(this.application)
        buttonAction()
        observers()
    }

    private fun observers() {
        roomViewModel.getUser().observe(this) {
            var user = it
            if (binding.textUsername.text.toString().equals(user.username) &&
                binding.textPassword.text.toString().equals(user.password)
            ) {
                user.isLogged = true
                CoroutineScope(Dispatchers.IO).launch {
                    roomViewModel.updateUser(user)
                    redirectToDashboard()
                }
            } else {
                SnackBars.error(getString(R.string.invalid_username_or_password), binding.root)
            }
        }
    }

    private fun buttonAction() {
        binding.btnLogin.setOnClickListener {
            loginUser()
        }
    }

    private fun loginUser() {
        if (binding.textUsername.text.isEmpty()) {
            SnackBars.error(getString(R.string.enter_username), binding.root)
        } else if (binding.textPassword.text.isEmpty()) {
            SnackBars.error(getString(R.string.enter_password), binding.root)
        } else {
            CoroutineScope(Dispatchers.IO).launch {
                roomViewModel.loadUser()
            }
        }
    }

    private fun redirectToDashboard() {
        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
        finish()
    }
}
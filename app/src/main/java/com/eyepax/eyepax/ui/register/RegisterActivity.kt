package com.eyepax.eyepaxtest.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.eyepax.eyepaxtest.R
import com.eyepax.eyepaxtest.data.entities.User
import com.eyepax.eyepaxtest.data.local.AppDatabase
import com.eyepax.eyepaxtest.data.repository.RoomDatabaseRepository
import com.eyepax.eyepaxtest.databinding.ActivityRegisterBinding
import com.eyepax.eyepaxtest.databinding.ActivitySplashBinding
import com.eyepax.eyepaxtest.ui.base.BaseActivity
import com.eyepax.eyepaxtest.ui.base.RoomDatabaseViewModel
import com.eyepax.eyepaxtest.ui.base.RoomDatabaseViewModelFactory
import com.eyepax.eyepaxtest.ui.login.LoginActivity
import com.eyepax.eyepaxtest.utils.SnackBars
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterActivity : BaseActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initValue(this.application)
        buttonAction()
    }

    private fun buttonAction() {
        binding.btnRegister.setOnClickListener {
            registerUser()
        }
    }

    private fun registerUser() {
        if (binding.textUsername.text.isEmpty()) {
            SnackBars.error(getString(R.string.enter_username), binding.root)
        } else if (binding.textPassword.text.isEmpty()) {
            SnackBars.error(getString(R.string.enter_password), binding.root)
        } else if (binding.textConfirmPassword.text.isEmpty()) {
            SnackBars.error(getString(R.string.enter_confirm_password), binding.root)
        } else if (binding.textConfirmPassword.text.toString() != binding.textPassword.text.toString()
        ) {
            SnackBars.error(getString(R.string.passwords_do_not_match), binding.root)
        } else if (binding.textConfirmPassword.text.toString().length < 6) {
            SnackBars.error(getString(R.string.password_too_short), binding.root)
        } else {
            SnackBars.success(binding.root)
            val user = User(
                0,
                username = binding.textUsername.text.toString(),
                password = binding.textPassword.text.toString(),
                false
            )

            saveDataInDb(user)
        }
    }

    private fun saveDataInDb(user: User) {
        CoroutineScope(Dispatchers.IO).launch {
            roomViewModel.saveUser(user)
            redirectToLogin()
        }
    }

    private fun redirectToLogin() {
        startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
        finish()
    }
}
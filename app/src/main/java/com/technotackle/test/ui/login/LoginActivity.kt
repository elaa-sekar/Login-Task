package com.technotackle.test.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.technotackle.test.databinding.ActivityLoginBinding
import com.technotackle.test.ui.login.LoginViewModel.EventHandler.*
import com.technotackle.test.ui.verify_otp.OtpVerificationActivity
import com.technotackle.test.util.ViewUtils.showMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    companion object {
        const val MOBILE_NUMBER = "mobile_number"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.viewModel = viewModel
        initEventHandler()
    }

    private fun initEventHandler() {
        lifecycleScope.launchWhenResumed {
            viewModel.eventHandler.collect {
                when (it) {
                    is NotifyMessage -> showMessage(it.message)
                    is VerifyOtp -> startOtpVerificationActivity(it.mobileNumber)
                }
            }
        }
    }

    //click Listeners
    fun onLoginClicked(view: View) {
        viewModel.validateAndLogin()
    }

    private fun startOtpVerificationActivity(mobileNumber: String) {
        Intent(this, OtpVerificationActivity::class.java).apply {
            putExtra(MOBILE_NUMBER, mobileNumber)
        }.also {
            startActivity(it)
        }
    }
}
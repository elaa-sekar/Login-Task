package com.technotackle.test.ui.verify_otp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.technotackle.test.databinding.ActivityVerifyOtpBinding
import com.technotackle.test.ui.home.HomeActivity
import com.technotackle.test.ui.verify_otp.OtpVerificationViewModel.EventHandler.LoginSuccess
import com.technotackle.test.ui.verify_otp.OtpVerificationViewModel.EventHandler.NotifyMessage
import com.technotackle.test.util.ViewUtils.showMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class OtpVerificationActivity : AppCompatActivity() {

    lateinit var binding: ActivityVerifyOtpBinding
    private val viewModel: OtpVerificationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerifyOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.viewModel = viewModel
        initOnClickListener()
        initEventHandler()
    }

    private fun initEventHandler() {
        lifecycleScope.launchWhenResumed {
            viewModel.eventHandler.collect {
                when (it) {
                    is LoginSuccess -> startHomeActivity()
                    is NotifyMessage -> showMessage(it.message)
                }
            }
        }
    }

    private fun startHomeActivity() {
        startActivity(Intent(this, HomeActivity::class.java))
    }

    private fun initOnClickListener() {
        binding.btnLogin.setOnClickListener {
            viewModel.validateAndVerifyOtp()
        }
    }

    fun onBackArrowClicked(view: View) {
        finish()
    }
}
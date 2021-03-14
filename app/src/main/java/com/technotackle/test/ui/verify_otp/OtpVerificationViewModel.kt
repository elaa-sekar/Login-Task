package com.technotackle.test.ui.verify_otp

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.technotackle.test.data.repositories.OtpVerificationRepository
import com.technotackle.test.data.session.UserSession
import com.technotackle.test.data.session.UserSession.getCustomerId
import com.technotackle.test.util.LogicUtil
import com.technotackle.test.util.LogicUtil.generateAuthToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class OtpVerificationViewModel @Inject constructor(private val repository: OtpVerificationRepository) :
    ViewModel() {

    // Coroutine Error Handler
    private val coroutineExceptionHandler: CoroutineExceptionHandler =
        CoroutineExceptionHandler { _, throwable ->
            Timber.d("Coroutine Exception - DiscussionPostViewModel: $throwable")
//            progressVisibility.set(View.GONE)
//            triggerEvent(EventHandler.ProgressBar(false))
            triggerEvent(EventHandler.NotifyMessage(throwable.message.toString()))
        }

    private var eventSender = Channel<EventHandler>()
    val eventHandler = eventSender.receiveAsFlow()

    sealed class EventHandler {
        object LoginSuccess : EventHandler()
        data class NotifyMessage(val message: String) : EventHandler()
    }

    private fun triggerEvent(event: EventHandler) {
        viewModelScope.launch {
            eventSender.send(event)
        }
    }

    //data
    var mobileNumber = ObservableField<String>()
    var otp = ObservableField<String>()

    fun validateAndVerifyOtp() {
        val otp = otp.get()
        when {
            otp.isNullOrEmpty() -> triggerEvent(
                EventHandler.NotifyMessage(
                    "Please enter otp"
                )
            )
            otp.length < 4 -> triggerEvent(
                EventHandler.NotifyMessage(
                    "Please enter 4 Digit OTP"
                )
            )
            else -> verifyOtp(otp)
        }
    }

    private fun verifyOtp(otp: String) {
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            Timber.d("Generated Token")
            val response =
                repository.verifyOtp(
                    getCustomerId(),
                    otp,
                    generateAuthToken("${getCustomerId()}$otp")
                )
            withContext(Dispatchers.Main) {
                response.apply {
                    if (success) {
                        UserSession.apply {
                            saveLoginStatus(true)
                            customerDetails?.let { saveCustomerDetails(it) }
                        }
                        triggerEvent(EventHandler.LoginSuccess)
                    }
                    triggerEvent(EventHandler.NotifyMessage(message))
                }
            }
        }
    }
}
package com.technotackle.test.ui.login

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.technotackle.test.data.repositories.LoginRepository
import com.technotackle.test.data.session.UserSession
import com.technotackle.test.util.LogicUtil
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
class LoginViewModel @Inject constructor(private val repository: LoginRepository) : ViewModel() {

    // Coroutine Error Handler
    private val coroutineExceptionHandler: CoroutineExceptionHandler =
        CoroutineExceptionHandler { _, throwable ->
            Timber.d("Coroutine Exception - LoginViewModel: $throwable")
            //            progressVisibility.set(View.GONE)
            //            triggerEvent(EventHandler.ProgressBar(false))
            triggerEvent(EventHandler.NotifyMessage(throwable.message.toString()))
        }

    private var eventSender = Channel<EventHandler>()
    val eventHandler = eventSender.receiveAsFlow()

    sealed class EventHandler {
        data class VerifyOtp(val mobileNumber: String) : EventHandler()
        data class NotifyMessage(val message: String) : EventHandler()
    }

    private fun triggerEvent(event: EventHandler) {
        viewModelScope.launch {
            eventSender.send(event)
        }
    }

    //data
    var mobileNumber = ObservableField<String>()

    fun validateAndLogin() {
        when {
            mobileNumber.get().isNullOrEmpty() -> {
                triggerEvent(EventHandler.NotifyMessage("Please enter mobile number"))
            }
            mobileNumber.get()!!.length < 10 -> {
                triggerEvent(EventHandler.NotifyMessage("Please enter 10 Digit mobile number"))
            }
            else -> requestLogin(mobileNumber.get()!!)
        }
    }

    private fun requestLogin(mobileNumber: String) {

        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            Timber.d("Generated Token ${LogicUtil.generateAuthToken(mobileNumber)}")
            val response =
                repository.requestLogin(mobileNumber, LogicUtil.generateAuthToken(mobileNumber))
            withContext(Dispatchers.Main) {
                response.apply {
                    if (success) {
                        UserSession.apply {
                            customerDetails?.let { saveCustomerDetails(it) }
                        }
                        triggerEvent(EventHandler.VerifyOtp(mobileNumber))
                    }
                    triggerEvent(EventHandler.NotifyMessage(message))
                }
            }
        }
    }
}
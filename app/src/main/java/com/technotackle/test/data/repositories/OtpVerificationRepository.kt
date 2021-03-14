package com.technotackle.test.data.repositories

import com.technotackle.test.data.network.ApiService
import com.technotackle.test.data.network.SafeApiRequest
import com.technotackle.test.data.responses.LoginResponse
import javax.inject.Inject

class OtpVerificationRepository @Inject constructor(private val apiService: ApiService) : SafeApiRequest() {

    suspend fun verifyOtp(customerId: Int, otp: String, authToken: String) : LoginResponse {
        return apiRequest { apiService.verifyOtp(customerId, otp, authToken) }
    }
}
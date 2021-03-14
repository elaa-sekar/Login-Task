package com.technotackle.test.data.repositories

import com.technotackle.test.data.network.ApiService
import com.technotackle.test.data.network.SafeApiRequest
import com.technotackle.test.data.responses.LoginResponse
import javax.inject.Inject

class LoginRepository @Inject constructor(private val apiService: ApiService) : SafeApiRequest() {

    suspend fun requestLogin(mobileNumber: String, authToken: String): LoginResponse {
        return apiRequest { apiService.login(mobileNumber, authToken) }
    }

}
package com.technotackle.test.data.network

import com.technotackle.test.data.responses.LoginResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {

    @FormUrlEncoded
    @POST("customers/login")
    suspend fun login(
        @Field("phone_number") phoneNumber: String,
        @Field("auth_token") authToken: String
    ): Response<LoginResponse>

    @FormUrlEncoded
    @POST("customers/login")
    suspend fun verifyOtp(
        @Field("customer_id") customerId: Int,
        @Field("otp") otp: String,
        @Field("auth_token") authToken: String
    ): Response<LoginResponse>



}
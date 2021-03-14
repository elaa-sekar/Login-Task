package com.technotackle.test.data.network

import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber

class HeaderInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        Timber.d("HeaderInterceptor called")
        chain.request().newBuilder().apply {
            header("Content-Type", "application/json")
            header("Accept", "application/json")
//            UserSession.getUserAuthToken().let { token ->
//                if (token.isNotEmpty()) header("Authorization", "Bearer $token")
//            }
        }.also {
            return chain.proceed(it.build())
        }
    }
}
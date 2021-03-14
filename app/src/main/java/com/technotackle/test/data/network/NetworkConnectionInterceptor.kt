package com.technotackle.test.data.network

import android.content.Context
import com.technotackle.test.util.ApiException
import com.technotackle.test.util.NetworkUtils.isInternetAvailable
import com.technotackle.test.util.NoInternetException
import okhttp3.Interceptor
import okhttp3.Response

class NetworkConnectionInterceptor(private val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        if (!isInternetAvailable(context)) {
            throw NoInternetException("Make sure you have Internet connection")
        } else {
            try {
                return chain.proceed(chain.request())
            } catch (e: Exception) {
                throw ApiException(e.message ?: e.toString())
            }
        }
    }
}
package com.technotackle.test.data.session

import com.pixplicity.easyprefs.library.Prefs
import com.technotackle.test.data.models.Customer
import com.technotackle.test.data.session.UserSession.PreferenceKeys.EMAIL
import com.technotackle.test.data.session.UserSession.PreferenceKeys.IS_LOGGED_IN
import com.technotackle.test.data.session.UserSession.PreferenceKeys.MOBILE
import com.technotackle.test.data.session.UserSession.PreferenceKeys.STATE
import com.technotackle.test.data.session.UserSession.PreferenceKeys.CUSTOMER_AUTH_TOKEN
import com.technotackle.test.data.session.UserSession.PreferenceKeys.CUSTOMER_ID
import com.technotackle.test.data.session.UserSession.PreferenceKeys.CUSTOMER_NAME

object UserSession {

    private object PreferenceKeys {
        const val CUSTOMER_AUTH_TOKEN = "auth_token"
        const val CUSTOMER_ID = "user_id"
        const val CUSTOMER_NAME = "name"
        const val MOBILE = "mobile"
        const val EMAIL = "email"
        const val STATE = "state"
        const val IS_LOGGED_IN = "is_logged"
    }

    fun saveCustomerAuthToken(CustomerAuthToken: String) = Prefs.putString(CUSTOMER_AUTH_TOKEN, CustomerAuthToken)
    fun setCustomerId(customerId: Int) = Prefs.putInt(CUSTOMER_ID, customerId)
    fun saveCustomerName(name: String) = Prefs.putString(CUSTOMER_NAME, name)
    fun saveCustomerMobile(mobile: String) = Prefs.putString(MOBILE, mobile)
    fun saveCustomerEmail(email: String) = Prefs.putString(EMAIL, email)
    fun saveState(state: String) = Prefs.putString(STATE, state)
    fun saveLoginStatus(loginStatus: Boolean) = Prefs.putBoolean(IS_LOGGED_IN, loginStatus)

    fun getCustomerAuthToken(): String = Prefs.getString(CUSTOMER_AUTH_TOKEN, "")
    fun getCustomerId(): Int = Prefs.getInt(CUSTOMER_ID, -1)
    fun getState(): String = Prefs.getString(STATE, "")
    fun getCustomerName(): String = Prefs.getString(CUSTOMER_NAME, "")
    fun getCustomerMobile(): String = Prefs.getString(MOBILE, "")
    fun getCustomerEmail(): String = Prefs.getString(EMAIL, "")
    fun getLoginStatus(): Boolean = Prefs.getBoolean(IS_LOGGED_IN, false)

    fun saveCustomerDetails(customer: Customer) {
        customer.apply {
            customerId?.let { setCustomerId(it) }
            fullName?.let { saveCustomerName(it) }
            phoneNumber?.let { saveCustomerMobile(it) }
            emailId?.let { saveCustomerEmail(it) }
            state?.let { saveState(it) }
        }
    }

    fun clear() {
        Prefs.clear().apply()
    }
}


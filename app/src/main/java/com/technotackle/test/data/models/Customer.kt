package com.technotackle.test.data.models

import com.google.gson.annotations.SerializedName

data class Customer(
    @SerializedName("customer_id") val customerId: Int?,
    @SerializedName("phone_number") val phoneNumber: String?,
    @SerializedName("otp") val otp: String?,
    @SerializedName("full_name") val fullName: String?,
    @SerializedName("gender") val gender: String?,
    @SerializedName("email_id") val emailId: String?,
    @SerializedName("address") val address: String?,
    @SerializedName("city") val city: String?,
    @SerializedName("state") val state: String?,
    @SerializedName("date_of_birth") val dob: String?,
    @SerializedName("marital_status") val maritalStatus: String?,
    @SerializedName("no_of_childern") val noOfChildren: Int?,
    @SerializedName("referal_code") val referralCode: String?,
    @SerializedName("referred_by_customer") val referredBy: Int?,
    @SerializedName("total_loyalty_points") val loyaltyPoints: Int?,
    @SerializedName("is_active") val isActive: Int?

)
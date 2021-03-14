package com.technotackle.test.data.responses

import com.google.gson.annotations.SerializedName
import com.technotackle.test.data.models.Customer

data class LoginResponse(
    @SerializedName("parameters") var customerDetails: Customer?
) : CommonResponse()
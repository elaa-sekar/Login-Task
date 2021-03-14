package com.technotackle.test.data.responses

import com.google.gson.annotations.SerializedName

open class CommonResponse(
    @SerializedName("success")
    val success: Boolean = false,
    @SerializedName("message")
    var message: String = ""
)

package com.technotackle.test.util

import java.math.BigInteger
import java.security.MessageDigest

object LogicUtil {

    private const val SALT = "P@r<@tH!@2020h&*o54ol\$%"

    fun generateAuthToken(input: String): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest("$SALT$input".toByteArray())).toString(16).padStart(32, '0')
    }
}
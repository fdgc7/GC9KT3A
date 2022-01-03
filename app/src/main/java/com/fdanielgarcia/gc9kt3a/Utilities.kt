package com.fdanielgarcia.gc9kt3a

class Utilities {
    fun uObfC(string: String) = (if (string.first().toString() == "-") kotlin.math.sqrt(string.substring(1).toLong(16).toDouble()) / "F4240".toLong(16).toInt() * -1 else kotlin.math.sqrt(string.toLong(16).toDouble()) / "F4240".toLong(16).toInt())
}
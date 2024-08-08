package com.elfak.andjelanikolic.models

data class User(
    val username: String = "",
    val email: String = "",
    val phone: String = "",
    val name: String = "",
    val avatar: String = "",
    val latitude: Float = 0f,
    val longitude: Float = 0f
)
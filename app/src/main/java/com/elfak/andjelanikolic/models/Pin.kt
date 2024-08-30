package com.elfak.andjelanikolic.models

data class Pin (
    var uid: String = "",
    var photo: String = "",
    var title: String = "",
    var description: String = "",
    var latitude: Float = 0f,
    var longitude: Float = 0f,
    var owner: String = "",
)
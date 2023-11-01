package com.example.vozdux.domain.model.drone
data class Cost(
    val value: String,
    val currency: String
) {
    constructor(): this(
        value = "",
        currency = USD_CODE
    )
}
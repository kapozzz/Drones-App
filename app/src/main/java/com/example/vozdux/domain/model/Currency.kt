package com.example.vozdux.domain.model

import java.io.Serializable

sealed class Currency(val value: String) : Serializable {
    companion object {
        const val RUB_CODE = "RUB"
        const val USD_CODE = "USD"
    }

    object RUB : Currency(RUB_CODE)
    object USD : Currency(USD_CODE)
}
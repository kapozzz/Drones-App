package com.example.vozdux.domain.model.other

sealed class  OrderType {
    object Ascending: OrderType()
    object Descending: OrderType()

    fun copy(orderType: OrderType): OrderType {
        return when(this) {
            is Ascending -> Ascending
            else -> Descending
        }
    }
}
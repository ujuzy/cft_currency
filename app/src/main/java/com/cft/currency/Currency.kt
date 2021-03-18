package com.cft.currency

import kotlinx.serialization.Serializable

@Serializable
data class Currency (
    val ID: String = "",
    val NumCode: String = "",
    val CharCode: String = "",
    val Nominal: Int? = null,
    val Name: String = "",
    val Value: Double? = null,
    val Previous: Double? = null
)
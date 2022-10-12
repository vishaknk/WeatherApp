package com.example.weatherapp.data.model

import com.example.weatherapp.extensions.ifNull

data class ConditionXWS(
    val code: Int?,
    val icon: String?,
    val text: String?
) {
    fun getConditionIcon() = "https://" + icon.ifNull().removePrefix("//")
}
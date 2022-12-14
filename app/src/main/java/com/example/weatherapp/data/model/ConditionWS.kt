package com.example.weatherapp.data.model

data class ConditionWS(
    val code: Int,
    val icon: String,
    val text: String
) {
    fun getCurrentConditionIcon() = "https://" + icon.removePrefix("//")
}
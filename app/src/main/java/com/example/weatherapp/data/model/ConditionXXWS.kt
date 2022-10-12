package com.example.weatherapp.data.model

import android.os.Parcelable
import com.example.weatherapp.extensions.ifNull
import kotlinx.parcelize.Parcelize

@Parcelize
data class ConditionXXWS(
    val code: Int?,
    val icon: String?,
    val text: String?
) : Parcelable {
    fun getHourlyConditionIcon() = "https://" + icon.ifNull().removePrefix("//")
}
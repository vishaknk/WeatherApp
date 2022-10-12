package com.example.weatherapp.data.model

data class ForecastDayWS(
    val astro: AstroWS,
    val date: String,
    val date_epoch: Int,
    val day: DayWS,
    val hour: List<HourWS>
)
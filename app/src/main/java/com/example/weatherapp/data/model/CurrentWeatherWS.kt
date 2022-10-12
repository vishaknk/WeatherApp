package com.example.weatherapp.data.model

data class CurrentWeatherWS(
    val current: CurrentWS,
    val forecast: ForecastWS,
    val location: LocationWS
)
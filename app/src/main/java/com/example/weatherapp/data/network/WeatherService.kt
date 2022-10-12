package com.example.weatherapp.data.network

import com.example.weatherapp.data.model.CurrentWeatherWS
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("forecast.json")
    suspend fun getCurrentWeatherByLocation(
        @Query("key") apiKey: String,
        @Query("q") location: String,
        @Query("days") days: Int = 10
    ): CurrentWeatherWS
}
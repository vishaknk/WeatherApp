package com.example.weatherapp.data.repository.currentweather

import android.util.Log
import com.example.weatherapp.BuildConfig
import com.example.weatherapp.data.database.CurrentWeatherDB
import com.example.weatherapp.data.database.CurrentWeatherDatabase
import com.example.weatherapp.data.network.WeatherService
import com.example.weatherapp.data.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class CurrentWeatherRepository @Inject constructor(
    private val database: CurrentWeatherDatabase,
    private val weatherService: WeatherService
) {

    val currentWeather: Flow<CurrentWeatherDB?> = database.currentWeatherDao.getCurrentWeather()

    suspend fun getCurrentWeather(location: String) {
        withContext(Dispatchers.IO) {
            Log.e("Tag","Here")
            val currentWeather = weatherService.getCurrentWeatherByLocation(BuildConfig.WEATHER_API_KEY, location)
            database.currentWeatherDao.insertCurrentWeather(currentWeather.asDatabaseModel())
        }
    }
}
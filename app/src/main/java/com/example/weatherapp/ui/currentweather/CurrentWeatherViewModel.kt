package com.example.weatherapp.ui.currentweather

import android.app.Application
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.network.asCurrentWeatherDailyForecastDomainModel
import com.example.weatherapp.data.network.asCurrentWeatherDomainModel
import com.example.weatherapp.data.repository.currentweather.CurrentWeatherRepository
import com.example.weatherapp.ui.domain.CurrentWeather
import com.example.weatherapp.ui.domain.CurrentWeatherDailyForecast
import com.example.weatherapp.ui.uistate.NetworkResult
import com.example.weatherapp.util.LOCATION_PREFERENCE_KEY
import com.example.weatherapp.util.locationDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class CurrentWeatherViewModel @Inject constructor(
    private val app: Application,
    private val currentWeatherRepository: CurrentWeatherRepository
) : AndroidViewModel(app) {

    val currentWeatherStateFlow: StateFlow<NetworkResult<CurrentWeather>>
        get() = currentWeatherMutableStateFlow

    private val currentWeatherMutableStateFlow: MutableStateFlow<NetworkResult<CurrentWeather>> = MutableStateFlow(NetworkResult.Loading())

    val dailyForecastWeatherListStateFlow: StateFlow<NetworkResult<List<CurrentWeatherDailyForecast>>>
        get() = dailyForecastWeatherListMutableStateFlow

    private val dailyForecastWeatherListMutableStateFlow: MutableStateFlow<NetworkResult<List<CurrentWeatherDailyForecast>>> =
        MutableStateFlow(NetworkResult.Loading())

    init {
        viewModelScope.launch {
            currentWeatherRepository.currentWeather
                .collect { currentWeather ->
                    if(currentWeather != null) {
                        currentWeatherMutableStateFlow.value = NetworkResult.Success(currentWeather.asCurrentWeatherDomainModel())

                        dailyForecastWeatherListMutableStateFlow.value =
                            NetworkResult.Success(currentWeather.forecast.asCurrentWeatherDailyForecastDomainModel())
                    }

                }
        }
    }

    fun refreshCurrentWeatherFromRepository(location: String) {
        viewModelScope.launch {
            try {
                currentWeatherMutableStateFlow.value = NetworkResult.Loading()
                saveLocationDataStore(location)
                currentWeatherRepository.getCurrentWeather(location)
            } catch (networkError: IOException) {
                currentWeatherMutableStateFlow.value = NetworkResult.Error("Refresh current weather from repository", networkError)
            }
        }
    }

    private suspend fun saveLocationDataStore(locationParams: String) {
        app.locationDataStore.edit { location ->
            location[LOCATION_PREFERENCE_KEY] = locationParams
        }
    }
}
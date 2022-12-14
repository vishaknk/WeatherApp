package com.example.weatherapp.data.network

import com.example.weatherapp.data.database.CurrentWeatherDB
import com.example.weatherapp.data.model.CurrentWeatherWS
import com.example.weatherapp.data.model.ForecastWS
import com.example.weatherapp.extensions.ifNull
import com.example.weatherapp.ui.domain.CurrentWeather
import com.example.weatherapp.ui.domain.CurrentWeatherDailyForecast

/**
 * DataTransferObjects go in this file. These are responsible for parsing responses from the server
 * or formatting objects to send to the server. You should convert these to domain objects before
 * using them.
 */

/**
 * Convert database results CurrentWeatherDB to CurrentWeather UI domain object
 */
fun CurrentWeatherDB.asCurrentWeatherDomainModel(): CurrentWeather {
    return CurrentWeather(
        name = location.name.ifNull(),
        region = location.region.ifNull(),
        country = location.country.ifNull(),
        currentDayTime = location.localtime.ifNull(),
        temp_c = current.temp_c.ifNull(),
        temp_f = current.temp_f.ifNull(),
        feelslike_c = current.feelslike_c.ifNull(),
        feelslike_f = current.feelslike_f.ifNull(),
        wind_mph = current.wind_mph.ifNull(),
        wind_kph = current.wind_kph.ifNull(),
        humidity = current.humidity.ifNull(),
        conditionText = current.condition?.text.ifNull(),
        conditionIcon = current.condition?.getCurrentConditionIcon().ifNull()
    )
}

/**
 * Convert CurrentWeatherDB to CurrentWeatherDailyForecast UI domain object
 */
fun ForecastWS.asCurrentWeatherDailyForecastDomainModel(): List<CurrentWeatherDailyForecast> {
    return forecastday.map {
        CurrentWeatherDailyForecast(
            day = it.date,
            monthDay = it.date,
            conditionText = it.day.condition.text.ifNull(),
            conditionIcon = it.day.condition.getConditionIcon(),
            maxtemp_c = it.day.maxtemp_c,
            maxtemp_f = it.day.maxtemp_f,
            mintemp_c = it.day.maxtemp_c,
            mintemp_f = it.day.maxtemp_f,
            astro = it.astro,
            hourList = it.hour
        )
    }
}

/**
 * Convert Network results CurrentWeatherWS to database objects CurrentWeatherDB
 */
fun CurrentWeatherWS.asDatabaseModel(): CurrentWeatherDB {
    return CurrentWeatherDB(
        current = current,
        forecast = forecast,
        location = location
    )
}
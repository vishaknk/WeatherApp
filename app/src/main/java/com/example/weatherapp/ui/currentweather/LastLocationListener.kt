package com.example.weatherapp.ui.currentweather

import android.location.Location

interface LastLocationListener {
    fun onLastLocationFound(location: Location)
    fun onCheckPermissonFailed()
}
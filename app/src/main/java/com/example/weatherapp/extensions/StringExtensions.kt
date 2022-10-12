package com.example.weatherapp.extensions

fun String?.ifNull(): String {
    return this ?: ""
}
package com.example.weatherapp.extensions

import kotlin.Int.Companion.MIN_VALUE

fun Int?.ifNull(): Int {
    return this ?: MIN_VALUE
}
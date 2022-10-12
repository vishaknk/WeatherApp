package com.example.weatherapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherapp.databinding.ActivityMyWeatherBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyWeatherActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyWeatherBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyWeatherBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}
package com.example.weatherapp.ui.dailyforecast

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherapp.R
import com.example.weatherapp.data.model.HourWS
import com.example.weatherapp.databinding.HourlyForecastItemBinding
import com.example.weatherapp.util.getTimeFromDateString
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

@FragmentScoped
class HourlyForecastAdapter @Inject constructor() : RecyclerView.Adapter<HourlyForecastViewHolder>() {
    var currentHourlyForecastList: List<HourWS> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyForecastViewHolder {
        val itemBinding = HourlyForecastItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HourlyForecastViewHolder(itemBinding)
    }

    override fun getItemCount() = currentHourlyForecastList.size

    override fun onBindViewHolder(holder: HourlyForecastViewHolder, position: Int) {
        val paymentBean: HourWS = currentHourlyForecastList[position]
        holder.bind(paymentBean)
    }
}

class HourlyForecastViewHolder(private val itemBinding: HourlyForecastItemBinding) :
    RecyclerView.ViewHolder(itemBinding.root) {
    fun bind(hourlyForecast: HourWS) {
        itemBinding.apply {
            tvHourlyTime.text = hourlyForecast.time?.let { getTimeFromDateString(it) }
            Glide.with(ivHourlyConditionIcon.context).load(hourlyForecast.condition?.getHourlyConditionIcon()).into(ivHourlyConditionIcon)
            tvHourlyWeather.text = tvHourlyWeather.context.getString(R.string.current_weather_temp, hourlyForecast.temp_f.toString())
            tvHourlyCondition.text = hourlyForecast.condition?.text
            tvHourlyHumidityValue.text = hourlyForecast.humidity.toString()
            tvHourlyWindValue.text = tvHourlyWindValue.context.getString(R.string.hour_wind_value, hourlyForecast.wind_mph.toString())
            tvHourlyTempFeelLike.text = tvHourlyTempFeelLike.context.getString(R.string.current_weather_temp, hourlyForecast.feelslike_f.toString())
        }
    }
}
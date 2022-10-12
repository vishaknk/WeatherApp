package com.example.weatherapp.ui.currentweather

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherapp.databinding.DailyForecastItemBinding
import com.example.weatherapp.ui.domain.CurrentWeatherDailyForecast
import com.example.weatherapp.util.getDayNameFromDateString
import com.example.weatherapp.util.getMonthDayFromDateString
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

@FragmentScoped
class DailyForecastAdapter @Inject constructor() : RecyclerView.Adapter<DailyForecastViewHolder>() {

    private var onDailyForecastItemClickLister: ((CurrentWeatherDailyForecast) -> Unit)? = null
    fun setOnDailyForecastItemClickLister(listener: (CurrentWeatherDailyForecast) -> Unit) {
        onDailyForecastItemClickLister = listener
    }

    var currentWeatherDailyForecastList: List<CurrentWeatherDailyForecast> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyForecastViewHolder {
        val itemBinding = DailyForecastItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DailyForecastViewHolder(itemBinding)
    }

    override fun getItemCount() = currentWeatherDailyForecastList.size

    override fun onBindViewHolder(holder: DailyForecastViewHolder, position: Int) {
        val paymentBean: CurrentWeatherDailyForecast = currentWeatherDailyForecastList[position]
        holder.bind(paymentBean, onDailyForecastItemClickLister)
    }
}

class DailyForecastViewHolder(private val itemBinding: DailyForecastItemBinding) :
    RecyclerView.ViewHolder(itemBinding.root) {
    fun bind(currentWeatherDailyForecast: CurrentWeatherDailyForecast, onDailyForecastItemClickLister: ((CurrentWeatherDailyForecast) -> Unit)?) {
        itemBinding.apply {
            clDailyForecast.setOnClickListener {
                onDailyForecastItemClickLister?.let {
                    it(currentWeatherDailyForecast)
                }
            }
            tvDay.setText("${currentWeatherDailyForecast.day}")
            tvDate.text = getDayNameFromDateString(currentWeatherDailyForecast.day)

            //tvDate.text = getMonthDayFromDateString(currentWeatherDailyForecast.monthDay)
            tvCondition.text = currentWeatherDailyForecast.conditionText
            Glide.with(ivConditionIcon.context).load(currentWeatherDailyForecast.conditionIcon).into(ivConditionIcon)
            tvTempMax.setText("Max - ${currentWeatherDailyForecast.maxtemp_f.toString()}")
            tvTempMin.setText("Min - ${currentWeatherDailyForecast.mintemp_f.toString()}")
        }
    }
}
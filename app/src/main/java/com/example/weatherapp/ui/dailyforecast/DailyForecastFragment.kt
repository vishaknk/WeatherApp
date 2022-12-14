package com.example.weatherapp.ui.dailyforecast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentDailyForecastBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DailyForecastFragment : Fragment() {

    private var _binding: FragmentDailyForecastBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var hourlyAdapter: HourlyForecastAdapter

    private val args: DailyForecastFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDailyForecastBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        val currentWeatherDailyForecast = args.currentWeatherDailyForecast
        val locationName = args.currentLocationName
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.hour_forecast, locationName)

        if (currentWeatherDailyForecast.hourList.isNotEmpty()) {
            binding.apply {
                rvHourlyForecast.apply {
                    addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
                    layoutManager = LinearLayoutManager(context)
                    adapter = hourlyAdapter
                }
                hourlyAdapter?.currentHourlyForecastList = currentWeatherDailyForecast.hourList

                Glide.with(ivMoon).load(R.drawable.ic_moon).into(ivMoon)
                Glide.with(ivSun).load(R.drawable.ic_sun).into(ivSun)

                tvSunRise.text = getString(R.string.sun_rise, currentWeatherDailyForecast.astro.sunrise)
                tvSunSet.text = getString(R.string.sun_set, currentWeatherDailyForecast.astro.sunset)

                tvMoonRise.text = getString(R.string.moon_rise, currentWeatherDailyForecast.astro.moonrise)
                tvMoonSet.text = getString(R.string.moon_set, currentWeatherDailyForecast.astro.moonset)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
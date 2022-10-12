package com.example.weatherapp

import android.app.Application
import androidx.datastore.preferences.core.emptyPreferences
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.Configuration.Provider
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.weatherapp.util.LOCATION_KEY
import com.example.weatherapp.util.LOCATION_PREFERENCE_KEY
import com.example.weatherapp.util.locationDataStore
import com.example.weatherapp.work.RefreshWeatherDataWorker
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject


@HiltAndroidApp
class MyWeatherApplication : Application(), Provider {
    private val applicationScope = CoroutineScope(Dispatchers.Default)

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        delayedInit()
    }


    private fun setupRecurringWork() {
        val dataStoreLocation = applicationContext.locationDataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    Timber.d("Error reading preferences: ")
                    emit(emptyPreferences())
                } else {
                    Timber.d("Error reading preferences: ")
                    throw exception
                }
            }.map { preferences ->
                preferences[LOCATION_PREFERENCE_KEY] ?: ""
            }

        applicationScope.launch {
            if (dataStoreLocation.first().isEmpty()) {
                Timber.d("Datastore preferences[LOCATION_PREFERENCE_KEY] is null or empty")
            } else {
                val constraints = Constraints.Builder()
                    .setRequiresBatteryNotLow(true)
                    .build()

                val inputLocationData = Data.Builder()
                    .putString(LOCATION_KEY, dataStoreLocation.first())
                    .build()

                val repeatingRequest = OneTimeWorkRequestBuilder<RefreshWeatherDataWorker>()
                    .setInputData(inputLocationData)
                    .setConstraints(constraints)
                    .build()

                Timber.d("Periodic Work request for sync is scheduled")
                WorkManager.getInstance(applicationContext).enqueue(
                    repeatingRequest
                )
            }
        }
    }

    private fun delayedInit() {
        applicationScope.launch {
            Timber.plant(Timber.DebugTree())
            setupRecurringWork()
        }
    }

    override fun getWorkManagerConfiguration() =
        Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .setMinimumLoggingLevel(android.util.Log.DEBUG)
            .build()
}
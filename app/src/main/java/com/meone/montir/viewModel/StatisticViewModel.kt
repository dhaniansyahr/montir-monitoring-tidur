package com.meone.montir.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.github.mikephil.charting.data.BarEntry
import com.meone.montir.data.repository.SleepDataRepository
import com.meone.montir.data.repository.UserRepository
import com.meone.montir.response.GetSleepDur
import com.meone.montir.response.GetSleepDurResponse
import com.meone.montir.response.LoginResponse
import com.meone.montir.service.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class StatisticViewModel(private val repository: UserRepository): ViewModel() {
//    private val repository = SleepDataRepository()
    private val _barChartData = MutableLiveData<List<BarEntry>>()
    val barChartData: LiveData<List<BarEntry>> = _barChartData

    private val _value = MutableLiveData<List<GetSleepDur>>()
    val value: LiveData<List<GetSleepDur>> = _value

    private val _valueAverage = MutableLiveData<List<GetSleepDur>>()
    val valueAverage: LiveData<List<GetSleepDur>> = _valueAverage

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    // Initialize with empty data
    init {
//        _barChartData.value = emptyList()
        DefaultChartData("2024-05-01", "2024-05-07")
    }

    //get Average sleep score, sleep duration, BMI, Sleep Time
    fun getAverageData(){
        _isLoading.value = true
        viewModelScope.launch {
            repository.getToken().asLiveData().observeForever{
                if (it != null){
                    val bearerToken = "Bearer $it"
                    val client = ApiConfig.getApiService().getAverageData("Bearer $it")

                    Log.d("get average", "Token: $bearerToken")
                    Log.d("get average", "Request URL: ${client.request().url}")

                    client.enqueue(object : Callback<GetSleepDurResponse>{
                        override fun onResponse(
                            call: Call<GetSleepDurResponse>,
                            response: Response<GetSleepDurResponse>
                        ) {
                            _isLoading.value = false
                            if (response.isSuccessful) {
                                _valueAverage.value = response.body()?.data
                                Log.e("get average", "onSucces ${response.body()}")
                            }else {
                                Log.e(
                                    "get average",
                                    "onFailure Response ${it} ${response.code()} - ${response.message()} - ${
                                        response.errorBody()?.string()
                                    }"
                                )
                            }
                        }

                        override fun onFailure(call: Call<GetSleepDurResponse>, t: Throwable) {
                            _isLoading.value = false
                            Log.e("get average unreachable", "onFailure ${t.message}")
                        }

                    })
                }
            }
        }
    }

    // Function to update chart data based on selected date range
    fun updateChartData(startDate: String, endDate: String) {
        _isLoading.value = true

        viewModelScope.launch {
            repository.getToken().asLiveData().observeForever {
                if (it != null) {
                    val client = ApiConfig.getApiService()
                        .getSleepDuration("Bearer ${it}", startDate, endDate)

                    client.enqueue(object : Callback<GetSleepDurResponse> {
                        override fun onResponse(
                            call: Call<GetSleepDurResponse>,
                            response: Response<GetSleepDurResponse>
                        ) {
                            _isLoading.value = false
                            if (response.isSuccessful) {
                                _value.value = response.body()?.data
                                Log.e("get sleepdur", "onSucces ${response.body()}")
                            } else {
                                DefaultChartData("2024-05-01", "2024-05-07")
                                Log.e(
                                    "get sleepdur",
                                    "onFailure Response ${it} ${response.code()} - ${response.message()} - ${
                                        response.errorBody()?.string()
                                    }"
                                )
                            }
                        }

                        override fun onFailure(call: Call<GetSleepDurResponse>, t: Throwable) {
                            _isLoading.value = false
                            Log.e("get sleepdur unreachable", "onFailure ${t.message}")
                        }
                    })
                }
            }

            _value.observeForever { getSleepDurList ->
                if (getSleepDurList != null) {
                    val barEntries = getSleepDurList.map { sleepDur ->
                        val date = SimpleDateFormat(
                            "yyyy-MM-dd",
                            Locale.getDefault()
                        ).parse(sleepDur.date.toString())?.time?.toFloat() ?: 0f
                        BarEntry(date, sleepDur.sleep_duration.toFloat())
                    }
                    _barChartData.postValue(barEntries)
                }
            }
        }
    }

    fun DefaultChartData(startDate: String, endDate: String){
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val calendar = Calendar.getInstance().apply {
            time = dateFormat.parse(startDate) ?: Date()
        }
        val endCalendar = Calendar.getInstance().apply {
            time = dateFormat.parse(endDate) ?: Date()
        }

        val data = mutableListOf<BarEntry>()

        while (!calendar.after(endCalendar)) {
            // Example: Get dummy sleep duration (replace with actual data retrieval logic)
            val sleepDuration = getRandomSleepDuration()
            val barEntry = BarEntry(calendar.timeInMillis.toFloat(), sleepDuration.toFloat())
            data.add(barEntry)
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }

        _barChartData.value = data
    }

    // Example function to generate random sleep duration for demonstration
    private fun getRandomSleepDuration(): Float {
        return (0.toFloat()) // Random sleep duration between 4 to 9 hours
    }
}
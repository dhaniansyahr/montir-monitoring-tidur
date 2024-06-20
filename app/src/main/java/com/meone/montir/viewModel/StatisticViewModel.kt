package com.meone.montir.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.github.mikephil.charting.data.BarEntry
import com.meone.montir.data.repository.SleepDataRepository
import com.meone.montir.data.repository.UserRepository
import com.meone.montir.response.GetSleepDur
import com.meone.montir.response.LoginResponse
import com.meone.montir.service.ApiConfig
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

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    // Initialize with empty data
    init {
//        _barChartData.value = emptyList()
        updateChartData("2024-05-01", "2024-05-07")
    }

    // Function to update chart data based on selected date range
    fun updateChartData(startDate: String, endDate: String) {
        _isLoading.value = true

//        repository.getSession().asLiveData().observeForever{
//            if (it != null){
//                val client = ApiConfig.getApiService().getSleepDuration(startDate, endDate)
//
//                client.enqueue(object : Callback<List<GetSleepDur>> {
//                    override fun onResponse(
//                        call: Call<List<GetSleepDur>>,
//                        response: Response<List<GetSleepDur>>
//                    ) {
//                        _isLoading.value = false
//                        if (response.isSuccessful){
//                            _value.value = response.body()
//                        }else{
//                            Log.e("get sleepdur", "onFailure ${response.message()}")
//                        }
//                    }
//
//                    override fun onFailure(call: Call<List<GetSleepDur>>, t: Throwable) {
//                        TODO("Not yet implemented")
//                        _isLoading.value = false
//                        Log.e("get sleepdur", "onFailure ${t.message.toString()}")
//                    }
//                })
//            }
//        }

//        repository.fetchSleepData(startDate, endDate) { data ->
//            val barEntries = data.map { GetSleepDur ->
//                val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(GetSleepDur.date.toString())?.time?.toFloat() ?: 0f
//                BarEntry(date, GetSleepDur.sleep_duration.toFloat())
//            }
//            _barChartData.postValue(barEntries)
//        }
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val calendar = Calendar.getInstance().apply {
            time = dateFormat.parse(startDate) ?: Date()
        }
        val endCalendar = Calendar.getInstance().apply {
            time = dateFormat.parse(endDate) ?: Date()
        }

        val data = mutableListOf<BarEntry>()
        var index = calendar.time

        while (!calendar.after(endCalendar)) {
            // Example: Get dummy sleep duration (replace with actual data retrieval logic)
            val sleepDuration = getRandomSleepDuration()
            val barEntry = BarEntry(calendar.timeInMillis.toFloat(), sleepDuration.toFloat())
            data.add(barEntry)
            calendar.add(Calendar.DAY_OF_MONTH, 1)
//            index++
        }

        _barChartData.value = data
    }

    // Example function to generate random sleep duration for demonstration
    private fun getRandomSleepDuration(): Float {
        return (4..9).random().toFloat() // Random sleep duration between 4 to 9 hours
    }
}
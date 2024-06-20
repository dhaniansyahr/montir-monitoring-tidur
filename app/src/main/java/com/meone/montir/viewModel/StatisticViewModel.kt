package com.meone.montir.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.mikephil.charting.data.BarEntry
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class StatisticViewModel: ViewModel() {
    private val _barChartData = MutableLiveData<List<BarEntry>>()
    val barChartData: LiveData<List<BarEntry>> = _barChartData

    // Initialize with empty data
    init {
        _barChartData.value = emptyList()
    }

    // Function to update chart data based on selected date range
    fun updateChartData(startDate: String, endDate: String) {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val calendar = Calendar.getInstance().apply {
            time = dateFormat.parse(startDate) ?: Date()
        }
        val endCalendar = Calendar.getInstance().apply {
            time = dateFormat.parse(endDate) ?: Date()
        }

        val data = mutableListOf<BarEntry>()

        while (calendar <= endCalendar) {
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
        return (4..9).random().toFloat() // Random sleep duration between 4 to 9 hours
    }
}
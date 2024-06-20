package com.meone.montir.view.statistic

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.github.mikephil.charting.data.Entry
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.meone.montir.R
import com.meone.montir.databinding.ActivityStatisticBinding
import com.meone.montir.view.music.MusicActivity
import com.meone.montir.view.profile.ProfileActivity
import com.meone.montir.view.sleep.SleepTrackerActivity
import java.util.Calendar


class StatisticActivity : AppCompatActivity(), OnChartValueSelectedListener {

    private lateinit var binding: ActivityStatisticBinding
    private lateinit var chart: BarChart

    //date
    private val TAG = "StatisticActivity"
    private lateinit var mClickDateStart: CardView
    private lateinit var mDisplayDateStart: TextView
    private lateinit var mDateStartSetListener: DatePickerDialog.OnDateSetListener
    private lateinit var mClickDateEnd: CardView
    private lateinit var mDisplayDateEnd: TextView
    private lateinit var mDateEndSetListener: DatePickerDialog.OnDateSetListener

    @SuppressLint("DefaultLocale")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStatisticBinding.inflate(layoutInflater)

        setContentView(binding.root)

        //initialize chart
        chart = findViewById(R.id.chart1)
        //set the value selected
        chart.setOnChartValueSelectedListener(this)

        //setup chart data
        val data = dataValue1()
        val barDataSet1 = BarDataSet(data, "Data Set 1")
        val dataSets = ArrayList<IBarDataSet>()
        dataSets.add(barDataSet1)

        val xAxis: XAxis = chart.xAxis
        xAxis.valueFormatter = MyAxisValueFormatter()


        chart.setNoDataText("Not Enough Record Yet for this week")
        val description = Description()
        description.text = "Sleep Durations"
        chart.description = description


        val barData = BarData(dataSets)
        chart.data = barData
        chart.invalidate()

        binding.apply {
            sleepscoreBtn.setOnClickListener {
                startActivity(Intent(this@StatisticActivity, SleepScoreActivity::class.java))
            }
            musicButton.setOnClickListener {
                startActivity(Intent(this@StatisticActivity, MusicActivity::class.java))
            }
            statsButton.setOnClickListener{
                startActivity(Intent(this@StatisticActivity, StatisticActivity::class.java))
            }
            profileButton.setOnClickListener{
                startActivity(Intent(this@StatisticActivity, ProfileActivity::class.java))
            }
            sleepButton.setOnClickListener{
                startActivity(Intent(this@StatisticActivity, SleepTrackerActivity::class.java))
            }
            //date
            mClickDateStart = findViewById(R.id.tglawalCard)
            mClickDateEnd = findViewById(R.id.tglakhirCard)
            mDisplayDateStart = findViewById(R.id.tglInput)
            mDisplayDateEnd = findViewById(R.id.tglakhirInput)

            mClickDateStart.setOnClickListener({
                val cal = Calendar.getInstance()
                val year = cal.get(Calendar.YEAR)
                val month = cal.get(Calendar.MONTH)
                val day = cal.get(Calendar.DAY_OF_MONTH)

                val dialog = DatePickerDialog(
                    this@StatisticActivity,
                    android.R.style.Theme_Holo_Dialog_MinWidth,
                    mDateStartSetListener,
                    year,
                    month,
                    day
                )
                dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog.show()
            })

            mClickDateEnd.setOnClickListener({
                val cal = Calendar.getInstance()
                val year = cal.get(Calendar.YEAR)
                val month = cal.get(Calendar.MONTH)
                val day = cal.get(Calendar.DAY_OF_MONTH)

                val dialog = DatePickerDialog(
                    this@StatisticActivity,
                    android.R.style.Theme_Holo_Dialog_MinWidth,
                    mDateEndSetListener,
                    year,
                    month,
                    day
                )
                dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog.show()
            })

            mDateStartSetListener = DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
                val adjustedMonth = month + 1 // Adjust month since Calendar.MONTH is zero-based
                Log.d(TAG, "onDataSet: dd/mm/yyyy: $day-$adjustedMonth-$year")
                val date = "$day/$adjustedMonth/$year"
                mDisplayDateStart.text = date // Assuming mDisplayDateStart is a TextView
            }

            mDateEndSetListener = DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
                val adjustedMonth = month + 1 // Adjust month since Calendar.MONTH is zero-based
                Log.d(TAG, "onDataSet: dd/mm/yyyy: $day-$adjustedMonth-$year")
                val date = "$day/$adjustedMonth/$year"
                mDisplayDateEnd.text = date // Assuming mDisplayDateStart is a TextView
            }

        }
    }

    private fun  dataValue1():ArrayList<BarEntry>
    {
        val dataVals = ArrayList<BarEntry>()
        dataVals.add(BarEntry(0f,20f))
        dataVals.add(BarEntry(1f,10f))
        dataVals.add(BarEntry(2f,30f))
        dataVals.add(BarEntry(3f,50f))
        dataVals.add(BarEntry(4f,70f))
        dataVals.add(BarEntry(5f,65f))
        dataVals.add(BarEntry(6f,100f))
        return dataVals
    }

    private fun MyAxisValueFormatter() : ValueFormatter
    {
        return object  : ValueFormatter(){
            override fun getFormattedValue(value : Float, axis: AxisBase): String{
                return "Day $value"
            }
        }
    }

    override fun onValueSelected(e: Entry?, h: Highlight?) {
        // Handle value selected
    }

    override fun onNothingSelected() {
        // Handle nothing selected
    }


}
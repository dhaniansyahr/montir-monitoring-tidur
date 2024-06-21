package com.meone.montir.view.statistic

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.viewModels
import com.github.mikephil.charting.data.Entry
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
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
import com.meone.montir.viewModel.StatisticViewModel
import com.meone.montir.viewModel.ViewModelFactory
import com.meone.montir.viewModel.sleep.SleepTrackerViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class StatisticActivity : AppCompatActivity(), OnChartValueSelectedListener {

    private lateinit var binding: ActivityStatisticBinding
    private lateinit var chart: LineChart
    private val BASE_URL = "https://montir-cvzxsttw2a-et.a.run.app/"
    private val TAG:String = "STATISTICACTIVITY"

    //date
    private lateinit var mClickDateStart: CardView
    private lateinit var mDisplayDateStart: TextView
    private lateinit var mDateStartSetListener: DatePickerDialog.OnDateSetListener
    private lateinit var mClickDateEnd: CardView
    private lateinit var mDisplayDateEnd: TextView
    private lateinit var mDateEndSetListener: DatePickerDialog.OnDateSetListener
    private lateinit var mSleepScore: TextView
    private lateinit var mSleepDur: TextView
    private lateinit var mScoreBMI: TextView
    private lateinit var mStress: TextView

    private val viewModel by viewModels<StatisticViewModel> {
        ViewModelFactory.getInstance(this)
    }
    @SuppressLint("DefaultLocale")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStatisticBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val getSleepScore = intent.getStringExtra("QUALITY_SCORE")
        val getSleepDur = intent.getStringExtra("SLEEP_DURATION")
        val getWake = intent.getStringExtra("WAKE_UP_TIME")

        mSleepScore = findViewById(R.id.AverageSleepScore)
        mSleepDur = findViewById(R.id.AverageSleepDuration)
        mScoreBMI = findViewById(R.id.AverageSleepTime)
        mStress = findViewById(R.id.AverageStressLevel)

        var aveSleepScore:String = ""
        var aveSleepDur:String = ""
        var scoreBMI:String = ""
        var aveStress:String = ""

        viewModel.getAverageData()
        viewModel.valueAverage.observeForever{ averageList ->
            var sleepScoreTotal:Float = 0F
            var sleepDurTotal:Float = 0F
            var stressTotal:Float = 0F


            if (averageList != null) { averageList.map { vals ->
                    sleepScoreTotal = vals.quality_score + sleepScoreTotal
                    sleepDurTotal = vals.sleep_duration + sleepDurTotal
                    stressTotal = vals.stress_level + stressTotal
//                    Log.d("ISIAN AVERAGE SLEEP SCORE", vals.quality_score.toString())
                }
                aveSleepScore = String.format("%.2f",(sleepScoreTotal/averageList.size) )
                aveSleepDur = String.format("%.2f", (sleepDurTotal/averageList.size))
                scoreBMI = averageList[0].bmi.toString()
                aveStress = String.format("%.2f", (stressTotal/averageList.size))
            }
            mSleepScore.text = aveSleepScore
            mSleepDur.text = aveSleepDur
            mStress.text = aveStress
            mScoreBMI.text = scoreBMI

        }

        //initialize chart
        chart = findViewById(R.id.chart1)

        setupChart()

        viewModel.barChartData.observe(this, Observer { data ->
            if (data.isEmpty()) {
                val dataSet = LineDataSet(data, "Sleep Duration")
                dataSet.color = Color.BLUE // Example color customization
                val barData = LineData(dataSet)
                chart.data = barData
                chart.invalidate() // Refresh the chart
            } else {
                updateBarChart(data)
            }
        })

        binding.apply {
            sleepscoreBtn.setOnClickListener {
//                startActivity(Intent(this@StatisticActivity, SleepScoreActivity::class.java))
                val intent = Intent(this@StatisticActivity, SleepScoreActivity::class.java)
                if (!getSleepScore.isNullOrBlank() and !getSleepScore.isNullOrBlank() and !getWake.isNullOrBlank()){
                    intent.apply {
                        putExtra("SLEEPSCORE", getSleepScore.toString())
                        putExtra("SLEEPDUR", getSleepDur.toString())
                        putExtra("WAKE_UP", getWake.toString())
                    }
                }
                startActivity(intent)
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
                Log.d(TAG, mDisplayDateEnd.text.toString())
            })

            mDateStartSetListener = DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
                val adjustedMonth = month + 1 // Adjust month since Calendar.MONTH is zero-based
                Log.d(TAG, "onDataSet: yyyy/mm/dd: $year-$adjustedMonth-$day")
                val date = "$year-$adjustedMonth-$day"
                mDisplayDateStart.text = date // Assuming mDisplayDateStart is a TextView
            }

            mDateEndSetListener = DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
                val adjustedMonth = month + 1 // Adjust month since Calendar.MONTH is zero-based
                Log.d(TAG, "onDataSet: yyyy/mm/dd: $year-$adjustedMonth-$day")
                val date = "$year-$adjustedMonth-$day"
                mDisplayDateEnd.text = date // Assuming mDisplayDateStart is a TextView
                viewModel.updateChartData(mDisplayDateStart.text.toString(), mDisplayDateEnd.text.toString())
            }

        }
    }

    private fun setupChart(){
        chart.description.isEnabled = false
        chart.setDrawGridBackground(false)
        chart.setPinchZoom(true)
        chart.setScaleEnabled(true)
        chart.setDragEnabled(true)
        chart.setVisibleXRangeMaximum(7f)  // Show 7 entries at a time

        // Customize xAxis
        val xAxis = chart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.valueFormatter = DateAxisValueFormatter() // Custom formatter for date display
        xAxis.granularity = 1f // One day interval
    }

    private fun updateBarChart(data: List<BarEntry>) {
        val dataSet = LineDataSet(data, "Sleep Duration")
        dataSet.color = Color.BLUE // Example color customization

        val barData = LineData(dataSet)
        chart.data = barData
        chart.invalidate() // Refresh the chart
    }

    // Custom ValueFormatter for xAxis to display dates
    private inner class DateAxisValueFormatter : ValueFormatter() {
        private val dateFormat = SimpleDateFormat("MM/dd", Locale.getDefault())

        override fun getFormattedValue(value: Float): String {
            return dateFormat.format(value.toLong())
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


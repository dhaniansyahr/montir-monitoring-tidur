package com.meone.montir.view.component

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.ArrayAdapter
import androidx.annotation.NonNull
import com.meone.montir.databinding.SleepDialogBinding
import com.meone.montir.view.sleep.StopTrackerActivity

class StressDialog(@NonNull context: Context, adapter: ArrayAdapter<String>, duration: Long) : Dialog(context) {

    private val binding: SleepDialogBinding = SleepDialogBinding.inflate(LayoutInflater.from(context))
    var stressInput: Int = 0
    init {
        window?.let {
            val params: WindowManager.LayoutParams = it.attributes
            params.gravity = Gravity.CENTER
            it.attributes = params
            it.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }

        setTitle(null)
        setCancelable(false)
        setOnCancelListener(null)

        setContentView(binding.root)

        binding.apply {
            stressSelect.setAdapter(adapter)

            stressSelect.setOnItemClickListener { adapterView, view, i, l ->
                val selectedItem = adapterView.getItemAtPosition(i).toString()
                stressInput = selectedItem.toInt()
            }
        }


        binding.button2.setOnClickListener {
            val intent = Intent(context, StopTrackerActivity::class.java)
            intent.putExtra("duration", duration)
            intent.putExtra("stress", stressInput)
            context.startActivity(intent)
            dismiss()
        }
    }

}



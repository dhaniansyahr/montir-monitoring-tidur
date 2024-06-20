package com.meone.montir.view.component

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.annotation.NonNull
import com.meone.montir.databinding.SleepDialogBinding

class StressDialog(@NonNull context: Context) : Dialog(context) {

    private val binding: SleepDialogBinding = SleepDialogBinding.inflate(LayoutInflater.from(context))

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

        binding.button2.setOnClickListener {
            dismiss()
        }
    }

}



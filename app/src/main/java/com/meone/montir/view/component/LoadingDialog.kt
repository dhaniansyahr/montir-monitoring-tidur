package com.meone.montir.view.component

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.annotation.NonNull
import com.meone.montir.R

class LoadingDialog(@NonNull context: Context) : Dialog(context) {

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

        val view: View = LayoutInflater.from(context).inflate(R.layout.loading_layout, null)
        setContentView(view)
    }

}



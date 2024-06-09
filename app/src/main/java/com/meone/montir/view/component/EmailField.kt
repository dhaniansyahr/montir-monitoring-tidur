package com.meone.montir.view.component

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import java.util.regex.Pattern

class EmailField: AppCompatEditText {
    private var errorMessage: String = "Invalid email address"
    private var errorShown: Boolean = false

    constructor(context: Context) : super(context) {
        setup()
    }
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        setup()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        setup()
    }

    private fun setup() {
        this.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (!isValidEmail(s.toString())) {
                    showError()
                } else {
                    hideError()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }

    private fun isValidEmail(email: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        val pattern = Pattern.compile(emailPattern)
        return pattern.matcher(email).matches()
    }

    private fun showError() {
        if (!errorShown) {
            this.error = errorMessage
            errorShown = true
        }
    }

    private fun hideError() {
        if (errorShown) {
            this.error = null
            errorShown = false
        }
    }
}
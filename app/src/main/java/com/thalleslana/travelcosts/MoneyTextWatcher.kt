package com.thalleslana.travelcosts

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText


class MoneyTextWatcher(private val input: EditText) : TextWatcher {
    private var temporaryText: String? = null
    override fun beforeTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        // Not in here
    }
    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        temporaryText = s.toString()

        // Return the position by first char specified if null return -1
        val decimalPosition = temporaryText!!.indexOf(".")

        if (decimalPosition == -1) {
            // If there's no decimal (has been deleted)
            temporaryText = if (temporaryText!!.length < 3) {
                // Keep pattern of x.xx
                val template = "0.00"
                template.substring(0, template.length - temporaryText!!.length) + temporaryText
            } else {
                // String length > 2, add decimal
                temporaryText!!.substring(0, temporaryText!!.length - 2) + "." + temporaryText!!.substring(temporaryText!!.length - 2)
            }
        } else if (decimalPosition != temporaryText!!.length - 3) {
            // If decimal is not positioned at the correct position: remove and fix
            temporaryText = temporaryText!!.replace(".", "")
            while (temporaryText!!.length < 3) {
                // Add paddings to left
                temporaryText = "0$temporaryText"
            }
            val s1 = temporaryText!!.substring(0, temporaryText!!.length - 2)
            val s2 = temporaryText!!.substring(temporaryText!!.length - 2)
            temporaryText = "$s1.$s2"
        }

        // Get rid of extra 0s on the left
        while ((temporaryText!![0] == '0') and (temporaryText!!.length > 4)) {
            temporaryText = temporaryText!!.substring(1, temporaryText!!.length)
        }
    }

    override fun afterTextChanged(editable: Editable) {
        input.removeTextChangedListener(this)
        input.setText(temporaryText)
        input.addTextChangedListener(this)
        input.setSelection(input.length()) // Move cursor to end of input
    }
}
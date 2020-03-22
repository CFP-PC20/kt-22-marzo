package com.meru.evaluacion

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.animation.CycleInterpolator
import android.view.animation.TranslateAnimation
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var string = ""

        editText.addTextChangedListener(
            object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    // desacoplamos el listener
                    var selection = editText.selectionStart
                    editText.removeTextChangedListener(this)

                    if (s != null) {
                        if (s.length > 10) {
                            editText.shakeError()
                        }

                        if (string.length < 10 || s.length < 10 ) {
                            string = s.toString()
                                .replace("[\\W\\d_]".toRegex(), "")
                                .let {
                                    it.substring(0, Math.min(it.length, 10))
                                }
                        }
                        editText.setText(string)
                        editText2.setText(string)

                        selection -= (s.length - string.length)
                    }

                    //lo volvemos a acoplar
                    editText.setSelection(selection)
                    editText.addTextChangedListener(this)
                }
            }
        )

        editText2.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                v.clearFocus()
                val imm = applicationContext.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        }
    }


}

fun View.shakeError () {
    val shake = TranslateAnimation(-7f, 7f, 0f, 0f)
    shake.duration = 400
    shake.interpolator = CycleInterpolator(3f)
    this.startAnimation(shake)
}

package com.tastycafe.mykotlinsample.SupportClasses

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.util.*

class SupportFunctions {

    fun getRandomNumber(): Int {
        return Random().nextInt((800000 - 10)) + 10
                }

    fun Activity.hideKeyboard(view: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }

        }
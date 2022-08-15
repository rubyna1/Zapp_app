package com.example.zapp_project.utils

import android.app.Activity
import android.view.inputmethod.InputMethodManager

fun hideSoftKeyboard(activity: Activity) {
    try {
        val inputMethodManager =
            activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(activity.currentFocus!!.windowToken, 0)
    } catch (e: Exception) {

    }
}
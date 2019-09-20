package com.pmarchenko.itdroid.pocketkotlin.utils

import android.app.Activity
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

/**
 * @author Pavel Marchenko
 */
fun Activity.toast(@StringRes text: Int) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun Activity.toast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun Activity.toastLong(@StringRes text: Int) {
    Toast.makeText(this, text, Toast.LENGTH_LONG).show()
}

fun Activity.toastLong(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_LONG).show()
}

fun Fragment.toast(@StringRes text: Int) {
    Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
}

fun Fragment.toast(text: String) {
    Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
}

fun Fragment.toastLong(@StringRes text: Int) {
    Toast.makeText(requireContext(), text, Toast.LENGTH_LONG).show()
}

fun Fragment.toastLong(text: String) {
    Toast.makeText(requireContext(), text, Toast.LENGTH_LONG).show()
}
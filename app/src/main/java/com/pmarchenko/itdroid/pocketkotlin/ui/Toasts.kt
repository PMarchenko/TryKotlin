package com.pmarchenko.itdroid.pocketkotlin.ui

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

/**
 * @author Pavel Marchenko
 */
fun Context.toast(@StringRes text: Int) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun Context.toast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun Context.toastLong(@StringRes text: Int) {
    Toast.makeText(this, text, Toast.LENGTH_LONG).show()
}

fun Context.toastLong(text: String) {
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
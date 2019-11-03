package com.pmarchenko.itdroid.pocketkotlin.core.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*

/**
 * @author Pavel Marchenko
 */
fun ViewModel.doInBackground(block: () -> Unit) {
    viewModelScope.launch {
        withContext(Dispatchers.IO) {
            block()
        }
    }
}

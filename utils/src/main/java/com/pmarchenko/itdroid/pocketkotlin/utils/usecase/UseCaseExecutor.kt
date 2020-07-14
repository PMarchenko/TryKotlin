package com.pmarchenko.itdroid.pocketkotlin.utils.usecase

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

/**
 * @author Pavel Marchenko
 */
class UseCaseExecutor : ViewModel() {

    operator fun <T> invoke(useCase: UseCase<T>) {
        viewModelScope.launch {
            useCase.execute()
        }
    }
}

fun <T> FragmentActivity.executeUseCase(useCase: () -> UseCase<T>) {
    ViewModelProviders
        .of(this)
        .get(UseCaseExecutor::class.java)
        .invoke(useCase())
}
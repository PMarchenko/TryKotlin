package com.pmarchenko.itdroid.trykotlin.editor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.pmarchenko.itdroid.trykotlin.model.CodeExecutionResult
import com.pmarchenko.itdroid.trykotlin.model.Resource
import com.pmarchenko.itdroid.trykotlin.repository.ProgramRepository

/**
 * @author Pavel Marchenko (Pavel.Marchenko@datart.com -- DataArt)
 */
class EditorViewModel : ViewModel() {

    private val programRepository = ProgramRepository()

    private val programExecutor = MutableLiveData<String>()

    val executionResult: LiveData<Resource<CodeExecutionResult>> = Transformations.switchMap(programExecutor) {
        programRepository.execute(it)
    }

    fun executeProgram(code: String) {
        programExecutor.value = code
    }
}
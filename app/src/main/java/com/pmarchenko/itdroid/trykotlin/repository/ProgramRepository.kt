package com.pmarchenko.itdroid.trykotlin.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pmarchenko.itdroid.trykotlin.model.*

/**
 * @author Pavel Marchenko (Pavel.Marchenko@datart.com -- DataArt)
 */
class ProgramRepository {

    private val result = MutableLiveData<Resource<CodeExecutionResult>>()

    fun execute(code: String): LiveData<Resource<CodeExecutionResult>> {
        result.postValue(Loading())
        Thread {
            Thread.sleep(3000)
            if(Math.random() > 0.5) {
                result.postValue(Error("Test error executing $code"))
            } else {
                result.postValue(Success(null))
            }
        }.start()
        return result
    }
}
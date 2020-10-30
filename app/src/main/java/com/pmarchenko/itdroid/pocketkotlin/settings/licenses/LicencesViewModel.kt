package com.pmarchenko.itdroid.pocketkotlin.settings.licenses

import android.app.Application
import androidx.lifecycle.*
import com.pmarchenko.itdroid.pocketkotlin.R
import com.pmarchenko.itdroid.pocketkotlin.compose.state.UiState
import com.pmarchenko.itdroid.pocketkotlin.utils.ErrorInput
import com.pmarchenko.itdroid.pocketkotlin.utils.eLog
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * @author Pavel Marchenko
 */
class LicencesViewModel(app: Application) : AndroidViewModel(app) {

    private val licensesRepository = RepositoryLicenses(app)

    // all licenses
    private val _licensesState =
        MutableLiveData<UiState<List<LicenseInfo>>>(UiState())
    val licensesState: LiveData<UiState<List<LicenseInfo>>> = _licensesState

    fun loadLicenses() {
        if (_licensesState.value?.hasData() == true) return

        viewModelScope.launch {
            @Suppress("EXPERIMENTAL_API_USAGE")
            licensesRepository.readLicensesList()
                .map { UiState(it) }
                .onStart {
                    emit(UiState(isLoading = true))
                }
                .onEach {
                    if (it.data?.isEmpty() == true) error("Licenses is empty")
                }
                .catch { error ->
                    eLog(error) { "While retrieving licenses list" }
                    emit(
                        UiState(
                            error = ErrorInput(
                                resId = R.string.screen__open_source_licenses__error__loading_licenses,
                                cause = error
                            )
                        )
                    )
                }
                .collect {
                    _licensesState.value = it
                }
        }
    }

    // single licenses
    private val _licenseState =
        MutableLiveData<UiState<Pair<LicenseInfo, String>>>(UiState())
    val licenseState: LiveData<UiState<String>> =
        _licenseState.map {
            UiState(
                data = it.data?.second,
                isLoading = it.isLoading,
                error = it.error
            )
        }

    fun loadLicense(info: LicenseInfo) {
        val (currentInfo, currentText) = _licenseState.value?.data ?: null to null
        if (currentInfo == info && !currentText.isNullOrEmpty()) return
        if (info.length == 0) _licenseState.value = UiState(info to "")

        viewModelScope.launch {
            @Suppress("EXPERIMENTAL_API_USAGE")
            licensesRepository.readLicenseInfo(info)
                .map { UiState(info to it) }
                .onStart { emit(UiState(isLoading = true)) }
                .onEach {
                    if (info.length > 0 && it.data?.second?.isEmpty() == true) {
                        error("Licenses is empty")
                    }
                }
                .catch { error ->
                    eLog(error) { "While retrieving text for license $info" }
                    emit(
                        UiState(
                            error = ErrorInput(
                                resId = R.string.screen__open_source_license__error__loading_license,
                                cause = error,
                            )
                        )
                    )
                }
                .collect {
                    _licenseState.value = it
                }
        }
    }
}

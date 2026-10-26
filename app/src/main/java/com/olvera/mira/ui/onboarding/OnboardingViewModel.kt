package com.olvera.mira.ui.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.olvera.mira.domain.model.PhotographyInterest
import com.olvera.mira.domain.model.PhotographyLevel
import com.olvera.mira.domain.model.UserPreferences
import com.olvera.mira.domain.repository.UserPreferencesRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OnboardingViewModel(
    private val preferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(OnboardingUiState())

    val uiState = _uiState.asStateFlow()

    init {
        loadSavedPreferences()
    }

    fun nextStep() {
        _uiState.update { currentState ->
            currentState.copy(
                currentStep = (currentState.currentStep + 1)
                    .coerceAtMost(2)
            )
        }
    }

    fun previousStep() {
        _uiState.update { state ->
            state.copy(
                currentStep = (state.currentStep - 1)
                    .coerceAtLeast(0)
            )
        }
    }

    fun toggleInterest(interest: PhotographyInterest) {
        _uiState.update { state ->
            val updatedInterests =
                if (interest in state.selectedInterests) {
                    state.selectedInterests - interest
                } else {
                    state.selectedInterests + interest
                }

            state.copy(
                selectedInterests = updatedInterests
            )
        }
    }

    fun selectLevel(level: PhotographyLevel) {
        _uiState.update { state ->
            state.copy(level = level)
        }
    }

    fun setDailyMinutes(minutes: Int) {
        _uiState.update { state ->
            state.copy(
                dailyMinutes = minutes.coerceIn(5, 60)
            )
        }
    }

    fun completeOnboarding() {
        val currentState = _uiState.value

        if (currentState.selectedInterests.isEmpty()) {
            _uiState.update {
                it.copy(
                    errorMessage = "Selecciona al menos un interés."
                )
            }
            return
        }

        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isSaving = true,
                    errorMessage = null
                )
            }

            try {
                preferencesRepository.save(
                    UserPreferences(
                        selectedInterests = currentState.selectedInterests,
                        level = currentState.level,
                        dailyMinutes = currentState.dailyMinutes,
                        hasCompletedOnboarding = true
                    )
                )

                _uiState.update {
                    it.copy(
                        isSaving = false,
                        isCompleted = true
                    )
                }
            } catch (e: CancellationException) {
                _uiState.update {
                    it.copy(
                        isSaving = false,
                        errorMessage =
                            "No se pudieron guardar tus preferencias."
                    )
                }
            }
        }
    }

    private fun loadSavedPreferences() {
        viewModelScope.launch {
            try {
                val saved = preferencesRepository.preferences.first()

                _uiState.update { state ->
                    state.copy(
                        selectedInterests = saved.selectedInterests,
                        level = saved.level,
                        dailyMinutes = saved.dailyMinutes,
                        isCompleted = saved.hasCompletedOnboarding,
                        isCheckingPreferences = false,
                        errorMessage = null
                    )
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isCheckingPreferences = false,
                        errorMessage = "No se pudieron cargar las preferencias."
                    )
                }
            }
        }
    }

}
package com.olvera.mira.ui.onboarding

import androidx.lifecycle.ViewModel
import com.olvera.mira.domain.model.PhotographyInterest
import com.olvera.mira.domain.model.PhotographyLevel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class OnboardingViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(OnboardingUiState())

    val uiState = _uiState.asStateFlow()

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

}
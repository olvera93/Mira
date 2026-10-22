package com.olvera.mira.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.olvera.mira.data.mission.FakeMissionRepository
import com.olvera.mira.domain.repository.MissionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val missionRepository: MissionRepository = FakeMissionRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        HomeUiState()
    )

    val uiState = _uiState.asStateFlow()

    init {
        loadDailyMission(dayNumber = 1)
    }

    private fun loadDailyMission(dayNumber: Int) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    errorMessage = null
                )
            }

            try {
                val mission = missionRepository.getDailyMission(dayNumber)

                _uiState.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        dailyMission = mission,
                        errorMessage = if (mission == null) {
                            "No se encontró la misión."
                        } else {
                            null
                        }
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.message
                    )
                }
            }
        }
    }
}
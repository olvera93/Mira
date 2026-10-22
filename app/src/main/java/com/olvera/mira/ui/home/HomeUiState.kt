package com.olvera.mira.ui.home

import com.olvera.mira.domain.model.Mission

data class HomeUiState(

    val isLoading: Boolean = true,
    val challengeTitle: String = "Mira tu ciudad en 7 días",
    val completedDays: Int = 0,
    val totalDays: Int = 7,
    val dailyMission: Mission? = null,
    val errorMessage: String? = null
)
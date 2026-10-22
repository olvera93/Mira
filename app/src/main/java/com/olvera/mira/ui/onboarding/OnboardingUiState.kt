package com.olvera.mira.ui.onboarding

import com.olvera.mira.domain.model.PhotographyInterest
import com.olvera.mira.domain.model.PhotographyLevel

data class OnboardingUiState(

    val currentStep: Int = 0,
    val selectedInterests: Set<PhotographyInterest> = emptySet(),
    val level: PhotographyLevel = PhotographyLevel.BEGINNER,
    val dailyMinutes: Int = 10,
    val isSaving: Boolean = false,
    val errorMessage: String? = null
)

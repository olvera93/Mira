package com.olvera.mira.domain.model

data class UserPreferences(
    val selectedInterests: Set<PhotographyInterest> = emptySet(),
    val level: PhotographyLevel = PhotographyLevel.BEGINNER,
    val dailyMinutes: Int = 10,
    val hasCompletedOnboarding: Boolean = false
)

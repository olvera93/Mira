package com.olvera.mira.domain.repository

import com.olvera.mira.domain.model.UserPreferences
import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {

    val preferences: Flow<UserPreferences>

    suspend fun save(preferences: UserPreferences)
}
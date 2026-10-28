package com.olvera.mira.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.olvera.mira.domain.model.PhotographyInterest
import com.olvera.mira.domain.model.PhotographyLevel
import com.olvera.mira.domain.model.UserPreferences
import com.olvera.mira.domain.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import okio.IOException

private val Context.userPreferencesStore by
preferencesDataStore(name = "mira_user_preferences")

private object UserPreferencesKeys {

    val SELECTED_INTERESTS =
        stringSetPreferencesKey("selected_interests")

    val PHOTOGRAPHY_LEVEL =
        stringPreferencesKey("photography_level")

    val DAILY_MINUTES =
        intPreferencesKey("daily_minutes")

    val HAS_COMPLETED_ONBOARDING =
        booleanPreferencesKey("has_completed_onboarding")
}

class DataStoreUserPreferencesRepository(
    context: Context
) : UserPreferencesRepository {

    private val dataStore = context.userPreferencesStore

    override val preferences: Flow<UserPreferences> =
        dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                val selectedInterests =
                    preferences[UserPreferencesKeys.SELECTED_INTERESTS]
                        .orEmpty()
                        .mapNotNull { name ->
                            PhotographyInterest.entries
                                .firstOrNull { it.name == name }
                        }
                        .toSet()

                val levelName =
                    preferences[UserPreferencesKeys.PHOTOGRAPHY_LEVEL]

                val level =
                    PhotographyLevel.entries.firstOrNull {
                        it.name == levelName
                    } ?: PhotographyLevel.BEGINNER

                UserPreferences(
                    selectedInterests = selectedInterests,
                    level = level,
                    dailyMinutes = preferences[
                        UserPreferencesKeys.DAILY_MINUTES
                    ] ?: 10,
                    hasCompletedOnboarding = preferences[
                        UserPreferencesKeys.HAS_COMPLETED_ONBOARDING
                    ] ?: false
                )
            }

    override suspend fun save(preferences: UserPreferences) {
        dataStore.edit { storedPreferences ->
            storedPreferences[
                UserPreferencesKeys.SELECTED_INTERESTS
            ] = preferences.selectedInterests
                .map { it.name }
                .toSet()

            storedPreferences[
                UserPreferencesKeys.PHOTOGRAPHY_LEVEL
            ] = preferences.level.name

            storedPreferences[
                UserPreferencesKeys.DAILY_MINUTES
            ] = preferences.dailyMinutes

            storedPreferences[
                UserPreferencesKeys.HAS_COMPLETED_ONBOARDING
            ] = preferences.hasCompletedOnboarding
        }
    }
}
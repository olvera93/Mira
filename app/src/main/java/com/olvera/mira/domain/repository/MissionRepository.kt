package com.olvera.mira.domain.repository

import com.olvera.mira.domain.model.Mission


interface MissionRepository {

    suspend fun getDailyMission(dayNumber: Int): Mission?
}
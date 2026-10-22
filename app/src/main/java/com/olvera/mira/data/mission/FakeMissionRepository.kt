package com.olvera.mira.data.mission

import com.olvera.mira.domain.model.Mission
import com.olvera.mira.domain.repository.MissionRepository

class FakeMissionRepository : MissionRepository {

    private val missions = listOf(
        Mission(
            id = "city-detail-01",
            dayNumber = 1,
            title = "Encuentra lo extraordinario",
            hook = "Fotografía algo cotidiano que casi nadie mira.",
            technique = "Observación y selección del sujeto",
            durationMinutes = 10,
            instructions = """
                Encuentra un detalle de tu ciudad y fotografíalo desde una perspectiva interesante.
            """.trimIndent()
        )
    )

    override suspend fun getDailyMission(dayNumber: Int): Mission? {
        return missions.firstOrNull { it.dayNumber == dayNumber }
    }
}
package com.olvera.mira.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Text(text = "MIRA")

        Text(text = uiState.challengeTitle)

        Text(
            text = "${uiState.completedDays} de " +
                    "${uiState.totalDays} días completados"
        )

        val mission = uiState.dailyMission

        when {
            uiState.isLoading -> {
                Text(text = "Preparando tu misión...")
            }

            uiState.errorMessage != null -> {
                Text(text = uiState.errorMessage)
            }

            mission != null -> {
                Text(mission.title)
                Text(mission.hook)
                Text(mission.instructions)
                Text("Técnica: ${mission.technique}")

            }

            else -> {
                Text("Tu misión de hoy estará aquí.")
            }
        }


    }

}
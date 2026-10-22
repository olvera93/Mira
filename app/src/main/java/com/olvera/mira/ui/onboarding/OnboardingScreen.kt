package com.olvera.mira.ui.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.FilterChip
import com.olvera.mira.domain.model.PhotographyInterest
import com.olvera.mira.domain.model.PhotographyLevel
import com.olvera.mira.ui.theme.MiraCream
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.olvera.mira.ui.theme.MiraInk
import com.olvera.mira.ui.theme.MiraMuted

@Composable
fun OnboardingScreen(
    uiState: OnboardingUiState,
    onNext: () -> Unit,
    onPrevious: () -> Unit,
    onToggleInterest: (PhotographyInterest) -> Unit,
    onSelectLevel: (PhotographyLevel) -> Unit,
    onSetDailyMinutes: (Int) -> Unit,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .fillMaxWidth()
            .background(MiraCream)
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(
            text = "MIRA.",
            color = MiraInk,
            style = MaterialTheme.typography.headlineLarge.copy(
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = (-1).sp
            )
        )

        Text(
            text = "PASO ${uiState.currentStep + 1} DE 3",
            color = MiraMuted,
            style = MaterialTheme.typography.labelMedium.copy(
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 1.2.sp
            )
        )

        when (uiState.currentStep) {
            0 -> {
                OnboardingWelcomeStep(
                    onStart = onNext,
                    modifier = Modifier.weight(1f)
                )
            }

            1 -> {
                Text(
                    text = "¿Qué te gustaría aprender?",
                    style = MaterialTheme.typography.headlineMedium
                )

                Text(
                    text = "Selecciona todos los temas que te interesen."
                )

                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    PhotographyInterest.entries.forEach { interest ->
                        FilterChip(
                            selected = interest in uiState.selectedInterests,
                            onClick = {
                                onToggleInterest(interest)
                            },
                            label = {
                                Text(interest.toDisplayName())
                            }
                        )
                    }
                }

                Button(onClick = onPrevious) {
                    Text("Atrás")
                }

                Button(
                    onClick = onNext,
                    enabled = uiState.selectedInterests.isNotEmpty()
                ) {
                    Text("Continuar")
                }
            }

            2 -> {
                Text(
                    text = "Define tu aventura.",
                    style = MaterialTheme.typography.headlineMedium
                )

                Text(text = "¿Cuál es tu nivel fotográfico?")

                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    PhotographyLevel.entries.forEach { level ->
                        FilterChip(
                            selected = uiState.level == level,
                            onClick = { onSelectLevel(level) },
                            label = {
                                Text(level.toDisplayName())
                            }
                        )
                    }
                }

                Text("¿Cuánto tiempo quieres dedicar al día?")

                Row(
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    listOf(5, 10, 15, 20, 30).forEach { minutes ->
                        FilterChip(
                            selected = uiState.dailyMinutes == minutes,
                            onClick = {
                                onSetDailyMinutes(minutes)
                            },
                            label = {
                                Text("$minutes min")
                            }
                        )
                    }
                }

                Button(onClick = onPrevious) {
                    Text("Atrás")
                }
            }
        }
    }
}

private fun PhotographyInterest.toDisplayName(): String =
    when (this) {
        PhotographyInterest.COMPOSITION ->
            "Composición"

        PhotographyInterest.LIGHT_AND_SHADOW ->
            "Luz y sombras"

        PhotographyInterest.ARCHITECTURE ->
            "Arquitectura"

        PhotographyInterest.COLORS ->
            "Color"

        PhotographyInterest.REFLECTIONS ->
            "Reflejos"

        PhotographyInterest.STREET_PHOTOGRAPHY ->
            "Fotografía callejera"
    }

private fun PhotographyLevel.toDisplayName(): String =
    when (this) {
        PhotographyLevel.BEGINNER -> "Principiante"
        PhotographyLevel.HOBBYIST -> "Aficionado"
        PhotographyLevel.ADVANCED -> "Avanzado"
    }
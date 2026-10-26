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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.olvera.mira.ui.onboarding.components.SelectableOptionCard
import com.olvera.mira.ui.theme.MiraInk
import com.olvera.mira.ui.theme.MiraLime
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
                    text = "¿Qué te gustaría fotografiar",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MiraInk
                )

                Text(
                    text = "Elige los temas que te inspiren. " +
                            "Personalizaremos tus primeras misiones.",
                    color = MiraMuted
                )

                val interest = PhotographyInterest.entries

                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    interest.chunked(2).forEach { rowInterests ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {

                            rowInterests.forEach { interest ->
                                SelectableOptionCard(
                                    title = interest.toDisplayName(),
                                    description = interest.toDescription(),
                                    selected = interest in uiState.selectedInterests,
                                    onClick = {
                                        onToggleInterest(interest)
                                    },
                                    modifier = Modifier.weight(1f)
                                )
                            }

                            if (rowInterests.size == 1) {
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    OutlinedButton(
                        onClick = onPrevious,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Atrás")
                    }

                    Button(
                        onClick = onNext,
                        enabled = uiState.selectedInterests.isNotEmpty(),
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(18.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MiraLime,
                            contentColor = MiraInk
                        )
                    ) {
                        Text("Continuar")
                    }

                }
            }

            2 -> {


                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    Text(
                        text = "Define tu aventura",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MiraInk
                    )

                    Text(
                        text = "Adaptaremos los retos a tu experiencia y al tiempo que tengas.",
                        color = MiraMuted
                    )

                    Text(
                        text = "¿Cuál es tu nivel?",
                        style = MaterialTheme.typography.titleMedium,
                        color = MiraInk
                    )

                    PhotographyLevel.entries.forEach { level ->
                        SelectableOptionCard(
                            title = level.toDisplayName(),
                            description = level.toDescription(),
                            selected = uiState.level == level,
                            onClick = {
                                onSelectLevel(level)
                            }
                        )
                    }
                }

                Text(
                    text = "¿Cuánto tiempo tienes al día?",
                    style = MaterialTheme.typography.titleMedium,
                    color = MiraInk
                )

                listOf(5, 10, 15, 20, 30).chunked(3).forEach { rowMinutes ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        rowMinutes.forEach { minutes ->
                            FilterChip(
                                selected = uiState.dailyMinutes == minutes,
                                onClick = {
                                    onSetDailyMinutes(minutes)
                                },
                                label = {
                                    Text("${minutes} min")
                                }
                            )
                        }
                    }
                }

                OutlinedButton(
                    onClick = onPrevious,
                    modifier = Modifier.fillMaxWidth()
                ) {
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

private fun PhotographyInterest.toDescription(): String =
    when (this) {
        PhotographyInterest.COMPOSITION ->
            "Ordena los elementos y guía la mirada."

        PhotographyInterest.LIGHT_AND_SHADOW ->
            "Descubre formas creadas por la luz."

        PhotographyInterest.ARCHITECTURE ->
            "Explora edificios, líneas y perspectivas."

        PhotographyInterest.COLORS ->
            "Juega con contrastes y tonos."

        PhotographyInterest.REFLECTIONS ->
            "Busca escenas en charcos y ventanas."

        PhotographyInterest.STREET_PHOTOGRAPHY ->
            "Cuenta historias de la vida urbana."
    }

private fun PhotographyLevel.toDescription(): String =
    when (this) {
        PhotographyLevel.BEGINNER ->
            "Estoy empezando y quiero aprender desde cero."

        PhotographyLevel.HOBBYIST ->
            "Ya tomo fotos y quiero mejorar mi técnica."

        PhotographyLevel.ADVANCED ->
            "Quiero experimentar con técnicas más complejas."
    }
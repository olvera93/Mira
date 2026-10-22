package com.olvera.mira.ui.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun OnboardingScreen(
    uiState: OnboardingUiState,
    onNext: () -> Unit,
    onPrevious: () -> Unit,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(
            text = "MIRA.",
            style = MaterialTheme.typography.headlineLarge
        )

        Text(
            text = "Paso ${uiState.currentStep + 1} de 3",
            style = MaterialTheme.typography.labelMedium
        )

        when (uiState.currentStep) {
            0 -> {
                Text(
                    text = "Tu ciudad tiene otra perspectiva.",
                    style = MaterialTheme.typography.headlineMedium
                )

                Text(
                    text = "Descubre lugares, completa retos y " +
                            "aprende fotografía mientras exploras."
                )

                Button(onClick = onNext) {
                    Text("Empezar mi aventura")
                }
            }

            1 -> {
                Text(
                    text = "¿Qué te gustaría aprender?",
                    style = MaterialTheme.typography.headlineMedium
                )

                Text("Aquí seleccionaremos tus intereses fotográficos.")

                Button(onClick = onPrevious) {
                    Text("Atrás")
                }

                Button(onClick = onNext) {
                    Text("Continuar")
                }
            }

            2 -> {
                Text(
                    text = "Define tu aventura.",
                    style = MaterialTheme.typography.headlineMedium
                )

                Text("Aquí elegiremos tu nivel y tiempo disponible.")

                Button(onClick = onPrevious) {
                    Text("Atrás")
                }
            }
        }
    }

}
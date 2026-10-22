package com.olvera.mira.ui.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.olvera.mira.ui.theme.MiraHeroEnd
import com.olvera.mira.ui.theme.MiraHeroStart
import com.olvera.mira.ui.theme.MiraInk
import com.olvera.mira.ui.theme.MiraLime
import com.olvera.mira.ui.theme.MiraMuted
import androidx.compose.foundation.Image
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.olvera.mira.R

@Composable
fun OnboardingWelcomeStep(
    onStart: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Panel visual principal
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp)
                .clip(RoundedCornerShape(28.dp))
        ) {
            Image(
                painter = painterResource(
                    id = R.drawable.onboarding_city
                ),
                contentDescription = "Arquitectura urbana al atardecer",
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize()
            )

            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        Brush.linearGradient(
                            colors = listOf(
                                MiraHeroStart.copy(alpha = 0.65f),
                                MiraHeroEnd.copy(alpha = 0.55f)
                            )
                        )
                    )
            )

            Box(
                modifier = Modifier
                    .matchParentSize()
                    .padding(20.dp)
            ) {
                Column(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .fillMaxWidth(0.78f),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "RETO FOTOGRÁFICO · 7 DÍAS",
                        color = MiraLime,
                        style = MaterialTheme.typography.labelSmall.copy(
                            letterSpacing = 1.2.sp
                        )
                    )

                    Text(
                        text = "Mira lo que otros pasan por alto.",
                        color = Color.White,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                }

                Column(
                    modifier = Modifier.align(Alignment.BottomStart),
                    verticalArrangement = Arrangement.spacedBy(3.dp)
                ) {
                    Text(
                        text = "OBSERVA · ENCUENTRA · CAPTURA",
                        color = Color.White,
                        style = MaterialTheme.typography.labelMedium
                    )

                    Text(
                        text = "Una nueva misión cada día.",
                        color = Color.White.copy(alpha = 0.9f),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }

        Text(
            text = "Tu ciudad tiene otra perspectiva.",
            style = MaterialTheme.typography.headlineMedium,
            color = MiraInk,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Descubre rincones, completa retos y aprende fotografía mientras exploras.",
            style = MaterialTheme.typography.bodyLarge,
            color = MiraMuted
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onStart,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(18.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MiraLime,
                contentColor = MiraInk
            )
        ) {
            Text(
                text = "Empezar mi aventura",
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}
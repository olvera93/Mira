package com.olvera.mira

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.olvera.mira.ui.home.HomeScreen
import com.olvera.mira.ui.home.HomeViewModel
import com.olvera.mira.ui.onboarding.OnboardingScreen
import com.olvera.mira.ui.onboarding.OnboardingViewModel
import com.olvera.mira.ui.theme.MiraTheme

class MainActivity : ComponentActivity() {

    private val homeViewModel: HomeViewModel by viewModels()
    private val onboardingViewModel: OnboardingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MiraTheme {

                //val uiState = homeViewModel.uiState.collectAsState().value
                val uiState = onboardingViewModel.uiState
                    .collectAsStateWithLifecycle()
                    .value

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    OnboardingScreen(
                        uiState = uiState,
                        onNext = onboardingViewModel::nextStep,
                        onPrevious = onboardingViewModel::previousStep,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
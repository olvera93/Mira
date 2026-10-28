package com.olvera.mira

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.olvera.mira.data.preferences.DataStoreUserPreferencesRepository
import com.olvera.mira.ui.home.HomeScreen
import com.olvera.mira.ui.home.HomeViewModel
import com.olvera.mira.ui.onboarding.OnboardingScreen
import com.olvera.mira.ui.onboarding.OnboardingViewModel
import com.olvera.mira.ui.onboarding.OnboardingViewModelFactory
import com.olvera.mira.ui.theme.MiraTheme

class MainActivity : ComponentActivity() {

    private val homeViewModel: HomeViewModel by viewModels()
    private val preferencesRepository by lazy {
        DataStoreUserPreferencesRepository(applicationContext)
    }

    private val onboardingViewModel: OnboardingViewModel by viewModels {
        OnboardingViewModelFactory(preferencesRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MiraTheme {

                val onboardingState =
                    onboardingViewModel.uiState
                        .collectAsStateWithLifecycle()
                        .value

                val homeState =
                    homeViewModel.uiState
                        .collectAsStateWithLifecycle()
                        .value

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    when {
                        onboardingState.isCheckingPreferences -> {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(innerPadding),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }

                        onboardingState.isCompleted -> {
                            HomeScreen(
                                uiState = homeState,
                                modifier = Modifier.padding(innerPadding)
                            )
                        }

                        else -> {
                            OnboardingScreen(
                                uiState = onboardingState,
                                onNext = onboardingViewModel::nextStep,
                                onPrevious = onboardingViewModel::previousStep,
                                onToggleInterest = onboardingViewModel::toggleInterest,
                                onSelectLevel = onboardingViewModel::selectLevel,
                                onSetDailyMinutes = onboardingViewModel::setDailyMinutes,
                                onComplete = onboardingViewModel::completeOnboarding,
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                    }
                }
            }
        }
    }
}
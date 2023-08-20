package com.example.studyapp

import android.app.Application
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.studyapp.data.base.MainViewModel
import com.example.studyapp.data.base.MainViewModelFactory
import com.example.studyapp.drawer.Drawer
import com.example.studyapp.drawer.DrawerViewModel
import com.example.studyapp.presentation.sign_in.GoogleAuthUiClient
import com.example.studyapp.presentation.sign_in.SignInViewModel
import com.example.studyapp.ui.components.ProfileScreen
import com.example.studyapp.ui.screens.CardScreen
import com.example.studyapp.ui.screens.MainScreen
import com.example.studyapp.ui.screens.QuizScreen
import com.example.studyapp.ui.screens.SettingsScreen
import com.example.studyapp.ui.screens.SignInScreen
import com.example.studyapp.ui.theme.AppTheme
import com.google.android.gms.auth.api.identity.Identity
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val drawerViewModel = DrawerViewModel(rememberNavController())

            val isSystemDark = isSystemInDarkTheme()
            val isDarkTheme = remember { mutableStateOf(isSystemDark) }

            AppTheme(darkTheme = isDarkTheme.value) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.surfaceVariant
                ) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background,
                        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
                    ) {
                        Drawer(drawerViewModel) {
                            val owner = LocalViewModelStoreOwner.current

                            owner?.let {
                                val viewModel: MainViewModel = viewModel(
                                    it, "MainViewModel", MainViewModelFactory(
                                        LocalContext.current.applicationContext as Application
                                    )
                                )

                                NavHost(navController = drawerViewModel.navController, startDestination = "main") {
                                    composable("main") { MainScreen(drawerViewModel, viewModel) }
                                    composable("quiz") { QuizScreen(drawerViewModel, viewModel) }
                                    composable("card") { CardScreen(drawerViewModel, viewModel) }
                                    composable("settings") { SettingsScreen(drawerViewModel, isDarkTheme) }
                                    composable("sign_in") {
                                        val signInModel = viewModel<SignInViewModel>()
                                        val state by signInModel.state.collectAsStateWithLifecycle()

                                        val launcher = rememberLauncherForActivityResult(
                                            contract = ActivityResultContracts.StartIntentSenderForResult(),
                                            onResult = { result ->
                                                if (result.resultCode == RESULT_OK) {
                                                    lifecycleScope.launch {
                                                        val signInResult = googleAuthUiClient.signInWithIntent(
                                                            intent = result.data ?: return@launch
                                                        )
                                                        signInModel.onSignInResult(signInResult)
                                                    }
                                                }
                                            }
                                        )

                                        LaunchedEffect(key1 = state.isSignInSuccessful) {
                                            if (state.isSignInSuccessful) {
                                                Toast.makeText(
                                                    applicationContext,
                                                    "Sign in successful!",
                                                    Toast.LENGTH_LONG
                                                ).show()
                                            }
                                        }

                                        if (!state.isSignInSuccessful || googleAuthUiClient.getSignedInUser() == null) {
                                            SignInScreen(
                                                state = state,
                                                onSignInClick = {
                                                    lifecycleScope.launch {
                                                        val signIntIntentSender = googleAuthUiClient.signIn()
                                                        launcher.launch(
                                                            IntentSenderRequest.Builder(
                                                                signIntIntentSender ?: return@launch
                                                            ).build()
                                                        )
                                                    }
                                                }
                                            )
                                        } else {
                                            ProfileScreen(
                                                userData = googleAuthUiClient.getSignedInUser(),
                                                onSignOut = {
                                                    lifecycleScope.launch {
                                                        googleAuthUiClient.signOut()
                                                        drawerViewModel.navigate("main")
                                                    }
                                                    Toast.makeText(
                                                        applicationContext,
                                                        "Signed out",
                                                        Toast.LENGTH_LONG
                                                    ).show()
                                                }
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

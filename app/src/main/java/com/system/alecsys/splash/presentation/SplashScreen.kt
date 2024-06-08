package com.system.alecsys.splash.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.system.alecsys.R
import com.system.alecsys.core.navigation.DashboardScreen
import com.system.alecsys.core.navigation.LoginScreen
import kotlinx.coroutines.delay

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SplashScreen(
    state: SplashScreenState,
    onNavigate: (destination: Any) -> Unit
) {
    LaunchedEffect(
        key1 = state.token,
        block = {
            delay(1000)
            if (state.token.isNotBlank())
                onNavigate(DashboardScreen)
            else onNavigate(LoginScreen)
        }
    )
    Scaffold {
        Box(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.background
                )
                .fillMaxSize()
                .padding(
                    it
                ),
            contentAlignment = Alignment.Center,
            content = {
                Icon(
                    painter = painterResource(id = R.drawable.alecsys_icon),
                    contentDescription = null,
                    modifier = Modifier.size(256.dp),
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        )
    }
}
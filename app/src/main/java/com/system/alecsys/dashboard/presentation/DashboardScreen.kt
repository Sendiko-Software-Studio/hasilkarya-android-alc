package com.system.alecsys.dashboard.presentation

import android.Manifest
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.system.alecsys.R
import com.system.alecsys.core.navigation.HeavyVehicleFuelScreen
import com.system.alecsys.core.navigation.TruckFuelScreen
import com.system.alecsys.core.navigation.SettingsScreen
import com.system.alecsys.core.ui.theme.poppinsFont
import com.system.alecsys.dashboard.presentation.component.MenuCard
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun DashboardScreen(
    state: DashboardScreenState,
    onEvent: (DashboardScreenEvent) -> Unit,
    onNavigate: (destination: Any) -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)
    LaunchedEffect(
        key1 = cameraPermissionState.hasPermission,
        block = {
            if (!cameraPermissionState.hasPermission)
                cameraPermissionState.launchPermissionRequest()
        }
    )
    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = {
                    val time = LocalDateTime.now().hour
                    val greeting = when (time) {
                        in 5..11 -> {
                            "Pagi"
                        }
                        in 12..15 -> {
                            "Siang"
                        }
                        in 15..17 -> {
                            "Sore"
                        }
                        else -> {
                            "Malam"
                        }
                    }
                    Column(
                        modifier = Modifier.padding(vertical = 8.dp)
                    ) {
                        Text(
                            text = "Selamat $greeting,",
                            fontFamily = poppinsFont,
                            fontSize = 18.sp
                        )
                        Text(
                            text = state.name,
                            fontFamily = poppinsFont,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { onNavigate(SettingsScreen) },
                        content = {
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = "Settings",
                            )
                        }
                    )
                },
                scrollBehavior = scrollBehavior
            )
        },
    ) { paddingValues ->
        LazyColumn(
            contentPadding = PaddingValues(
                top = paddingValues.calculateTopPadding(),
                start = 16.dp,
                end = 16.dp
            ),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            content = {
                item {
                    AnimatedVisibility(visible = state.isConnecting || state.isUploading) {
                        LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                    }
                }
                item {
                    Row(modifier = Modifier.padding(top = 0.dp)) {
                        MenuCard(
                            text = "Scan Transaksi BBM Truk",
                            icon = painterResource(id = R.drawable.scan_truck),
                            onClickAction = {
                                onNavigate(TruckFuelScreen)
                            },
                            enabled = true,
                            modifier = Modifier.weight(1f)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        MenuCard(
                            text = "Scan Transaksi BBM Alat Berat",
                            icon = painterResource(id = R.drawable.scan_exca),
                            onClickAction = {
                                onNavigate(HeavyVehicleFuelScreen)
                            },
                            enabled = true,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        )
    }
}
package com.system.alecsys.dashboard.presentation

import android.Manifest
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.system.alecsys.R
import com.system.alecsys.core.navigation.GasHeavyVehicleScreen
import com.system.alecsys.core.navigation.GasTruckScreen
import com.system.alecsys.core.navigation.ProfileScreen
import com.system.alecsys.core.network.Status
import com.system.alecsys.core.ui.theme.poppinsFont
import com.system.alecsys.dashboard.presentation.component.InfoItemCard
import com.system.alecsys.dashboard.presentation.component.MenuCard
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun DashboardScreen(
    state: DashboardScreenState,
    connectionStatus: Status,
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
    LaunchedEffect(
        key1 = state.totalData,
        key2 = connectionStatus,
        block = {
            if (state.totalData != 0 && connectionStatus == Status.Available)
                onEvent(DashboardScreenEvent.CheckDataAndPost)
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
                        onClick = { onNavigate(ProfileScreen) },
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
                    if (state.isUploading || state.isConnecting) {
                        LinearProgressIndicator(
                            strokeCap = StrokeCap.Round,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
                item {
                    Row(modifier = Modifier.padding(top = 0.dp)) {
                        MenuCard(
                            text = "Scan Transaksi BBM Truk",
                            icon = painterResource(id = R.drawable.scan_truck),
                            onClickAction = {
                                onNavigate(GasTruckScreen)
                            },
                            enabled = true,
                            modifier = Modifier.weight(1f)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        MenuCard(
                            text = "Scan Transaksi BBM Alat Berat",
                            icon = painterResource(id = R.drawable.scan_exca),
                            onClickAction = {
                                onNavigate(GasHeavyVehicleScreen)
                            },
                            enabled = true,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
                item {
                    AnimatedVisibility(
                        visible = state.isUploading,
                        enter = expandHorizontally(),
                        exit = shrinkHorizontally()
                    ) {
                        Spacer(modifier = Modifier.height(8.dp))
                        InfoItemCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            info = "Sedang mengupload data..",
                            icon = {
                                Icon(imageVector = Icons.Default.Info, contentDescription = "info")
                            }
                        )
                    }
                }
                item {
                    AnimatedVisibility(
                        visible = state.totalData != 0,
                        enter = expandHorizontally(),
                        exit = shrinkHorizontally()
                    ) {
                        InfoItemCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            info = "${state.totalData} data menunggu diupload.",
                            icon = {
                                Icon(imageVector = Icons.Default.Info, contentDescription = "info")
                            }
                        )
                    }
                }
                item {
                    AnimatedVisibility(
                        visible = state.isTokenExpired,
                        enter = expandHorizontally(),
                        exit = shrinkHorizontally()
                    ) {
                        InfoItemCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            info = "Maaf, aplikasi tidak bisa terhubung ke server.",
                            icon = {
                                TextButton(
                                    onClick = { onEvent(DashboardScreenEvent.RetryLogin) },
                                    content = {
                                        Text(
                                            text = "Coba lagi",
                                            color = MaterialTheme.colorScheme.onErrorContainer
                                        )
                                    }
                                )
                            },
                            isError = true
                        )
                    }
                }
            }
        )
    }
}
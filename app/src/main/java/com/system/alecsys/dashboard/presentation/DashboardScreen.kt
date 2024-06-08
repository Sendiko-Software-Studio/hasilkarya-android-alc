package com.system.alecsys.dashboard.presentation

import android.Manifest
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.system.alecsys.R
import com.system.alecsys.core.navigation.GasHeavyVehicleScreen
import com.system.alecsys.core.navigation.GasTruckScreen
import com.system.alecsys.core.navigation.MaterialScreen
import com.system.alecsys.core.navigation.ProfileScreen
import com.system.alecsys.core.navigation.StationScreen
import com.system.alecsys.core.network.Status
import com.system.alecsys.core.ui.theme.poppinsFont
import com.system.alecsys.dashboard.presentation.component.MenuCard
import com.system.alecsys.station.presentation.component.StationLocation
import com.system.alecsys.dashboard.presentation.component.InfoItemCard

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun DashboardScreen(
    state: DashboardScreenState,
    connectionStatus: Status,
    onEvent: (DashboardScreenEvent) -> Unit,
    onNavigate: (destination: Any) -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val noStation = state.activeStation == null

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
                    Text(
                        text = "Selamat datang, ${state.name}",
                        fontFamily = poppinsFont
                    )
                },
                actions = {
                    IconButton(onClick = { onNavigate(ProfileScreen) }) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Settings",
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    scrolledContainerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                    actionIconContentColor = MaterialTheme.colorScheme.onBackground
                ),
                scrollBehavior = scrollBehavior
            )
        },
    ) { paddingValues ->
        Column {
            if (state.role == "checker"){
                StationLocation(
                    stationName = if (noStation)
                        "Tidak ada."
                    else {
                        if (state.activeStation!!.name == "Station berhasil disimpan.") {
                            "Pos baru disimpan."
                        } else {
                            "${state.activeStation.name}, ${state.activeStation.province}."
                        }
                    },
                    onButtonClick = {
                        onNavigate(StationScreen)
                    },
                    modifier = Modifier.padding(
                        top = paddingValues.calculateTopPadding(),
                        start = 16.dp,
                        end = 16.dp
                    )
                )
            } else Spacer(modifier = Modifier.height(paddingValues.calculateTopPadding() + 16.dp))
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                content = {
                    if (state.role == "checker" || state.role == "admin") {
                        item {
                            MenuCard(
                                text = "Scan Material Movement",
                                icon = painterResource(id = R.drawable.scan_material_movement),
                                onClickAction = {
                                    onNavigate(MaterialScreen)
                                },
                                enabled = !noStation
                            )
                        }
                    }
                    if (state.role == "gas-operator" || state.role == "admin") {
                        item {
                            MenuCard(
                                text = "Scan Transaksi BBM Truk",
                                icon = painterResource(id = R.drawable.scan_truck),
                                onClickAction = {
                                    onNavigate(GasTruckScreen)
                                },
                                enabled = true
                            )
                        }
                    }
                    if (state.role == "gas-operator" || state.role == "admin") {
                        item {
                            MenuCard(
                                text = "Scan Transaksi BBM Alat Berat",
                                icon = painterResource(id = R.drawable.scan_exca),
                                onClickAction = {
                                    onNavigate(GasHeavyVehicleScreen)
                                },
                                enabled = true
                            )
                        }
                    }
                }
            )
            AnimatedVisibility(
                visible = state.totalData != 0,
                enter = expandHorizontally(),
                exit = shrinkHorizontally()
            ) {
                InfoItemCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    info = "${state.totalData} data menunggu diupload.",
                    icon = {
                        Icon(imageVector = Icons.Default.Info, contentDescription = "info")
                    }
                )
            }
            if (noStation && state.role == "checker") {
                InfoItemCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    info = "Mohon pilih Pos terlebih dulu.",
                    icon = {
                        Icon(imageVector = Icons.Default.Info, contentDescription = "info")
                    }
                )
            }
            AnimatedVisibility(
                visible = state.isUploading,
                enter = expandHorizontally(),
                exit = shrinkHorizontally()
            ) {
                Spacer(modifier = Modifier.height(8.dp))
                InfoItemCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    info = "Sedang mengupload data..",
                    icon = {
                        Icon(imageVector = Icons.Default.Info, contentDescription = "info")
                    }
                )
            }
            AnimatedVisibility(
                visible = state.isConnecting,
                enter = expandHorizontally(),
                exit = shrinkHorizontally()
            ) {
                InfoItemCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    info = "Mencoba menghubungi server..",
                    icon = {
                        Icon(imageVector = Icons.Default.Info, contentDescription = "info")
                    }
                )
            }
            AnimatedVisibility(
                visible = state.isTokenExpired,
                enter = expandHorizontally(),
                exit = shrinkHorizontally()
            ) {
                InfoItemCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    info = "Maaf, aplikasi tidak bisa terhubung ke server.",
                    icon = {
                        TextButton(
                            onClick = { onEvent(DashboardScreenEvent.RetryLogin) },
                            content = {
                                Text(text = "Coba lagi", color = MaterialTheme.colorScheme.onErrorContainer)
                            }
                        )
                    },
                    isError = true
                )
            }
        }
    }
}
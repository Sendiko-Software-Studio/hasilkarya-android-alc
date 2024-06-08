package com.system.alecsys.core

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.system.alecsys.core.navigation.DashboardScreen
import com.system.alecsys.core.navigation.GasHeavyVehicleScreen
import com.system.alecsys.core.navigation.GasTruckScreen
import com.system.alecsys.core.navigation.LoginScreen
import com.system.alecsys.core.navigation.MaterialScreen
import com.system.alecsys.core.navigation.ProfileScreen
import com.system.alecsys.core.navigation.SplashScreen
import com.system.alecsys.core.navigation.StationScreen
import com.system.alecsys.core.preferences.ThemeViewModel
import com.system.alecsys.core.ui.theme.AppTheme.Dark
import com.system.alecsys.core.ui.theme.AppTheme.Default
import com.system.alecsys.core.ui.theme.AppTheme.Light
import com.system.alecsys.core.ui.theme.HasilKaryaTheme
import com.system.alecsys.dashboard.presentation.DashboardScreen
import com.system.alecsys.dashboard.presentation.DashboardScreenViewModel
import com.system.alecsys.heavy_vehicle_fuel.presentation.HeavyVehicleFuelQrScreen
import com.system.alecsys.heavy_vehicle_fuel.presentation.HeavyVehicleFuelQrScreenViewModel
import com.system.alecsys.login.presentation.LoginScreen
import com.system.alecsys.login.presentation.LoginScreenViewModel
import com.system.alecsys.material.presentation.MaterialQrScreen
import com.system.alecsys.material.presentation.MaterialQrScreenViewModel
import com.system.alecsys.profile.presentation.ProfileScreen
import com.system.alecsys.profile.presentation.ProfileScreenViewModel
import com.system.alecsys.splash.presentation.SplashScreen
import com.system.alecsys.splash.presentation.SplashScreenViewModel
import com.system.alecsys.station.presentation.StationQrScreen
import com.system.alecsys.station.presentation.StationQrScreenViewModel
import com.system.alecsys.truck_fuel.presentation.TruckFuelQrScreen
import com.system.alecsys.truck_fuel.presentation.TruckFuelQrScreenViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val themeViewModel = hiltViewModel<ThemeViewModel>()
            HasilKaryaTheme(
                darkTheme = when (themeViewModel.state.collectAsState().value.theme) {
                    Default -> isSystemInDarkTheme()
                    Dark -> true
                    Light -> false
                }
            ) {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = SplashScreen,
                    builder = {
                        composable<SplashScreen>(
                            content = {
                                val viewModel = hiltViewModel<SplashScreenViewModel>()
                                SplashScreen(
                                    state = viewModel.state.collectAsState().value,
                                    onNavigate = { destination ->
                                        navController.navigate(destination){
                                            popUpTo(
                                                destination,
                                            ) { inclusive = true }
                                        }
                                    }
                                )
                            }
                        )
                        composable<LoginScreen>(
                            content = {
                                val viewModel = hiltViewModel<LoginScreenViewModel>()
                                LoginScreen(
                                    state = viewModel.state.collectAsState().value,
                                    onEvent = viewModel::onEvent,
                                    onNavigate = { destination ->
                                        navController.navigate(destination) {
                                            popUpTo(
                                                destination,
                                            ) { inclusive = true }
                                        }
                                    }
                                )
                            }
                        )
                        composable<DashboardScreen>(
                            content = {
                                val viewModel = hiltViewModel<DashboardScreenViewModel>()
                                val connectionStatus =
                                    viewModel.connectionStatus.collectAsState().value.connectionStatus
                                DashboardScreen(
                                    state = viewModel.state.collectAsState().value,
                                    onEvent = viewModel::onEvent,
                                    connectionStatus = connectionStatus,
                                    onNavigate = { destination ->
                                        navController.navigate(destination)
                                    }
                                )
                            }
                        )
                        composable<MaterialScreen>(
                            content = {
                                val viewModel = hiltViewModel<MaterialQrScreenViewModel>()
                                MaterialQrScreen(
                                    state = viewModel.state.collectAsState().value,
                                    onEvent = viewModel::onEvent,
                                    onNavigateBack = { destination ->
                                        if (destination == DashboardScreen) {
                                            navController.popBackStack()
                                        } else navController.navigate(destination) {
                                            popUpTo(
                                                destination,
                                            ) { inclusive = true }
                                        }
                                    }
                                )
                            }
                        )
                        composable<ProfileScreen>(
                            content = {
                                val viewModel = hiltViewModel<ProfileScreenViewModel>()
                                ProfileScreen(
                                    state = viewModel.state.collectAsState().value,
                                    onEvent = viewModel::onEvent,
                                    onNavigateBack = { destination ->
                                        if (destination == DashboardScreen) {
                                            navController.popBackStack()
                                        } else navController.navigate(destination) {
                                            popUpTo(
                                                destination,
                                            ) { inclusive = true }
                                        }
                                    }
                                )
                            }
                        )
                        composable<GasTruckScreen>(
                            content = {
                                val viewModel = hiltViewModel<TruckFuelQrScreenViewModel>()
                                TruckFuelQrScreen(
                                    state = viewModel.state.collectAsState().value,
                                    onEvent = viewModel::onEvent,
                                    connectionStatus = viewModel._connectionStatus.collectAsState().value.connectionStatus,
                                    onNavigateBack = { destination ->
                                        if (destination == DashboardScreen) {
                                            navController.popBackStack()
                                        } else navController.navigate(destination) {
                                            popUpTo(destination) { inclusive = true }
                                        }
                                    }
                                )
                            }
                        )
                        composable<GasHeavyVehicleScreen>(
                            content = {
                                val viewModel = hiltViewModel<HeavyVehicleFuelQrScreenViewModel>()
                                HeavyVehicleFuelQrScreen(
                                    state = viewModel.state.collectAsState().value,
                                    onEvent = viewModel::onEvent,
                                    onNavigateBack = { destination ->
                                        if (destination == DashboardScreen) {
                                            navController.popBackStack()
                                        } else navController.navigate(destination) {
                                            popUpTo(destination) { inclusive = true }
                                        }
                                    },
                                    connectionStatus = viewModel.connectionStatus.collectAsState().value.connectionStatus
                                )
                            }
                        )
                        composable<StationScreen>(
                            content = {
                                val viewModel = hiltViewModel<StationQrScreenViewModel>()
                                StationQrScreen(
                                    state = viewModel.state.collectAsState().value,
                                    connectionStatus = viewModel.state.collectAsState().value.connectionStatus,
                                    onNavigateBack = {
                                        navController.popBackStack()
                                    },
                                    onEvent = viewModel::onEvent
                                )
                            }
                        )
                    }
                )
            }
        }
    }
}
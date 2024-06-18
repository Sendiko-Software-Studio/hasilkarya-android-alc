package com.system.alecsys.settings.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.system.alecsys.core.navigation.DashboardScreen
import com.system.alecsys.core.navigation.SplashScreen
import com.system.alecsys.core.ui.components.ContentBoxWithNotification
import com.system.alecsys.core.ui.theme.AppTheme
import com.system.alecsys.core.ui.theme.poppinsFont

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    state: SettingsScreenState,
    onEvent: (SettingsScreenEvent) -> Unit,
    onNavigateBack: (destinations: Any) -> Unit
) {
    var isShowingThemeOptions by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(
        key1 = state,
        block = {
            if (state.isPostSuccessful) {
                onNavigateBack(SplashScreen)
            }
        }
    )
    ContentBoxWithNotification(
        message = state.notificationMessage,
        isLoading = state.isLoading,
        isErrorNotification = state.isRequestFailed.isFailed,
        content = {
            val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
            Scaffold(
                topBar = {
                    LargeTopAppBar(
                        title = { Text(text = "Pengaturan", fontFamily = poppinsFont) },
                        navigationIcon = {
                            IconButton(
                                onClick = { onNavigateBack(DashboardScreen) },
                                content = {
                                    Icon(
                                        imageVector = Icons.Default.ArrowBack,
                                        contentDescription = "kembali"
                                    )
                                }
                            )
                        },
                        scrollBehavior = scrollBehavior
                    )
                }
            ) {
                LazyColumn(
                    modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                    contentPadding = it,
                    content = {
                        item {
                            Card(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.SpaceBetween,
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 16.dp, vertical = 16.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(text = "Nama: ", fontFamily = poppinsFont)
                                        Text(text = state.name, fontFamily = poppinsFont)
                                    }
                                    HorizontalDivider()
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 16.dp, vertical = 8.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(text = "Email: ", fontFamily = poppinsFont)
                                        Text(
                                            text = state.email,
                                            fontFamily = poppinsFont,
                                            textAlign = TextAlign.End
                                        )
                                    }
                                    HorizontalDivider()
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 16.dp, vertical = 8.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(text = "Tema Aplikasi: ", fontFamily = poppinsFont)
                                    }
                                    Row(
                                        horizontalArrangement = Arrangement.SpaceEvenly,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        AppTheme.entries.forEach { theme ->
                                            InputChip(
                                                selected = theme == state.theme,
                                                onClick = { onEvent(SettingsScreenEvent.OnThemeChanged(theme)) },
                                                label = {
                                                    Text(
                                                        modifier = Modifier
                                                            .padding(
                                                                vertical = 4.dp,
                                                                horizontal = 8.dp
                                                            ),
                                                        text = theme.name,
                                                        fontFamily = poppinsFont
                                                    )
                                                },
                                                colors = InputChipDefaults.inputChipColors(
                                                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                                                    labelColor = MaterialTheme.colorScheme.onTertiaryContainer,
                                                    selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                                                    selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                                                )
                                            )
                                        }
                                    }
                                    HorizontalDivider()
                                    Button(
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = MaterialTheme.colorScheme.error,
                                            contentColor = MaterialTheme.colorScheme.onError,
                                        ),
                                        onClick = { onEvent(SettingsScreenEvent.OnLogout) },
                                        content = {
                                            Text(
                                                text = "Logout",
                                                fontFamily = poppinsFont
                                            )
                                        },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp)
                                    )
                                }
                            }
                        }
                        item {
                            val uriHandler = LocalUriHandler.current
                            Text(
                                text = "alc-1.1.2",
                                fontFamily = poppinsFont,
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth()
                                    .clickable { uriHandler.openUri("https://github.com/Sendiko-Software-Studio") },
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                )
            }
        }
    )

}
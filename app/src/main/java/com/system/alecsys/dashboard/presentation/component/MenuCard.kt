package com.system.alecsys.dashboard.presentation.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.system.alecsys.core.ui.theme.poppinsFont

@Composable
fun MenuCard(
    modifier: Modifier = Modifier,
    onClickAction: () -> Unit,
    text: String,
    icon: Painter,
    enabled: Boolean = true,
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = if (enabled) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.surfaceVariant,
            contentColor = if (enabled) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.surfaceContainerLow
        ),
        onClick = {
            if (enabled) onClickAction()
        },
        content = {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(24.dp)
            ) {
                Icon(
                    painter = icon,
                    contentDescription = text,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = text,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    fontFamily = poppinsFont,
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuCardExpendable(
    modifier: Modifier = Modifier,
    onClickAction1: () -> Unit,
    onClickAction2: () -> Unit,
    text: String,
    icon: Painter,
) {
    var isExpanded by remember {
        mutableStateOf(false)
    }
    Card(
        modifier = modifier,
        onClick = { isExpanded = !isExpanded },
        content = {
            AnimatedVisibility(
                visible = !isExpanded,
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(24.dp),
                ) {
                    Icon(
                        painter = icon,
                        contentDescription = text,
                        modifier = Modifier.size(128.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = text,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center,
                        fontFamily = poppinsFont,
                        modifier = Modifier.width(128.dp)
                    )
                }
            }
            AnimatedVisibility(
                visible = isExpanded,
                content = {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(24.dp),
                    ) {
                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1.5f),
                            onClick = { onClickAction1() },
                            shape = RoundedCornerShape(8.dp),
                            content = {
                                Column(
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "Catat Pengisian BBM",
                                        fontFamily = poppinsFont,
                                        fontSize = 20.sp
                                    )
                                    Text(
                                        text = "Truck",
                                        fontFamily = poppinsFont,
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight.Black
                                    )
                                }
                            }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            modifier = Modifier
                                .fillMaxWidth(1f)
                                .aspectRatio(1.5f),
                            onClick = { onClickAction2() },
                            shape = RoundedCornerShape(8.dp),
                            content = {
                                Column(
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "Catat Pengisian BBM",
                                        fontFamily = poppinsFont,
                                        fontSize = 20.sp
                                    )
                                    Text(
                                        text = "Alat Berat",
                                        fontFamily = poppinsFont,
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight.Black
                                    )
                                }
                            },
                            enabled = true
                        )
                    }
                }
            )
        }
    )
}
package com.system.alecsys.login.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.system.alecsys.R
import com.system.alecsys.core.navigation.DashboardScreen
import com.system.alecsys.core.ui.components.ContentBoxWithNotification
import com.system.alecsys.core.ui.components.NormalTextField
import com.system.alecsys.core.ui.components.PasswordTextField
import com.system.alecsys.core.ui.theme.poppinsFont
import kotlinx.coroutines.delay

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoginScreen(
    state: LoginScreenState,
    onEvent: (LoginScreenEvent) -> Unit,
    onNavigate: (destination: Any) -> Unit
) {
    LaunchedEffect(
        key1 = state,
        block = {
            if (state.isLoginSuccessful)
                onNavigate(DashboardScreen)

            if (state.isRequestFailed.isFailed){
                delay(1000)
                onEvent(LoginScreenEvent.OnClearNotification)
            }

            if (state.notificationMessage.isNotBlank()){
                delay(2000)
                onEvent(LoginScreenEvent.OnClearNotification)
            }
        }
    )
   ContentBoxWithNotification(
       message = state.notificationMessage,
       isErrorNotification = state.isRequestFailed.isFailed,
       isLoading = state.isLoading,
       content = {
           Scaffold {
               Column(
                   modifier = Modifier
                       .fillMaxSize()
                       .padding(horizontal = 16.dp),
                   verticalArrangement = Arrangement.Center,
                   horizontalAlignment = Alignment.CenterHorizontally
               ) {
                   Icon(
                       painter = painterResource(id = R.drawable.alecsys_icon),
                       contentDescription = null,
                       modifier = Modifier.size(256.dp),
                       tint = MaterialTheme.colorScheme.onBackground
                   )
                   Text(
                       text = "Login",
                       fontSize = 24.sp,
                       modifier = Modifier.padding(vertical = 8.dp).fillMaxWidth(),
                       fontWeight = FontWeight.Bold,
                       fontFamily = poppinsFont,
                       textAlign = TextAlign.Start
                   )
                   Text(
                       text = "Silahkan login menggunakan email dan password.",
                       fontSize = 16.sp,
                       modifier = Modifier.padding(vertical = 8.dp),
                       fontFamily = poppinsFont
                   )
                   Spacer(modifier = Modifier.size(8.dp))
                   NormalTextField(
                       modifier = Modifier.fillMaxWidth(),
                       value = state.emailText,
                       leadingIcon = Icons.Default.Email,
                       hint = "Contoh: contoh@gmail.com",
                       errorState = state.emailErrorState,
                       onNewValue = {
                           onEvent(LoginScreenEvent.OnEmailChange(it))
                       },
                       onClearText = {
                           onEvent(LoginScreenEvent.OnEmailClear)
                       },
                       keyboardType = KeyboardType.Email
                   )
                   PasswordTextField(
                       modifier = Modifier.fillMaxWidth(),
                       value = state.passwordText,
                       leadingIcon = Icons.Default.Lock,
                       isVisible = state.isPasswordVisible,
                       hint = "Masukkan password anda",
                       errorState = state.passwordErrorState,
                       onNewValue = {
                           onEvent(LoginScreenEvent.OnPasswordChange(it))
                       },
                       onVisibiltyToggle = {
                           onEvent(LoginScreenEvent.OnPasswordVisibilityChange(it))
                       }
                   )
                   Spacer(modifier = Modifier.size(4.dp))
                   Button(
                       modifier = Modifier.fillMaxWidth(),
                       onClick = { onEvent(LoginScreenEvent.OnLoginClick) },
                       content = {
                           Text(text = "Login", fontFamily = poppinsFont)
                       }
                   )
               }
           }
       }
   )
}
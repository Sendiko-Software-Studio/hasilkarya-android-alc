package com.system.alecsys.core.ui.components

import android.util.Log
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.system.alecsys.core.ui.theme.poppinsFont
import com.system.alecsys.core.ui.utils.ErrorTextField

@Composable
fun PasswordTextField(
    modifier: Modifier = Modifier,
    value: String,
    onNewValue: (String) -> Unit,
    leadingIcon: ImageVector,
    hint: String = "",
    isVisible: Boolean = false,
    onVisibiltyToggle: (Boolean) -> Unit,
    errorState: ErrorTextField = ErrorTextField()
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        textStyle = TextStyle(fontFamily = poppinsFont),
        onValueChange = { onNewValue(it) },
        shape = CircleShape,
        placeholder = {
            Text(text = hint, fontFamily = poppinsFont)
        },
        leadingIcon = {
            Icon(imageVector = leadingIcon, contentDescription = null)
        },
        trailingIcon = {
            IconButton(
                onClick = {
                    Log.i("PASSWORD_VISIBILIY", "PasswordTextField: ${!isVisible}")
                    onVisibiltyToggle(!isVisible)
                          },
                content = {
                    Icon(
                        imageVector = if (isVisible)
                            Icons.Default.VisibilityOff
                        else Icons.Default.Visibility,
                        contentDescription = "clear"
                    )
                }
            )
        },
        visualTransformation = if (isVisible) VisualTransformation.None else PasswordVisualTransformation(),
        supportingText = {
            Text(text = errorState.errorMessage, fontFamily = poppinsFont)
        },
        isError = errorState.isError,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
    )
}
package com.system.alecsys.core.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import com.system.alecsys.core.ui.theme.poppinsFont
import com.system.alecsys.core.ui.utils.ErrorTextField

@Composable
fun NormalTextField(
    modifier: Modifier = Modifier,
    value: String,
    onNewValue: (String) -> Unit,
    leadingIcon: ImageVector,
    hint: String = "",
    onClearText: () -> Unit,
    errorState: ErrorTextField = ErrorTextField(),
    keyboardType: KeyboardType = KeyboardType.Text,
    shape: Shape = CircleShape,
    singleLine: Boolean = true,
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        textStyle = TextStyle(fontFamily = poppinsFont),
        onValueChange = { onNewValue(it) },
        shape = shape,
        placeholder = {
            Text(text = hint, fontFamily = poppinsFont)
        },
        leadingIcon = {
            Icon(imageVector = leadingIcon, contentDescription = null)
        },
        trailingIcon = {
            AnimatedVisibility(
                visible = value.isNotBlank(),
                enter = fadeIn(),
                exit = fadeOut(),
                content = {
                    IconButton(
                        onClick = { onClearText() },
                        content = {
                            Icon(imageVector = Icons.Default.Clear, contentDescription = "clear")
                        }
                    )
                }
            )
        },
        supportingText = {
            if (errorState.isError) Text(text = errorState.errorMessage, fontFamily = poppinsFont)
        },
        isError = errorState.isError,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        singleLine = singleLine
    )
}
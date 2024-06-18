package com.system.alecsys.core.ui.utils

/**
 *
 * Error States for TextField.
 *
 * @param isError to pass to TextField's isError.
 * @param errorMessage can be passed to TextField's supportingText.
 *
 * */
data class ErrorTextField(
    val isError: Boolean = false,
    val errorMessage: String = "",
)

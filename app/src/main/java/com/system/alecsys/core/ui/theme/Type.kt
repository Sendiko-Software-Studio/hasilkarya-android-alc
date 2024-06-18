package com.system.alecsys.core.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.system.alecsys.R

val poppinsFont = FontFamily(
    fonts = listOf(
        Font(
            resId = R.font.poppins_black,
            weight = FontWeight.Black
        ),
        Font(
            resId = R.font.poppins_blackitalic,
            weight = FontWeight.Black,
            style = FontStyle.Italic
        ),
        Font(
            resId = R.font.poppins_extrabold,
            weight = FontWeight.ExtraBold
        ),
        Font(
            resId = R.font.poppins_extrabold,
            weight = FontWeight.ExtraBold,
            style = FontStyle.Italic
        ),
        Font(
            resId = R.font.poppins_bold,
            weight = FontWeight.Bold
        ),
        Font(
            resId = R.font.poppins_bolditalic,
            weight = FontWeight.Black,
            style = FontStyle.Italic
        ),
        Font(
            resId = R.font.poppins_medium,
            weight = FontWeight.Medium
        ),
        Font(
            resId = R.font.poppins_mediumitalic,
            weight = FontWeight.Medium,
            style = FontStyle.Italic
        ),
        Font(
            resId = R.font.poppins_semibold,
            weight = FontWeight.SemiBold
        ),
        Font(
            resId = R.font.poppins_semibolditalic,
            weight = FontWeight.SemiBold,
            style = FontStyle.Italic
        ),
        Font(
            resId = R.font.poppins_regular,
            weight = FontWeight.Normal
        ),
        Font(
            resId = R.font.poppins_italic,
            weight = FontWeight.Normal,
            style = FontStyle.Italic
        ),
        Font(
            resId = R.font.poppins_light,
            weight = FontWeight.Light
        ),
        Font(
            resId = R.font.poppins_lightitalic,
            weight = FontWeight.Black,
            style = FontStyle.Italic
        ),
        Font(
            resId = R.font.poppins_extralight,
            weight = FontWeight.ExtraLight
        ),
        Font(
            resId = R.font.poppins_extralightitalic,
            weight = FontWeight.ExtraLight,
            style = FontStyle.Italic
        ),
        Font(
            resId = R.font.poppins_thin,
            weight = FontWeight.Thin
        ),
        Font(
            resId = R.font.poppins_thinitalic,
            weight = FontWeight.Thin,
            style = FontStyle.Italic
        ),
    )
)


// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)
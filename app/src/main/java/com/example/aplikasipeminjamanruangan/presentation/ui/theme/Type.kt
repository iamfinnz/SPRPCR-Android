package com.example.aplikasipeminjamanruangan.presentation.ui.theme

import androidx.compose.material.Text
import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.aplikasipeminjamanruangan.R

// Setting font
val AvenirFontFamily = FontFamily(
    Font(R.font.avenir_bold, FontWeight.Bold),
    Font(R.font.avenir_demi, FontWeight.SemiBold),
    Font(R.font.avenir_medium, FontWeight.Medium),
    Font(R.font.avenir_regular, FontWeight.Normal)
)

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = AvenirFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),

    h2 = TextStyle(
        fontFamily = AvenirFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),

    h3 = TextStyle(
        fontFamily = AvenirFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    ),

    caption = TextStyle(
        fontFamily = AvenirFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )

)
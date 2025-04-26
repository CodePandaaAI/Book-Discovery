package com.pandawork.bookdiscovery.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.pandawork.bookdiscovery.R

// Set of Material typography styles to start with
val Nunito = FontFamily(
    Font(R.font.nunito_regular),
    Font(R.font.nunito_light),
    Font(R.font.nunito_semi_bold)
)

val RobotoSerif = FontFamily(
    Font(R.font.roboto_serif_regular),
    Font(R.font.roboto_serif_bold, FontWeight.Bold),
    Font(R.font.roboto_serif_thin, FontWeight.Thin),
    Font(R.font.roboto_serif_extra_bold, FontWeight.ExtraBold)
)

val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = RobotoSerif,
        fontWeight = FontWeight.Normal,
        fontSize = 36.sp
    ),
    titleMedium = TextStyle(
        fontFamily = RobotoSerif,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp
    ),
    displayMedium = TextStyle(
        fontFamily = RobotoSerif,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    ),
    labelSmall = TextStyle(
        fontFamily = Nunito,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = Nunito,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    )
)
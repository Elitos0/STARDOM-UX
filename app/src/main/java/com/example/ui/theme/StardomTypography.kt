package com.example.ui.theme

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.example.R

val SpaceGrotesk = FontFamily(
    Font(
        resId = R.font.space_grotesk_regular,
        weight = FontWeight.Normal
    ),
    Font(
        resId = R.font.space_grotesk_medium,
        weight = FontWeight.Medium
    ),
    Font(
        resId = R.font.space_grotesk_medium,
        weight = FontWeight.SemiBold
    )
)

val IbmPlexMono = FontFamily(
    Font(
        resId = R.font.ibm_plex_mono_regular,
        weight = FontWeight.Normal
    ),
    Font(
        resId = R.font.ibm_plex_mono_medium,
        weight = FontWeight.Medium
    )
)
package com.example.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

private val StardomDarkColorScheme =
  darkColorScheme(
    primary = StardomColors.TextPrimary,
    onPrimary = StardomColors.Background,
    primaryContainer = StardomColors.PanelSelected,
    onPrimaryContainer = StardomColors.TextPrimary,
    secondary = StardomColors.TextSecondary,
    onSecondary = StardomColors.Background,
    secondaryContainer = StardomColors.Panel,
    onSecondaryContainer = StardomColors.TextSecondary,
    tertiary = StardomColors.TextPrimary,
    onTertiary = StardomColors.Background,
    background = StardomColors.Background,
    onBackground = StardomColors.TextPrimary,
    surface = StardomColors.Panel,
    onSurface = StardomColors.TextPrimary,
    surfaceVariant = StardomColors.Panel,
    onSurfaceVariant = StardomColors.TextSecondary,
    outline = StardomColors.Border,
    outlineVariant = StardomColors.BorderFaint
  )

// Strict geometric shapes: razor-sharp corners (0dp)
val StardomShapes = Shapes(
  extraSmall = RoundedCornerShape(0.dp),
  small = RoundedCornerShape(0.dp),
  medium = RoundedCornerShape(0.dp),
  large = RoundedCornerShape(0.dp),
  extraLarge = RoundedCornerShape(0.dp)
)

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = true,
  dynamicColor: Boolean = false,
  content: @Composable () -> Unit,
) {
  MaterialTheme(
    colorScheme = StardomDarkColorScheme,
    shapes = StardomShapes,
    typography = Typography,
    content = content
  )
}

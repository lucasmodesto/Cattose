package br.com.cattose.app.core.ui.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

private const val HALF_VALUE_DIVIDER = 2

@Composable
fun halfScreenHeightDp(): Dp {
    return LocalConfiguration.current.screenHeightDp.dp.div(HALF_VALUE_DIVIDER)
}

@Composable
fun halfScreenWidthDp(): Dp {
    return LocalConfiguration.current.screenWidthDp.dp.div(HALF_VALUE_DIVIDER)
}

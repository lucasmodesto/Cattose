package br.com.cattose.app.core.ui.preview

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.Composable
import br.com.cattose.app.core.ui.theme.CattoTheme

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionPreviewTheme(
    content: @Composable SharedTransitionScope.(AnimatedVisibilityScope) -> Unit
) {
    CattoTheme {
        SharedTransitionLayout {
            AnimatedVisibility(visible = true, label = "") {
                content(this)
            }
        }
    }
}

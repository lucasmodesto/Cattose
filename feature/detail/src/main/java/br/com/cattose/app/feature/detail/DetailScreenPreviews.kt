@file:OptIn(ExperimentalSharedTransitionApi::class)

package br.com.cattose.app.feature.detail

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import br.com.cattose.app.core.ui.preview.SharedTransitionPreviewTheme
import br.com.cattose.app.data.model.domain.Breed
import br.com.cattose.app.data.model.domain.CatDetails

@PreviewLightDark
@PreviewScreenSizes
@Composable
fun DetailScreenSuccessPreview() {
    val catDetails = CatDetails(
        "id",
        "https://cdn2.thecatapi.com/images/zKO1twSOV.jpg",
        mainBreed = Breed(
            "Norwegian Forest Cat",
            temperaments = listOf(
                "Sweet",
                "Active",
                "Intelligent",
                "Social",
                "Playful",
                "Lively",
                "Curious"
            ),
            description = "The Norwegian Forest Cat is a sweet, " +
                    "loving cat. She appreciates praise and loves to interact with her parent. " +
                    "She makes a loving companion and bonds with her parents once she accepts them for her own. " +
                    "She is still a hunter at heart. She loves to chase toys as if they are real. " +
                    "She is territorial and patrols several times each day to make certain that all is fine."
        )
    )

    SharedTransitionPreviewTheme {
        Surface {
            DetailsScreenContent(
                state = DetailState(
                    catDetails = catDetails,
                    isLoading = false,
                    hasError = false,
                    catImageUrl = catDetails.imageUrl
                ),
                onBackClick = {},
                onTryAgainClick = {},
                animatedVisibilityScope = it
            )
        }
    }
}


@PreviewLightDark
@PreviewScreenSizes
@Composable
fun DetailScreenErrorPreview() {
    SharedTransitionPreviewTheme {
        Surface {
            DetailsScreenContent(
                state = DetailState(
                    isLoading = false,
                    hasError = true,
                    catImageUrl = "https://cdn2.thecatapi.com/images/zKO1twSOV.jpg"
                ),
                onBackClick = {},
                onTryAgainClick = {},
                animatedVisibilityScope = it
            )
        }
    }
}

@PreviewLightDark
@PreviewScreenSizes
@Composable
fun DetailScreenLoadingPreview() {
    SharedTransitionPreviewTheme {
        Surface {
            DetailsScreenContent(
                state = DetailState(
                    isLoading = true,
                    hasError = false,
                    catImageUrl = "https://cdn2.thecatapi.com/images/zKO1twSOV.jpg"
                ),
                onBackClick = {},
                onTryAgainClick = {},
                animatedVisibilityScope = it
            )
        }
    }
}

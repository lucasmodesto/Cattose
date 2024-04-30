@file:OptIn(ExperimentalSharedTransitionApi::class)

package br.com.cattose.app.feature.detail

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.cattose.app.core.ui.R
import br.com.cattose.app.core.ui.error.TryAgain
import br.com.cattose.app.core.ui.image.DefaultAsyncImage
import br.com.cattose.app.core.ui.tags.TagList
import br.com.cattose.app.core.ui.theme.CattoTheme
import br.com.cattose.app.core.ui.util.halfScreenHeightDp
import br.com.cattose.app.core.ui.util.halfScreenWidthDp
import br.com.cattose.app.data.model.domain.Breed
import br.com.cattose.app.data.model.domain.CatDetails

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.DetailScreen(
    onBackClick: () -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState()

    DetailsScreenContent(
        state = state.value,
        onBackClick = onBackClick,
        onTryAgainClick = {
            viewModel.fetchDetails()
        },
        animatedVisibilityScope = animatedVisibilityScope,
    )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.DetailsScreenContent(
    state: DetailState,
    onBackClick: () -> Unit,
    onTryAgainClick: () -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE

    Row(modifier = Modifier.fillMaxSize()) {
        if (isLandscape) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                Box(modifier = Modifier) {
                    DefaultAsyncImage(
                        imageUrl = state.catImageUrl,
                        contentScale = ContentScale.Crop,
                        transformations = listOf(),
                        modifier = Modifier
                            .width(halfScreenWidthDp())
                            .fillMaxHeight()
                            .align(Alignment.TopStart)
                            .sharedBounds(
                                sharedContentState = rememberSharedContentState(key = state.catImageUrl),
                                animatedVisibilityScope = animatedVisibilityScope,
                                placeHolderSize = SharedTransitionScope.PlaceHolderSize.animatedSize,
                                renderInOverlayDuringTransition = false
                            )
                            .clip(
                                RoundedCornerShape(
                                    bottomStart = 8.dp,
                                    bottomEnd = 8.dp
                                )
                            )
                            .testTag(DetailTestTags.IMAGE)
                    )
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.content_description_back_button),
                        modifier = Modifier
                            .clickable {
                                onBackClick()
                            }
                            .padding(24.dp)
                            .testTag(DetailTestTags.BACK_BUTTON)
                    )
                }
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                state.catDetails?.mainBreed?.let {
                    BreedDetailsColumn(
                        breed = it,
                        modifier = Modifier
                            .padding(bottom = 16.dp)
                            .testTag(DetailTestTags.BREED_DETAILS)
                    )
                }

                if (state.isLoading) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .testTag(DetailTestTags.LOADING),
                        contentAlignment = Alignment.Center,
                    ) {
                        CircularProgressIndicator()
                    }
                }

                if (state.hasError) {
                    TryAgain(
                        message = stringResource(id = br.com.cattose.app.feature.detail.R.string.error_detail_loading),
                        tryAgainActionText = stringResource(id = R.string.action_retry),
                        onTryAgainClick = onTryAgainClick,
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f)
                    )
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
            ) {
                Box(modifier = Modifier) {
                    DefaultAsyncImage(
                        imageUrl = state.catImageUrl,
                        contentScale = ContentScale.Crop,
                        transformations = listOf(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(halfScreenHeightDp())
                            .sharedBounds(
                                sharedContentState = rememberSharedContentState(key = state.catImageUrl),
                                animatedVisibilityScope = animatedVisibilityScope,
                                placeHolderSize = SharedTransitionScope.PlaceHolderSize.animatedSize,
                                renderInOverlayDuringTransition = false
                            )
                            .clip(
                                RoundedCornerShape(
                                    bottomStart = 8.dp,
                                    bottomEnd = 8.dp
                                )
                            )
                            .testTag(DetailTestTags.IMAGE)
                    )
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.content_description_back_button),
                        modifier = Modifier
                            .clickable {
                                onBackClick()
                            }
                            .padding(24.dp)
                            .testTag(DetailTestTags.BACK_BUTTON)
                    )
                }

                state.catDetails?.mainBreed?.let {
                    BreedDetailsColumn(
                        breed = it,
                        modifier = Modifier
                            .padding(bottom = 16.dp)
                            .testTag(DetailTestTags.BREED_DETAILS)
                    )
                }

                if (state.isLoading) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .testTag(DetailTestTags.LOADING),
                        contentAlignment = Alignment.Center,
                    ) {
                        CircularProgressIndicator()
                    }
                }

                if (state.hasError) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        TryAgain(
                            message = stringResource(id = br.com.cattose.app.feature.detail.R.string.error_detail_loading),
                            tryAgainActionText = stringResource(id = R.string.action_retry),
                            onTryAgainClick = onTryAgainClick
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BreedDetailsColumn(
    breed: Breed,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Spacer(modifier = Modifier.height(16.dp))
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text(
                text = breed.name,
                style = MaterialTheme.typography.headlineMedium
            )
            if (breed.temperaments.isNotEmpty()) {
                TagList(tags = breed.temperaments)
            }
            if (breed.description.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = breed.description,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@PreviewLightDark
@PreviewScreenSizes
@Composable
private fun DetailScreenPreview() {
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

    CattoTheme {
        SharedTransitionScope {
            AnimatedVisibility(visible = true, label = "") {
                DetailsScreenContent(
                    state = DetailState(catDetails = catDetails),
                    onBackClick = {},
                    onTryAgainClick = {},
                    animatedVisibilityScope = this
                )
            }
        }
    }
}

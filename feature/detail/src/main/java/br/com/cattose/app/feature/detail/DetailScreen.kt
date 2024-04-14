package br.com.cattose.app.feature.detail

import android.content.res.Configuration
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.cattose.app.core.ui.R
import br.com.cattose.app.core.ui.error.TryAgain
import br.com.cattose.app.core.ui.image.DefaultAsyncImage
import br.com.cattose.app.core.ui.image.ImagePlaceholder
import br.com.cattose.app.core.ui.tags.TagList
import br.com.cattose.app.core.ui.util.halfScreenHeightDp
import br.com.cattose.app.core.ui.util.halfScreenWidthDp
import br.com.cattose.app.data.model.domain.Breed
import br.com.cattose.app.data.model.domain.CatDetails

@Composable
fun DetailScreen(
    onBackClick: () -> Unit,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    DetailsScreenContent(
        state = state.value,
        onBackClick = onBackClick,
        onTryAgainClick = {
            viewModel.fetchDetails()
        }
    )
}

@Composable
fun DetailsScreenContent(
    state: DetailState,
    onBackClick: () -> Unit,
    onTryAgainClick: () -> Unit
) {
    when (state) {
        is DetailState.Success -> {
            val configuration = LocalConfiguration.current
            if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                LandscapeDetailScreenContent(
                    state = state,
                    onBackClick = onBackClick
                )
            } else {
                val scrollState = rememberScrollState()
                Column(
                    modifier = Modifier
                        .verticalScroll(scrollState)
                        .padding(bottom = 16.dp)
                ) {
                    Box(
                        modifier = Modifier
                    ) {
                        DefaultAsyncImage(
                            imageUrl = state.cat.imageUrl,
                            contentScale = ContentScale.Crop,
                            transformations = listOf(),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(halfScreenHeightDp())
                                .align(Alignment.TopStart)
                                .testTag(state.cat.imageUrl),
                            errorPlaceholder = {
                                ImagePlaceholder(
                                    drawable = R.drawable.cat_placeholder,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(300.dp)
                                )
                            },
                            loadingPlaceholder = {
                                ImagePlaceholder(
                                    drawable = R.drawable.cat_placeholder,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(halfScreenHeightDp())
                                )
                            }
                        )
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.content_description_back_button),
                            modifier = Modifier
                                .clickable {
                                    onBackClick()
                                }
                                .padding(16.dp)
                        )
                    }
                    state.cat.mainBreed?.let {
                        BreedDetailsColumn(
                            breed = it,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                    }
                }
            }
        }

        DetailState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .testTag("loading"),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        DetailState.Error -> {
            TryAgain(
                message = stringResource(id = br.com.cattose.app.feature.detail.R.string.error_detail_loading),
                tryAgainActionText = stringResource(id = R.string.action_retry),
                onTryAgainClick = onTryAgainClick,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
fun LandscapeDetailScreenContent(
    state: DetailState.Success,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
    ) {
        Row {
            Box(
                modifier = Modifier
            ) {
                DefaultAsyncImage(
                    imageUrl = state.cat.imageUrl,
                    contentScale = ContentScale.Crop,
                    transformations = listOf(),
                    modifier = Modifier
                        .width(halfScreenWidthDp())
                        .fillMaxHeight()
                        .align(Alignment.TopStart)
                        .testTag(state.cat.imageUrl),
                    errorPlaceholder = {
                        ImagePlaceholder(
                            drawable = R.drawable.cat_placeholder,
                            modifier = Modifier
                                .width(halfScreenWidthDp())
                                .fillMaxHeight()
                        )
                    },
                    loadingPlaceholder = {
                        ImagePlaceholder(
                            drawable = R.drawable.cat_placeholder,
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(halfScreenWidthDp())
                        )
                    }
                )
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(id = R.string.content_description_back_button),
                    modifier = Modifier
                        .clickable {
                            onBackClick()
                        }
                        .padding(16.dp),
                    tint = Color.White
                )
            }
            state.cat.mainBreed?.let {
                BreedDetailsColumn(
                    breed = it,
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .verticalScroll(rememberScrollState())
                )
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

@Preview
@Composable
fun DetailScreenPreview() {
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
    DetailsScreenContent(
        state = DetailState.Success(catDetails),
        onBackClick = {},
        onTryAgainClick = {})
}

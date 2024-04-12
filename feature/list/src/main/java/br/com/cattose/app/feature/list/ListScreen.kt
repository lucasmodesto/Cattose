package br.com.cattose.app.feature.list

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.cattose.app.core.domain.model.CatImage
import br.com.cattose.app.core.ui.R
import br.com.cattose.app.core.ui.error.TryAgain
import br.com.cattose.app.core.ui.image.DefaultAsyncImage
import br.com.cattose.app.core.ui.image.ImagePlaceholder


@Composable
fun LoginScreen(
    onItemClick: (CatImage) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ListViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    var hasFetched by rememberSaveable { mutableStateOf(false) }

    LoginScreenContent(
        state = state.value,
        onItemClick = onItemClick,
        onTryAgainClick = viewModel::fetchList,
        modifier = modifier
    )

    LaunchedEffect(Unit) {
        if (hasFetched.not()) {
            viewModel.fetchList()
            hasFetched = true
        }
    }
}

@Composable
fun LoginScreenContent(
    state: ListState,
    modifier: Modifier = Modifier,
    onItemClick: (CatImage) -> Unit,
    onTryAgainClick: () -> Unit
) {
    when (state) {
        is ListState.Success -> {
            val configuration = LocalConfiguration.current
            val columns = if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                2
            } else {
                3
            }
            LazyVerticalGrid(
                columns = GridCells.Fixed(columns),
                modifier = modifier.padding(8.dp)
            ) {
                items(state.data, key = { cat ->
                    cat.id
                }) {
                    Box(
                        modifier = Modifier.padding(8.dp)
                    ) {
                        CatListItem(it, { cat ->
                            onItemClick(cat)
                        })
                    }
                }
            }
        }

        ListState.Error -> {
            TryAgain(
                message = stringResource(id = br.com.cattose.app.feature.list.R.string.error_loading_message),
                tryAgainActionText = stringResource(id = R.string.action_retry),
                onTryAgainClick = onTryAgainClick,
                modifier = Modifier.fillMaxSize()
            )
        }

        ListState.Empty -> {
            TryAgain(
                message = stringResource(id = br.com.cattose.app.feature.list.R.string.empty_state_message),
                tryAgainActionText = stringResource(id = R.string.action_retry),
                onTryAgainClick = onTryAgainClick,
                modifier = Modifier.fillMaxSize()
            )
        }

        ListState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize().testTag("loading"),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
fun CatListItem(
    cat: CatImage,
    onItemClick: (CatImage) -> Unit,
    modifier: Modifier = Modifier
) {
    DefaultAsyncImage(
        imageUrl = cat.imageUrl,
        modifier = modifier
            .clickable { onItemClick(cat) }
            .height(200.dp)
            .clip(RoundedCornerShape(8.dp))
            .testTag(cat.id),
        contentScale = ContentScale.Crop,
        errorPlaceholder = {
            ImagePlaceholder(
                drawable = R.drawable.cat_placeholder,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
        },
        loadingPlaceholder = {
            ImagePlaceholder(
                drawable = R.drawable.cat_placeholder,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
        }
    )
}

package br.com.cattose.app.feature.detail

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import br.com.cattose.app.data.model.domain.CatDetails
import br.com.cattose.app.data.repository.CatRepository
import br.com.cattose.app.feature.detail.navigation.CAT_ID_ARG
import br.com.cattose.app.feature.detail.navigation.IMAGE_URL_ARG
import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DetailViewModelTest {

    private val dispatcher = StandardTestDispatcher()
    private val repository = mockk<CatRepository>()
    private val savedStateHandle = mockk<SavedStateHandle>()
    private lateinit var viewModel: DetailViewModel

    @Before
    fun setupMainDispatcher() {
        Dispatchers.setMain(dispatcher)
        every<String?> { savedStateHandle[CAT_ID_ARG] } returns "id"
        every<String?> { savedStateHandle[IMAGE_URL_ARG] } returns "imageUrl"
    }

    @Test
    fun `given cats should emit success state`() = runTest {
        val catDetails = mockk<CatDetails>()
        coEvery {
            repository.getDetails("id")
        } returns flowOf(catDetails)

        viewModel = DetailViewModel(repository, savedStateHandle)

        viewModel.state.test {
            Truth.assertThat(awaitItem())
                .isEqualTo(
                    DetailState(
                        isLoading = true,
                        catImageUrl = "imageUrl"
                    )
                )
            Truth.assertThat(awaitItem()).isEqualTo(
                DetailState(
                    isLoading = false,
                    hasError = false,
                    catImageUrl = "imageUrl",
                    catDetails = catDetails
                )
            )
        }
    }

    @Test
    fun `given error should emit error state`() = runTest {
        coEvery {
            repository.getDetails("id")
        } returns flow { throw Exception() }

        viewModel = DetailViewModel(repository, savedStateHandle)

        viewModel.state.test {
            Truth.assertThat(awaitItem()).isEqualTo(
                DetailState(
                    isLoading = true,
                    hasError = false,
                    catImageUrl = "imageUrl",
                    catDetails = null
                )
            )
            Truth.assertThat(awaitItem()).isEqualTo(
                DetailState(
                    isLoading = false,
                    hasError = true,
                    catImageUrl = "imageUrl",
                    catDetails = null
                )
            )
        }
    }
}

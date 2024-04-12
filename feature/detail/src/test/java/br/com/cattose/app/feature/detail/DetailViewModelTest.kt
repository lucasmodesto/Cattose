package br.com.cattose.app.feature.detail

import app.cash.turbine.test
import br.com.cattose.app.core.domain.model.CatDetails
import br.com.cattose.app.core.domain.repository.CatRepository
import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DetailViewModelTest {

    private val dispatcher = UnconfinedTestDispatcher()
    private val repository = mockk<CatRepository>()
    private lateinit var viewModel: DetailViewModel

    @Before
    fun setupMainDispatcher() {
        Dispatchers.setMain(dispatcher)
        viewModel = DetailViewModel(repository)
    }

    @Test
    fun `given cats should emit success state`() = runTest {
        val cats = mockk<CatDetails>()
        coEvery {
            repository.getDetails("id")
        } returns flowOf(cats)

        viewModel.state.test {
            viewModel.fetchDetails("id")
            Truth.assertThat(awaitItem()).isEqualTo(DetailState.Loading)
            Truth.assertThat(awaitItem()).isEqualTo(DetailState.Success(cats))
        }
    }

    @Test
    fun `given error should emit error state`() = runTest {
        coEvery {
            repository.getDetails("id")
        } returns flow { throw Exception() }

        viewModel.state.test {
            viewModel.fetchDetails("id")
            Truth.assertThat(awaitItem()).isEqualTo(DetailState.Loading)
            Truth.assertThat(awaitItem()).isEqualTo(DetailState.Error)
        }
    }
}

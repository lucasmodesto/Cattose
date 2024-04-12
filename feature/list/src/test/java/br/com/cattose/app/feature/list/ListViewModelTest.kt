package br.com.cattose.app.feature.list

import app.cash.turbine.test
import br.com.cattose.app.core.domain.model.CatImage
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
class ListViewModelTest {

    private val dispatcher = UnconfinedTestDispatcher()
    private val repository = mockk<CatRepository>()
    private lateinit var viewModel: ListViewModel

    @Before
    fun setupMainDispatcher() {
        Dispatchers.setMain(dispatcher)
        viewModel = ListViewModel(repository)
    }

    @Test
    fun `given cats should emit success state`() = runTest {
        val cats = listOf(mockk<CatImage>())
        coEvery {
            repository.getCats()
        } returns flowOf(cats)

        viewModel.state.test {
            viewModel.fetchList()
            Truth.assertThat(awaitItem()).isEqualTo(ListState.Loading)
            Truth.assertThat(awaitItem()).isEqualTo(ListState.Success(cats))
        }
    }

    @Test
    fun `given empty cats should emit empty state`() = runTest {
        val cats = listOf<CatImage>()
        coEvery {
            repository.getCats()
        } returns flowOf(cats)

        viewModel.state.test {
            viewModel.fetchList()
            Truth.assertThat(awaitItem()).isEqualTo(ListState.Loading)
            Truth.assertThat(awaitItem()).isEqualTo(ListState.Empty)
        }
    }

    @Test
    fun `given error should emit error state`() = runTest {
        coEvery {
            repository.getCats()
        } returns flow { throw Exception() }

        viewModel = ListViewModel(repository)

        viewModel.state.test {
            viewModel.fetchList()
            Truth.assertThat(awaitItem()).isEqualTo(ListState.Loading)
            Truth.assertThat(awaitItem()).isEqualTo(ListState.Error)
        }
    }
}

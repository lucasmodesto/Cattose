package br.com.cattose.app.feature.list

import androidx.paging.testing.asSnapshot
import br.com.cattose.app.feature.list.repository.FakeCatsRepository
import com.google.common.truth.Truth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ListViewModelTest {

    private val dispatcher = UnconfinedTestDispatcher()
    private val repository = FakeCatsRepository()
    private lateinit var viewModel: ListViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        viewModel = ListViewModel(repository)
    }

    @Test
    fun `should emit items from fake repository`() = runTest {
        val items = viewModel.catsPagingData.asSnapshot()

        Truth.assertThat(repository.list).isEqualTo(items)
    }
}

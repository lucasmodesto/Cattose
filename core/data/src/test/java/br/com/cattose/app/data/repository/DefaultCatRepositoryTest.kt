package br.com.cattose.app.data.repository

import app.cash.turbine.test
import br.com.cattose.app.data.api.CatsApi
import br.com.cattose.app.data.mapper.toDomain
import br.com.cattose.app.data.model.response.CatDetailResponse
import br.com.cattose.app.data.paging.CatsPagingSource
import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class DefaultCatRepositoryTest {

    private val api = mockk<CatsApi>()
    private val repository = DefaultCatRepository(api)

    @Test
    fun `should return cats paging source`() {
        val factory = repository.getCatsPagingFactory().invoke()
        Truth.assertThat(factory).isInstanceOf(CatsPagingSource::class.java)
    }

    @Test
    fun `given response should emit cat details`() = runTest {
        val catDetailsResponse = mockk<CatDetailResponse>(relaxed = true) {
            every { id } returns "id"
        }
        coEvery {
            api.fetchDetails("id")
        } returns catDetailsResponse

        repository.getDetails("id").test {
            val details = awaitItem()
            Truth.assertThat(details).isEqualTo(catDetailsResponse.toDomain())
            awaitComplete()
        }
    }
}

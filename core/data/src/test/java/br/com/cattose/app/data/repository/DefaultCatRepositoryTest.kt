package br.com.cattose.app.data.repository

import app.cash.turbine.test
import br.com.cattose.app.data.api.CatsApi
import br.com.cattose.app.data.mapper.mapToDomain
import br.com.cattose.app.data.mock.catImageResponse
import br.com.cattose.app.data.model.CatDetailResponse
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
    fun `given response should emit cats list`() = runTest {
        val response = listOf(catImageResponse)
        coEvery {
            api.fetchList()
        } returns response

        repository.getCats().test {
            val cats = awaitItem()
            Truth.assertThat(cats).isEqualTo(response.map { it.mapToDomain() })
            awaitComplete()
        }
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
            Truth.assertThat(details).isEqualTo(catDetailsResponse.mapToDomain())
            awaitComplete()
        }
    }
}

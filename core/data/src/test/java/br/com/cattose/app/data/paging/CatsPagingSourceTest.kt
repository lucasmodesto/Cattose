/*
 * Designed and developed by 2024 lucasmodesto (Lucas Modesto)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package br.com.cattose.app.data.paging

import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.testing.TestPager
import br.com.cattose.app.data.api.CatsApi
import br.com.cattose.app.data.mapper.toDomain
import br.com.cattose.app.data.model.response.CatImageResponse
import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.net.UnknownHostException

class CatsPagingSourceTest {

    private val catsApi = mockk<CatsApi>()
    private val pagingSource = CatsPagingSource(catsApi)
    private val pager = TestPager(config = PagingConfig(pageSize = 10), pagingSource)

    private fun getMockResponse(size: Int = 10):  List<CatImageResponse> {
        return mutableListOf<CatImageResponse>().apply {
            repeat(size) {
                add(CatImageResponse("$it", "https://catimage$it.jpg"))
            }
        }.toList()
    }

    @Test
    fun `given refresh should load first page with results`() = runTest {
        val mock = getMockResponse()
        coEvery { catsApi.fetchList(1) } returns mock

        val result = pager.refresh() as PagingSource.LoadResult.Page

        Truth.assertThat(mock.map { it.toDomain() }).isEqualTo(result.data)
    }

    @Test
    fun `given refresh should load first page with empty list`() = runTest {
        coEvery { catsApi.fetchList(1) } returns emptyList()

        val result = pager.refresh() as PagingSource.LoadResult.Page

        Truth.assertThat(result.data).isEmpty()
    }

    @Test
    fun `given refresh should load first page with error`() = runTest {
        coEvery { catsApi.fetchList(1) } throws UnknownHostException()

        val result = pager.refresh() as PagingSource.LoadResult.Error

        Truth.assertThat(result.throwable).isInstanceOf(UnknownHostException::class.java)
    }

    @Test
    fun `given append should load pages`() = runTest {
        val pageMock = getMockResponse()
        coEvery { catsApi.fetchList(any()) } returns pageMock

        val result = with(pager) {
            refresh()
            append()
            append()
        } as PagingSource.LoadResult.Page

        Truth.assertThat(result.nextKey).isEqualTo(4)
        Truth.assertThat(result.data).isEqualTo(pageMock.map { it.toDomain() })
    }

    @Test
    fun `given paging state then should return correct refresh key`() = runTest {
        val mock = getMockResponse()
        coEvery { catsApi.fetchList(1) } returns mock

        val anchorPosition = 5
        val prevKey = 4
        val nextKey = 6

        val pages = listOf(
            PagingSource.LoadResult.Page(
                data = getMockResponse().map { it.toDomain() },
                prevKey = prevKey,
                nextKey = nextKey
            )
        )

        val pagingState = PagingState(
            pages = pages,
            anchorPosition = anchorPosition,
            config = PagingConfig(pageSize = 10),
            leadingPlaceholderCount = 0
        )

        val refreshKey = pagingSource.getRefreshKey(pagingState)

        Truth.assertThat(refreshKey).isEqualTo(prevKey.plus(1))
    }

    @Test
    fun `given null anchor position when getRefreshKey is called then should return null`() = runTest {
        val mock = getMockResponse()
        coEvery { catsApi.fetchList(1) } returns mock

        val prevKey = 4
        val nextKey = 6

        val pages = listOf(
            PagingSource.LoadResult.Page(
                data = getMockResponse().map { it.toDomain() },
                prevKey = prevKey,
                nextKey = nextKey
            )
        )

        val pagingState = PagingState(
            pages = pages,
            anchorPosition = null,
            config = PagingConfig(pageSize = 10),
            leadingPlaceholderCount = 0
        )

        val refreshKey = pagingSource.getRefreshKey(pagingState)

        Truth.assertThat(refreshKey).isNull()
    }
}

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

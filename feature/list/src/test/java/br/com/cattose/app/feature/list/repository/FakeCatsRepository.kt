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

package br.com.cattose.app.feature.list.repository

import androidx.paging.PagingSourceFactory
import androidx.paging.testing.asPagingSourceFactory
import br.com.cattose.app.data.model.domain.CatDetails
import br.com.cattose.app.data.model.domain.CatImage
import br.com.cattose.app.data.repository.CatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeCatsRepository : CatRepository {

    private val catImage = CatImage("1", "https://image.jpg")
    val list = mutableListOf<CatImage>()
    override fun getCatsPagingFactory(): PagingSourceFactory<Int, CatImage> {
        repeat(10) {
            list.add(catImage)
        }
        return list.asPagingSourceFactory()
    }

    override fun getDetails(id: String): Flow<CatDetails> =
        flowOf(
            CatDetails(
                id = "id",
                mainBreed = null,
                imageUrl = "url"
            )
        )
}
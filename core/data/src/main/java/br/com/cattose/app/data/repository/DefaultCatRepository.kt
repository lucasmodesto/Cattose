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

import androidx.paging.PagingSourceFactory
import br.com.cattose.app.data.api.CatsApi
import br.com.cattose.app.data.mapper.toDomain
import br.com.cattose.app.data.model.domain.CatImage
import br.com.cattose.app.data.paging.CatsPagingSource
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DefaultCatRepository @Inject constructor(
    private val catsApi: CatsApi
) : CatRepository {
    override fun getCatsPagingFactory(): PagingSourceFactory<Int, CatImage> =
        PagingSourceFactory {
            CatsPagingSource(catsApi)
        }

    override fun getDetails(id: String) = flow {
        emit(catsApi.fetchDetails(id).toDomain())
    }
}

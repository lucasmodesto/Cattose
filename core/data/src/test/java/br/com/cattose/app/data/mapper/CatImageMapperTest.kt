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

package br.com.cattose.app.data.mapper

import br.com.cattose.app.data.model.response.CatImageResponse
import com.google.common.truth.Truth
import org.junit.Test

class CatImageMapperTest {

    @Test
    fun `given image response when map to domain then return cat image`() {
        CatImageResponse(
            id = "id",
            url = "url"
        ).toDomain().apply {
            Truth.assertThat(id).isEqualTo("id")
            Truth.assertThat(imageUrl).isEqualTo("url")
        }
    }
}
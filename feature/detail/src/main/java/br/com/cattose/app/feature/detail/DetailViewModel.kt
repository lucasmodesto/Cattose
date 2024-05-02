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

package br.com.cattose.app.feature.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.cattose.app.data.repository.CatRepository
import br.com.cattose.app.feature.detail.navigation.CAT_ID_ARG
import br.com.cattose.app.feature.detail.navigation.IMAGE_URL_ARG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: CatRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val catId: String = checkNotNull(savedStateHandle[CAT_ID_ARG])
    private val imageUrl: String = checkNotNull(savedStateHandle[IMAGE_URL_ARG])
    private val _state = MutableStateFlow(DetailState(catImageUrl = imageUrl))
    val state: StateFlow<DetailState> = _state

    init {
        fetchDetails()
    }

    fun fetchDetails() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true,
                    hasError = false
                )
            }
            repository.getDetails(catId).catch {
                _state.update {
                    it.copy(
                        isLoading = false,
                        hasError = true
                    )
                }
            }.collect { catDetails ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        hasError = false,
                        catDetails = catDetails
                    )
                }
            }
        }
    }
}

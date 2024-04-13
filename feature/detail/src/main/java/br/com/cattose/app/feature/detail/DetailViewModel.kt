package br.com.cattose.app.feature.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.cattose.app.core.domain.repository.CatRepository
import br.com.cattose.app.feature.detail.navigation.DetailScreenNavigation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: CatRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val catId: String = checkNotNull(savedStateHandle[DetailScreenNavigation.CAT_ID])
    private val _state = MutableStateFlow<DetailState>(DetailState.Loading)
    val state: StateFlow<DetailState> = _state

    init {
        fetchDetails()
    }

    fun fetchDetails() {
        viewModelScope.launch {
            _state.emit(DetailState.Loading)
            repository.getDetails(catId).catch {
                _state.emit(DetailState.Error)
            }.collect {
                _state.emit(DetailState.Success(it))
            }
        }
    }
}

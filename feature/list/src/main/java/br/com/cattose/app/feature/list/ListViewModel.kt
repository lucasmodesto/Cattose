package br.com.cattose.app.feature.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.cattose.app.core.domain.repository.CatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val catsRepository: CatRepository
) : ViewModel() {

    private val _state = MutableStateFlow<ListState>(ListState.Loading)
    val state: StateFlow<ListState> = _state

    fun fetchList() {
        viewModelScope.launch {
            _state.emit(ListState.Loading)
            catsRepository.getCats().catch {
                _state.emit(ListState.Error)
            }.collect { cats ->
                if (cats.isEmpty()) {
                    _state.emit(ListState.Empty)
                } else {
                    _state.emit(ListState.Success(cats))
                }
            }
        }
    }
}

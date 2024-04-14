package br.com.cattose.app.feature.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import br.com.cattose.app.data.model.domain.CatImage
import br.com.cattose.app.data.repository.CatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    catsRepository: CatRepository
) : ViewModel() {

    val catsPagingData: Flow<PagingData<CatImage>> = Pager(
        config = PagingConfig(pageSize = 10),
        pagingSourceFactory = catsRepository.getCatsPagingFactory()
    ).flow.cachedIn(viewModelScope)

}

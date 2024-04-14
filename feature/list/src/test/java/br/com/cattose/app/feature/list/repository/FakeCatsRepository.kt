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
package br.com.cattose.app.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import br.com.cattose.app.data.api.CatsApi
import br.com.cattose.app.data.mapper.toDomain
import br.com.cattose.app.data.model.domain.CatImage
import javax.inject.Inject

class CatsPagingSource @Inject constructor(
    private val api: CatsApi
) : PagingSource<Int, CatImage>() {
    override fun getRefreshKey(state: PagingState<Int, CatImage>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val closestPage = state.closestPageToPosition(anchorPosition) ?: return null

        val prevKey = closestPage.prevKey
        val nextKey = closestPage.nextKey

        return when {
            prevKey != null -> prevKey.plus(1)
            nextKey != null -> nextKey.minus(1)
            else -> null
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CatImage> {
        return try {
            val page = params.key ?: 1
            val response = api.fetchList(page)
            LoadResult.Page(
                data = response.map { it.toDomain() },
                prevKey = null,
                nextKey = if (response.isEmpty()) null else page.plus(1),
            )
        } catch (ex: Exception) {
            LoadResult.Error(ex)
        }
    }
}

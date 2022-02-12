package com.toters.marvelfan.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.toters.marvelfan.data.model.CharacterModel
import com.toters.marvelfan.data.network.api.CharactersAPIServices

const val CHARACTERS_LIMIT = 25

class CharactersPagingSource(
    private val service: CharactersAPIServices,
) : PagingSource<Int, CharacterModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterModel> {
        // Start refresh at position 1 if undefined.
        val position = params.key ?: 1
        val offset = if (params.key != null) ((position - 1) * CHARACTERS_LIMIT) + 1 else 0
        return try {
            val characters = service.getCharacters(offset = offset, limit = params.loadSize).data.results
            val nextKey = if (characters.isEmpty()) null
            else {
                // initial load size = 3 * NETWORK_PAGE_SIZE
                // ensure we're not requesting duplicating items, at the 2nd request
                position + (params.loadSize / CHARACTERS_LIMIT)
            }

            LoadResult.Page(
                data = characters,
                prevKey = null, // Only paging forward.
                // assume that if a full page is not loaded, that means the end of the data
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, CharacterModel>): Int? {
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index
        return null
    }
}
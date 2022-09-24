//package com.mod.marvelcomic.infrastructure.datasources
//
//import androidx.paging.PagingSource
//import androidx.paging.PagingState
//import com.mod.marvelcomic.infrastructure.apiservices.ComicCharacterApiService
//import com.mod.marvelcomic.infrastructure.dtos.ComicCharacterDto
//import javax.inject.Inject
//
//class ComicCharacterPagingSource @Inject constructor(
//    private val comicCharacterApiService: ComicCharacterApiService
//): PagingSource<Int, ComicCharacterDto>() {
//    override fun getRefreshKey(state: PagingState<Int, ComicCharacterDto>): Int? {
//        return state.anchorPosition
//    }
//
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ComicCharacterDto> {
//        val offset = params.key ?: 0
//
//        return try {
//            val comicCharactersResponse = comicCharacterApiService.getComicCharacters(offset = offset, limit = params.loadSize)
//            LoadResult.Page(
//                data = comicCharactersResponse.data.results,
//                prevKey = if (offset == 0) null else offset - params.loadSize,
//                nextKey = if (offset == comicCharactersResponse.data.total) null else offset + params.loadSize
//            )
//        } catch (e: Exception) {
//            LoadResult.Error(e)
//        }
//    }
//}
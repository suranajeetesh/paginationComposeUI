package com.example.composelist.utils

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.composelist.data.remote.model.response.userData.DataList
import com.example.composelist.repository.HomeRepository
import com.example.composelist.util.Constant.STARTING_PAGE_INDEX
import retrofit2.HttpException
import java.io.IOException

class UserDataPagingSource(val apiService: HomeRepository) : PagingSource<Int, DataList>() {
    override fun getRefreshKey(state: PagingState<Int, DataList>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DataList> {
        val position = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = apiService.getUserData(position.toString())
            val repos: List<DataList> = response.data.filterNotNull()
            val nextKey = if (repos.isEmpty()) {
                null
            } else {
                val nextPage = position + 1
                if (nextPage <= response.total_pages || nextPage<=5) {
                    nextPage
                } else {
                    null
                }
            }
            LoadResult.Page(
                data = repos,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}
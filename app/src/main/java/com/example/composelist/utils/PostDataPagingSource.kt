package com.example.composelist.utils

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.composelist.data.remote.model.response.post.PostResponseItem
import com.example.composelist.repository.HomeRepository
import com.example.composelist.util.Constant.NETWORK_PAGE_SIZE
import com.example.composelist.util.Constant.STARTING_PAGE_INDEX
import retrofit2.HttpException
import java.io.IOException

class PostDataPagingSource(val apiService: HomeRepository) : PagingSource<Int, PostResponseItem>() {
    override fun getRefreshKey(state: PagingState<Int, PostResponseItem>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PostResponseItem> {
        val position = params.key ?: STARTING_PAGE_INDEX
        Log.e("TAG","load() position--> $position")
        return try {
            val response = apiService.getPostData(position.toString(), NETWORK_PAGE_SIZE.toString())
            val repos: List<PostResponseItem> = response.filterNotNull()
            val nextPage = if (repos.isEmpty()) null else position + 1
            val prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1
            Log.e("TAG","load() nextPage--> $nextPage prevKey--> $prevKey")

            LoadResult.Page(
                data = repos,
                prevKey = prevKey,
                nextKey = nextPage
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}
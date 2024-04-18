package com.example.composelist.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.composelist.core.uI.BaseViewModel
import com.example.composelist.data.remote.model.response.post.PostResponseItem
import com.example.composelist.network.ApiException
import com.example.composelist.repository.HomeRepository
import com.example.composelist.util.Constant.NETWORK_PAGE_SIZE
import com.example.composelist.utils.PostDataPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by JeeteshSurana.
 */

@HiltViewModel
class HomeViewModel @Inject constructor(

    private val repository: HomeRepository) : BaseViewModel() {
    val postDetailsResponse = MutableStateFlow(PostResponseItem())

    fun getUserDataWithPagination(): Flow<PagingData<PostResponseItem>> {
        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE), initialKey = 1,
            pagingSourceFactory = { PostDataPagingSource(repository) } // Pass repository here
        ).flow.cachedIn(viewModelScope)
    }

    suspend fun getPostData(postId: String) = withContext(Dispatchers.Main) {
        try {
            val data = repository.getPostData(postId)
            postDetailsResponse.emit(data)
            Log.e("TAG", "getData() data--> $data")
        } catch (e: ApiException) {
            e.printStackTrace()
        }
    }
}
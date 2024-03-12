package com.example.composelist.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.composelist.core.uI.BaseViewModel
import com.example.composelist.data.remote.model.response.userData.DataList
import com.example.composelist.data.remote.model.response.userData.UserDataResponse
import com.example.composelist.network.ApiException
import com.example.composelist.repository.HomeRepository
import com.example.composelist.utils.UserDataPagingSource
import com.example.composelist.util.Constant.NETWORK_PAGE_SIZE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by JeeteshSurana.
 */

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository) : BaseViewModel() {

    private var pageCount: String? = "1"
    private val _getUser = MutableStateFlow(UserDataResponse())

    val userData: StateFlow<UserDataResponse>
        get() = _getUser

    suspend fun getData() = withContext(Dispatchers.Main) {
        try {
            val data = repository.getUserData(pageCount)
            _getUser.emit(data)
            Log.e("TAG", "getData() data--> $data")
        } catch (e: ApiException) {
            e.printStackTrace()
        }
    }

//    val userData: Flow<PagingData<DataList>> = Pager(
//        config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
//        pagingSourceFactory = { UserDataPagingSource(repository) }
//    ).flow.cachedIn(viewModelScope)
}
package com.example.composelist.repository

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.composelist.data.remote.model.response.userData.DataList
import com.example.composelist.data.remote.model.response.userData.UserDataResponse
import com.example.composelist.network.ApiRestService
import com.example.composelist.network.SafeApiRequest
import com.example.composelist.util.Constant
import com.example.composelist.utils.UserDataPagingSource
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ActivityRetainedScoped
class HomeRepository @Inject constructor(
    @ApplicationContext context: Context,
    private val api: ApiRestService
) : SafeApiRequest(context) {

    suspend fun getUserData(pageCount:String?): UserDataResponse {
        return apiRequest { api.getUserData(pageCount) }
    }

   fun getUserDataWithPagination() = Pager(
        config = PagingConfig(pageSize = Constant.NETWORK_PAGE_SIZE, enablePlaceholders = false),
        pagingSourceFactory = { UserDataPagingSource(this) }
    ).flow/*.cachedIn(viewModelScope)*/
}
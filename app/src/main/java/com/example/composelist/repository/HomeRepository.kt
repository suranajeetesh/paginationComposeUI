package com.example.composelist.repository

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import com.example.composelist.data.remote.model.response.post.PostResponse
import com.example.composelist.network.ApiRestService
import com.example.composelist.network.SafeApiRequest
import com.example.composelist.util.Constant
import com.example.composelist.utils.PostDataPagingSource
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class HomeRepository @Inject constructor(
    @ApplicationContext context: Context,
    private val api: ApiRestService
) : SafeApiRequest(context) {

    suspend fun getPostData(pageCount:String?,limit:String?): PostResponse {
        return apiRequest { api.getPostData(pageCount,limit) }
    }


    suspend fun loadPostData(pageCount:String?,limit:String?): PagingSource<Int,PostResponse> {
        return  api.loadPostData(pageCount,limit)
    }


}
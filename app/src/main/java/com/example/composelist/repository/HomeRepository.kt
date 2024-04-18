package com.example.composelist.repository

import android.content.Context
import com.example.composelist.data.remote.model.response.post.PostResponse
import com.example.composelist.data.remote.model.response.post.PostResponseItem
import com.example.composelist.network.ApiRestService
import com.example.composelist.network.SafeApiRequest
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

    suspend fun getPostData(postId: String): PostResponseItem {
        return apiRequest { api.getPostDetails(postId) }
    }

}
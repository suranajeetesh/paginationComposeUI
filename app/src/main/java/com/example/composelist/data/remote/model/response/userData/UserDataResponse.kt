package com.example.composelist.data.remote.model.response.userData

data class UserDataResponse(
    var data: List<DataList?> = ArrayList<DataList?>(),
    var page: Int = -1,
    var per_page: Int = -1,
    var support: Support? = null,
    var total: Int? = null,
    var total_pages: Int = -1
)
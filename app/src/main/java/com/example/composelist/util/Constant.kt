package com.example.composelist.util

/**
 * Created by Jeetesh surana.
 */
object Constant {
    const val BASE_URL = "https://jsonplaceholder.typicode.com/"
    const val STARTING_PAGE_INDEX = 1
    const val NETWORK_PAGE_SIZE = 10
    const val TOTAL_PAGE_SIZE = 100
    object Routes {
        const val LIST_VIEW_ROUTE = "listView"
        const val PRODUCT_DETAILS_ROUTE = "productDetails/{productId}"
    }
    object ArgumentsKeys {
        const val PRODUCT_ID = "productId"
        const val PRODUCT_DETAILS_ID = "productDetails/"
    }

}
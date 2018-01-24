package com.jaaarl.catalog.rest.response

/**
 * Created by Bohdan on 22.10.2017.
 */

abstract class BaseResponseModel<out T> {

    var status: Int? = null
    var message: String? = null
    var pager: PagerModel? = null

    abstract val data: List<T>?

    val isSuccessful: Boolean get() {
            var successful = false

            if (status!! in 200..300)
                successful = true

            return successful
        }
}

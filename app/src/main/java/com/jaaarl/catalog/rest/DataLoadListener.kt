package com.jaaarl.catalog.rest

/**
 * Created by Bohdan on 29.10.2017.
 */

interface DataLoadListener {

    fun onDataLoadingStart(showProgress: Boolean)

    fun <T> onDataLoadingComplete(data: List<T>?)

    fun onError(message: String?)
}

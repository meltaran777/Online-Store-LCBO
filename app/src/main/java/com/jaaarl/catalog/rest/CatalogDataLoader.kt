package com.jaaarl.catalog.rest

import android.util.Log
import com.google.gson.GsonBuilder
import com.jaaarl.catalog.MyApplication
import com.jaaarl.catalog.rest.response.BaseResponseModel
import com.jaaarl.catalog.rest.response.ErrorResponse
import com.jaaarl.catalog.rest.response.ProductResponse
import com.jaaarl.catalog.rest.response.StoreResponse
import com.jaaarl.catalog.model.SearchData
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

import javax.inject.Inject


/**
 * Created by Bohdan on 05.11.2017.
 */

class CatalogDataLoader {
    private val TAG = javaClass.simpleName

    @Inject
    lateinit var api: CatalogAPI

    var page = 1
    var isLoading = false

    val isFirstDataLoaded: Boolean get() = page != 1


    init {
        MyApplication.getAppComponent().inject(this)
    }

    fun requestStores(request: Request, listener: DataLoadListener) {
        requestData<StoreResponse>(
                api.getStores(page, request.perPage, null, request.matchCause),
                false,
                listener)
    }

    fun requestStoreProducts(request: Request, listener: DataLoadListener) {
        requestData<ProductResponse>(
                api.getStoreProducts(page, request.perPage, request.storeId),
                false,
                listener)
    }

    fun requestProducts(request: Request, showProgress: Boolean, listener: DataLoadListener?) {
        val order = buildOrderString(request.orderType)
        val where = buildSortOptionsString(request.optionsWhere)
        val where_not = buildSortOptionsString(request.optionsWhereNot)
        val match_str = buildMatchString(request.matchCause)
        val per_page = request.perPage

        requestData<ProductResponse>(
                api.getProducts(page, per_page, where, where_not, order, match_str),
                showProgress,
                listener)
    }

    private fun <T : BaseResponseModel<*>> requestData(observable: Flowable<T>,
                                                       showProgress: Boolean,
                                                       listener: DataLoadListener?) {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    isLoading = true
                    listener!!.onDataLoadingStart(showProgress)
                }
                .subscribe({ response ->
                    listener?.onDataLoadingComplete(response.data)
                }, { e ->
                    isLoading = false
                    if (e is HttpException) {
                        val body = e.response().errorBody()
                        try {
                            val error_json_str = body.string()
                            val gson = GsonBuilder().create()
                            val error = gson.fromJson(error_json_str, ErrorResponse::class.java)
                            listener?.onError(error.message)
                            Log.d(TAG, "Error massage = " + error.message)
                        } catch (e1: Exception) {
                            e1.printStackTrace()
                        }
                    } else {
                        listener?.onError(e.message)
                        e.printStackTrace()
                    }
                }, {
                    isLoading = false
                    page++
                })
    }

    private fun buildMatchString(matchCause: String?): String? {
        return if (matchCause.equals("")) null
        else matchCause
    }

    private fun buildOrderString(type: SearchData.OrderType?): String? {
        return type?.toString()?.toLowerCase()
    }

    private fun buildSortOptionsString(options: Array<SearchData.SortOptions?>?): String? {
        var where: String? = ""

        options?.asSequence()
                ?.map { it.toString().toLowerCase() }
                ?.forEachIndexed { i, option_str ->
                    where += if (i == 0)
                        option_str
                    else
                        "," + option_str
                }

        return if (where.equals("")) null
        else where
    }
}

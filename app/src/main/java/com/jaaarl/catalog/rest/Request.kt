package com.jaaarl.catalog.rest

import com.jaaarl.catalog.model.SearchData

/**
 * Created by Bohdan on 05.11.2017.
 */

class Request {
    var perPage = 10
    var storeId: Int = 0
    var matchCause: String? = null
    var orderType: SearchData.OrderType? = null
    var optionsWhere: Array<SearchData.SortOptions?>? = null
    var optionsWhereNot: Array<SearchData.SortOptions?>? = null
}

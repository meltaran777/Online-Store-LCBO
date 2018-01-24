package com.jaaarl.catalog.rest.response

import com.google.gson.annotations.SerializedName
import com.jaaarl.catalog.model.Store

/**
 * Created by Bohdan on 22.10.2017.
 */

class StoreResponse : BaseResponseModel<Store>() {

    @SerializedName(value = "result")
    override var data: List<Store>? = null
}

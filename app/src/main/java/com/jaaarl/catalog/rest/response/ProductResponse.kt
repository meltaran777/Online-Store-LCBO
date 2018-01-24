package com.jaaarl.catalog.rest.response

import com.google.gson.annotations.SerializedName
import com.jaaarl.catalog.model.Product

/**
 * Created by Bohdan on 22.10.2017.
 */

class ProductResponse : BaseResponseModel<Product>() {

    @SerializedName(value = "result")
    override var data: List<Product>? = null
}

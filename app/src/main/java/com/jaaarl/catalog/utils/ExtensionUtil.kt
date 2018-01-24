package com.jaaarl.catalog.utils



import android.support.design.widget.Snackbar
import android.view.View
import com.jaaarl.catalog.db.ProductDao
import com.jaaarl.catalog.model.Product
import kotlinx.coroutines.experimental.async

fun View.showSnack(s: String) {
    Snackbar.make(this, s, Snackbar.LENGTH_LONG)
            .setAction("Action", null)
            .show()
}

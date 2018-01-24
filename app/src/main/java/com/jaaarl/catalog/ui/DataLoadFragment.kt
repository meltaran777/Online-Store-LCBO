package com.jaaarl.catalog.ui

import com.jaaarl.catalog.rest.CatalogDataLoader
import com.jaaarl.catalog.rest.DataLoadListener
import com.jaaarl.catalog.rest.IDataLoadController

import javax.inject.Inject

/**
 * Created by Bohdan on 03.12.2017.
 */

abstract class DataLoadFragment : BaseFragment(), DataLoadListener, IDataLoadController {

    private var isOnScreen = false

    @Inject
    internal lateinit var catalogDataLoader: CatalogDataLoader


    protected abstract fun inject()

    override fun setMenuVisibility(menuVisible: Boolean) {
        super.setMenuVisibility(menuVisible)
        isOnScreen = menuVisible
        inject()
        if (isOnScreen && !catalogDataLoader.isFirstDataLoaded) {
            loadFirstData()
        }
    }
}

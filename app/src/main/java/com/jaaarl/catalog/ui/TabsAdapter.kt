package com.jaaarl.catalog.ui

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

import com.jaaarl.catalog.ui.productcategories.ProductCategoriesFragment
import com.jaaarl.catalog.ui.productsearch.ProductSearchFragment
import com.jaaarl.catalog.ui.shoppingcart.ShoppingCartFragment
import com.jaaarl.catalog.ui.storesearch.StoresFragment

import java.util.HashMap

/**
 * Created by Bohdan on 22.10.2017.
 */

class TabsAdapter(mFragmentManager: FragmentManager) : FragmentPagerAdapter(mFragmentManager) {

    private val fragmentsMap = HashMap<Int, BaseFragment?>()

    init {
        initTabsMap()
    }

    override fun getItem(position: Int): Fragment? {
        return fragmentsMap[position]
    }

    override fun getCount(): Int {
        return fragmentsMap.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return fragmentsMap[position]!!.title
    }

    private fun initTabsMap() {
        fragmentsMap.put(0, StoresFragment.newInstance().setTitle("Stores"))
        fragmentsMap.put(1, StoresFragment.newInstance().setTitle("Stores"))
        fragmentsMap.put(2, ProductCategoriesFragment.newInstance().setTitle("Product by Categories"))
        fragmentsMap.put(3, ProductSearchFragment.newInstance().setTitle("Product search with options"))
        fragmentsMap.put(4, ShoppingCartFragment().setTitle("Shopping cart"))
    }
}

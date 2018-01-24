package com.jaaarl.catalog.ui.storesearch

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jaaarl.catalog.MyApplication
import com.jaaarl.catalog.R
import com.jaaarl.catalog.rest.CatalogDataLoader
import com.jaaarl.catalog.rest.DataLoadListener
import com.jaaarl.catalog.rest.Request
import com.jaaarl.catalog.model.Product
import com.jaaarl.catalog.ui.ProductDetailsActivity
import com.jaaarl.catalog.ui.productcategories.ProductItemClickListener
import com.jaaarl.catalog.ui.productcategories.ProductListAdapter
import kotlinx.android.synthetic.main.fragment_product_list_dialog.*
import javax.inject.Inject


class ProductListDialogFragment : BottomSheetDialogFragment() {

    var storeId: Int = -1

    private lateinit var productListAdapter: ProductListAdapter

    private val products: MutableList<Product> = ArrayList()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        MyApplication.getAppComponent().inject(this)
        return inflater.inflate(R.layout.fragment_product_list_dialog, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        list.layoutManager = GridLayoutManager(context, 2)

        productListAdapter = ProductListAdapter(R.layout.product_list_item_grid, products)

        productListAdapter.listener = object : ProductItemClickListener {

            override fun onProductItemClick(product: Product, position: Int) {
                val intent = Intent(context, ProductDetailsActivity::class.java)
                intent.putExtra("product", product)
                startActivity(intent)
            }
        }

        list.adapter = productListAdapter
    }

}

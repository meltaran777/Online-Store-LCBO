package com.jaaarl.catalog.ui.productcategories

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.jaaarl.catalog.ui.DataLoadFragment
import com.jaaarl.catalog.MyApplication
import com.jaaarl.catalog.R
import com.jaaarl.catalog.rest.Request
import com.jaaarl.catalog.model.Product
import com.jaaarl.catalog.ui.BaseFragment
import com.jaaarl.catalog.ui.ProductDetailsActivity

import butterknife.BindView
import com.jaaarl.catalog.db.Db
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.ArrayList

/**
 * Created by Bohdan on 29.10.2017.
 */

class ProductTabFragment : DataLoadFragment() {
    //views
    @BindView(R.id.recycler_view_tabs)
    internal lateinit var recyclerView: RecyclerView

    private lateinit var adapter: ProductListAdapter
    private lateinit var layoutManager: LinearLayoutManager

    //data
    private var products: MutableList<Product> = ArrayList()

    //logic
    internal var productsRequest: Request = Request()


    override fun getContentView(): Int {
        return R.layout.product_tab_layout
    }

    override fun inject() {
        MyApplication.getAppComponent().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        //Log.d(TAG, "onViewCreated: Tab Created --> " + title)

        adapter = ProductListAdapter(R.layout.product_list_item, products)
        adapter.listener = object : ProductItemClickListener {
            override fun onProductItemClick(product: Product, position: Int) {
                val intent = Intent(context, ProductDetailsActivity::class.java)
                intent.putExtra("product", product)
                startActivity(intent)
            }

        }

        layoutManager = LinearLayoutManager(view.context)

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                //Log.d(TAG, "onScrolled: ")
                if (catalogDataLoader.isLoading)
                    return

                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val pastVisibleItems = layoutManager.findFirstVisibleItemPosition()

                Log.d(TAG, "onScrolled: visible = " + visibleItemCount)
                Log.d(TAG, "onScrolled: total = " + totalItemCount)
                Log.d(TAG, "onScrolled: past = " + pastVisibleItems)

                if (pastVisibleItems + visibleItemCount >= totalItemCount - visibleItemCount) {
                    //Log.d(TAG, "onScrolled: Load Data")
                    catalogDataLoader.requestProducts(productsRequest,
                            false,
                            this@ProductTabFragment)
                }
            }
        })

        return view
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // IDataLoadController
    ////////////////////////////////////////////////////////////////////////////////////////////////

    override fun loadFirstData() {
        productsRequest.apply { matchCause = title }
        catalogDataLoader.requestProducts(productsRequest,
                false,
                this@ProductTabFragment)
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // DataLoadListener
    ////////////////////////////////////////////////////////////////////////////////////////////////

    override fun onDataLoadingStart(showProgress: Boolean) {
        if (showProgress)
            showProgress()
    }

    override fun onError(message: String?) {

    }

    override fun <T> onDataLoadingComplete(data: List<T>?) {
        val list = data?.filterIsInstance<Product>()

        Observable.just(list).subscribeOn(Schedulers.io()).subscribe({
            it.let { it1 -> Db.getProductDao().insert(it1.toTypedArray()) }
        })

        runOnUiThread {
            hideProgress()
            list?.forEach { products.add(it); /*Log.d("ListDebug", "Name =  ${it.name}")*/ }
            adapter.notifyDataSetChanged()
        }
    }

    companion object {

        fun newInstance(): BaseFragment {
            return ProductTabFragment()
        }
    }
}

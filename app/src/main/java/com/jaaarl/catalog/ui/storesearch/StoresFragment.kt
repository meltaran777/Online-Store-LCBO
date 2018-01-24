package com.jaaarl.catalog.ui.storesearch

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View

import com.jaaarl.catalog.ui.DataLoadFragment
import com.jaaarl.catalog.MyApplication
import com.jaaarl.catalog.R
import com.jaaarl.catalog.rest.Request
import com.jaaarl.catalog.model.Store
import com.jaaarl.catalog.ui.BaseFragment

import java.util.ArrayList

import butterknife.BindView
import com.jaaarl.catalog.db.Db
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

/**
 * Created by Bohdan on 22.10.2017.
 */

class StoresFragment : DataLoadFragment() {

    @BindView(R.id.recyclerView)
    internal lateinit var recyclerView: RecyclerView

    lateinit var adapter: StoreAdapter
    lateinit var layoutManager: GridLayoutManager

    private var stores: MutableList<Store> = ArrayList()

    internal var storeRequest: Request = Request()


    override fun getContentView(): Int {
        return R.layout.stores_fragment_layout
    }

    override fun inject() {
        MyApplication.getAppComponent().inject(this)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        layoutManager = GridLayoutManager(context, 2)
        adapter = StoreAdapter(stores)

        adapter.setListener { store, position ->
            val intent = Intent(context, StoreDetailsActivity::class.java)
            intent.putExtra("store", store)
            startActivity(intent)
        }

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                if (catalogDataLoader.isLoading)
                    return

                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val pastVisibleItems = layoutManager.findFirstVisibleItemPosition()

                if (pastVisibleItems + visibleItemCount >= totalItemCount - visibleItemCount) {
                    catalogDataLoader.requestStores(storeRequest, this@StoresFragment)
                }
            }
        })
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //IDataLoadController
    ////////////////////////////////////////////////////////////////////////////////////////////////

    override fun loadFirstData() {
        catalogDataLoader.requestStores(storeRequest, this)
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //DataLoadListener
    ////////////////////////////////////////////////////////////////////////////////////////////////

    override fun onDataLoadingStart(showProgress: Boolean) {
        if (showProgress)
            showProgress()
    }

    override fun onError(message: String?) {

    }

    override fun <T> onDataLoadingComplete(data: List<T>?) {
        val list = data?.filterIsInstance<Store>()

        Observable.just(list)
                .subscribeOn(Schedulers.io())
                .subscribe({
                    it.let { it1 -> Db.getStoreDao().insert(it1.toTypedArray()) }
                })

        runOnUiThread {
            hideProgress()
            list?.forEach { stores.add(it) }
            adapter.notifyDataSetChanged()
        }
    }

    companion object {

        fun newInstance(): BaseFragment {
            return StoresFragment()
        }
    }
}

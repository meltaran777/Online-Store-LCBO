package com.jaaarl.catalog.ui.productsearch

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.*
import butterknife.BindView
import com.jaaarl.catalog.MyApplication
import com.jaaarl.catalog.R
import com.jaaarl.catalog.db.Db
import com.jaaarl.catalog.model.Product
import com.jaaarl.catalog.model.SearchData
import com.jaaarl.catalog.rest.Request
import com.jaaarl.catalog.ui.BaseFragment
import com.jaaarl.catalog.ui.DataLoadFragment
import com.jaaarl.catalog.ui.ProductDetailsActivity
import com.jaaarl.catalog.ui.productcategories.ProductItemClickListener
import com.jaaarl.catalog.ui.productcategories.ProductListAdapter
import com.jaaarl.catalog.utils.MyPreferences
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.*

/**
 * Created by Bohdan on 22.10.2017.
 */

class ProductSearchFragment : DataLoadFragment(),
        BottomFragmentListener, TextWatcher, ProductItemClickListener {

    @BindView(R.id.recyclerView)
    internal lateinit var recyclerView: RecyclerView

    private lateinit var adapter: ProductListAdapter
    private lateinit var layoutManager: GridLayoutManager

    private var request: Request = Request()
    private var handler: AfterTextChangeHandler = AfterTextChangeHandler()

    private var products: MutableList<Product> = ArrayList()


    override fun getContentView(): Int {
        return R.layout.stores_fragment_layout
    }

    override fun inject() {
        MyApplication.getAppComponent().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        layoutManager = GridLayoutManager(context, 2)
        adapter = ProductListAdapter(R.layout.product_list_item_grid, products)

        adapter.listener = this

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
                    catalogDataLoader.requestProducts(request,
                            false,
                            this@ProductSearchFragment)
                }
            }
        })

        request.orderType = MyPreferences.getSortOrder()
        request.optionsWhere = MyPreferences.getOptionsArray(SearchData.SortOptionType.WHERE)
        request.optionsWhereNot = MyPreferences.getOptionsArray(SearchData.SortOptionType.WHERE_NOT)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)

        menu!!.clear()
        inflater!!.inflate(R.menu.search_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.sort_action -> {
                val optionsDialog = SearchOptionsFragment.newInstance()
                optionsDialog.show(childFragmentManager, "sort_options")
            }
        }

        return super.onOptionsItemSelected(item)
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //ProductItemClickListener
    ////////////////////////////////////////////////////////////////////////////////////////////////

    override fun onProductItemClick(product: Product, position: Int) {
        val intent = Intent(context, ProductDetailsActivity::class.java)
        intent.putExtra("product", product)
        startActivity(intent)
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // IDataLoadController
    ////////////////////////////////////////////////////////////////////////////////////////////////

    override fun loadFirstData() {
        catalogDataLoader.requestProducts(request, false, this)
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // DataLoadListener
    ////////////////////////////////////////////////////////////////////////////////////////////////

    override fun onDataLoadingStart(showProgress: Boolean) {
        if (showProgress)
            showProgress()
    }

    override fun <T> onDataLoadingComplete(data: List<T>?) {
        val list = data?.filterIsInstance<Product>()

        Observable.just(list)
                .subscribeOn(Schedulers.io())
                .subscribe({
                    it.let { it1 -> Db.getProductDao().insert(it1.toTypedArray()) }
                })

        runOnUiThread {
            hideProgress()
            list?.forEach { products.add(it) }
            adapter.notifyDataSetChanged()
        }
    }

    override fun onError(message: String?) {

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SearchOptionsFragment.BottomFragmentListener
    ////////////////////////////////////////////////////////////////////////////////////////////////

    override fun onOptionsApply() {
        products.clear()
        catalogDataLoader.page = 1

        if (request.orderType == null)
            MyPreferences.setSortEnable(false)
        else {
            MyPreferences.setSortEnable(true)
            //Log.d("PreferenceDebug", "Order Type = ${request.orderType.toString()}")
            MyPreferences.setSortOrder(request.orderType?.ordinal)
        }

        MyPreferences.setOptionsArray(SearchData.SortOptionType.WHERE, request.optionsWhere)
        MyPreferences.setOptionsArray(SearchData.SortOptionType.WHERE_NOT, request.optionsWhereNot)

        catalogDataLoader.requestProducts(request, true, this)
    }

    override fun onFilterOptionsChange(type: SearchData.SortOptionType?, options: Array<SearchData.SortOptions?>?) {
        when (type) {
            SearchData.SortOptionType.WHERE -> request.optionsWhere = options
            SearchData.SortOptionType.WHERE_NOT -> request.optionsWhereNot = options
        }
        //Log.d("SearchDebug", "Type = ${type.toString()} Options ==>")
        options?.forEach { Log.d("SearchDebug", "\n ${it.toString()}") }
    }

    override fun onSortOptionsChange(order: SearchData.OrderType?) {
        request.orderType = order
        //Log.d("SearchDebug", "Type = ${order.toString()} Options ==>")
        //Log.d("SearchDebug", "///////////////////////////////////////////////////////////")
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // TextWatcher
    ////////////////////////////////////////////////////////////////////////////////////////////////

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        request.matchCause = s.toString().trim { it <= ' ' }

        if (TextUtils.isEmpty(s)) {
            handler.removeCallbacksAndMessages(null)
            return
        }

        val message = Message()
        /*        Bundle bundle = new Bundle();

        bundle.putString("str", s.toString());
        message.setData(bundle);*/

        handler.removeCallbacksAndMessages(null)
        handler.sendMessageDelayed(message, 1000)
    }

    override fun afterTextChanged(s: Editable) {

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // AfterTextChangeHandler
    ////////////////////////////////////////////////////////////////////////////////////////////////

    @SuppressLint("HandlerLeak")
    private inner class AfterTextChangeHandler : Handler() {

        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            //request.setMatchCause(String.valueOf(msg.getData().get("str")).trim());
            products.clear()
            catalogDataLoader.requestProducts(request,
                    true,
                    this@ProductSearchFragment)
        }
    }

    companion object {

        fun newInstance(): BaseFragment {
            return ProductSearchFragment()
        }
    }
}

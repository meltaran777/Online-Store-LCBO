package com.jaaarl.catalog.ui.shoppingcart

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.BindView
import com.jaaarl.catalog.R
import com.jaaarl.catalog.db.Db
import com.jaaarl.catalog.model.ShoppingCartEntity
import com.jaaarl.catalog.ui.BaseFragment
import com.jaaarl.catalog.ui.ProductDetailsActivity
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.shopping_card_fragment.*
import java.util.*


/**
 * Created by Bohdan on 11.12.2017.
 */

class ShoppingCartFragment : BaseFragment(),
        ShoppingCartListAdapter.ShoppingCartItemListener {

    @BindView(R.id.shopping_cart_recycler)
    internal lateinit var recyclerView: RecyclerView

    private lateinit var adapter: ShoppingCartListAdapter
    private lateinit var layoutManager: LinearLayoutManager

    private lateinit var detailsShoppingCart: ShoppingCartEntity
    private var detailsShoppingCartPosition: Int = -1

    private var shoppingCartList: MutableList<ShoppingCartEntity> = ArrayList()


    override fun getContentView(): Int {
        return R.layout.shopping_card_fragment
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        layoutManager = LinearLayoutManager(context)

        adapter = ShoppingCartListAdapter(shoppingCartList)
        adapter.listener = this

        val itemTouchHelperCallback: ItemTouchHelper.SimpleCallback =
                object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

                    override fun onMove(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?, target: RecyclerView.ViewHolder?): Boolean {
                        return false
                    }

                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int) {
                        val position = viewHolder?.adapterPosition
                        position?.let { onDeleteAction(position.let { shoppingCartList[it] }, it) }
                    }
                }

        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView)

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        initUi()

        return view
    }

    override fun onFragmentOnScreen() {
        initUi()
    }

    @SuppressLint("SetTextI18n")
    private fun initUi() {
        shoppingCartList.clear()

        Db.getShoppingCartDao().getAllShoppingCartItems()
                .subscribeOn(Schedulers.io())
                .subscribe(Consumer {
                    Flowable.fromIterable(it)
                            ?.doOnNext {
                                val product = Db.getProductDao().getProduct(it.productId)
                                it.product = product
                                shoppingCartList.add(it)
                            }
                            ?.observeOn(AndroidSchedulers.mainThread())
                            ?.doOnComplete {
                                adapter.notifyDataSetChanged()
                                initTotalPriceView()
                            }
                            ?.subscribe()
                })
    }

    @SuppressLint("SetTextI18n")
    private fun initTotalPriceView() {
        var totalPrice = 0f
        shoppingCartList.forEach {
            totalPrice += it.product!!.priceInCents * it.productCount
        }
        total_price_text.text = "${totalPrice / 100} $"
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == START_PRODUCT_DETAILS_FROM_SHOPPING_CART_CODE
                && resultCode == PRODUCT_REMOVED_RESULT_CODE) {
            onDeleteAction(detailsShoppingCart, detailsShoppingCartPosition)
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //ShoppingCartItemListener
    ////////////////////////////////////////////////////////////////////////////////////////////////

    override fun onShoppingCartItemClick(sh: ShoppingCartEntity?, position: Int) {
        detailsShoppingCart = sh!!
        detailsShoppingCartPosition = position

        val intent = Intent(context, ProductDetailsActivity::class.java)
        intent.putExtra("product", sh.product)
        startActivityForResult(intent, START_PRODUCT_DETAILS_FROM_SHOPPING_CART_CODE)
    }

    override fun onPlusProductClick(sh: ShoppingCartEntity?, position: Int) {
        if (sh != null)
            Flowable.just(sh)
                    .subscribeOn(Schedulers.io())
                    .doOnNext { sh.apply { productCount++; Db.getShoppingCartDao().update(it) } }
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnComplete {
                        initTotalPriceView()
                        adapter.notifyItemChanged(position)
                    }
                    .subscribe()
    }

    override fun onMinusProductClick(sh: ShoppingCartEntity?, position: Int) {
        if (sh?.productCount!! > 1)
            Flowable.just(sh)
                    .subscribeOn(Schedulers.io())
                    .doOnNext { sh.apply { productCount-- }.let { Db.getShoppingCartDao().update(it) } }
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnComplete {
                        initTotalPriceView()
                        adapter.notifyItemChanged(position)
                    }
                    .subscribe()
    }

    override fun onDeleteAction(sh: ShoppingCartEntity?, position: Int) {
        shoppingCartList.removeAt(position)
        adapter.notifyItemRemoved(position)

        initTotalPriceView()

        Flowable.just(sh)
                .subscribeOn(Schedulers.io())
                .doOnNext { Db.getShoppingCartDao().delete(it) }
                .subscribe()
    }

    companion object {
        val START_PRODUCT_DETAILS_FROM_SHOPPING_CART_CODE: Int = 1
        val PRODUCT_REMOVED_RESULT_CODE: Int = 1
    }
}

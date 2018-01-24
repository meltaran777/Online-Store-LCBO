package com.jaaarl.catalog.ui

import android.animation.ValueAnimator
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.FloatingActionButton
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.DrawableCompat
import android.support.v4.widget.NestedScrollView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.CardView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.jaaarl.catalog.R
import com.jaaarl.catalog.db.Db
import com.jaaarl.catalog.model.Product
import com.jaaarl.catalog.model.ShoppingCartEntity
import com.jaaarl.catalog.ui.shoppingcart.ShoppingCartFragment
import com.jaaarl.catalog.utils.showSnack
import com.squareup.picasso.Picasso
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ProductDetailsActivity : AppCompatActivity() {
    private val TAG = javaClass.simpleName

    @BindView(R.id.content_container_layout)
    lateinit internal var contentLayout: LinearLayout
    @BindView(R.id.content_scroll_view)
    lateinit internal var scrollView: NestedScrollView
    @BindView(R.id.productImageView)
    lateinit internal var productImageView: ImageView
    @BindView(R.id.app_bar)
    lateinit internal var appBarLayout: AppBarLayout
    @BindView(R.id.titleTextView)
    lateinit internal var titleTextView: TextView
    @BindView(R.id.arrowBackImage)
    lateinit internal var arrowBackImageView: ImageView
    @BindView(R.id.code_text)
    lateinit internal var codeTextView: TextView
    @BindView(R.id.available_text)
    lateinit internal var availableTextView: TextView
    @BindView(R.id.price_text)
    lateinit internal var priceTextView: TextView
    @BindView(R.id.producer_text)
    lateinit internal var producerNameTextView: TextView
    @BindView(R.id.full_info_text)
    lateinit internal var infoTextView: TextView
    @BindView(R.id.phone_text)
    lateinit internal var phoneTextView: TextView
    @BindView(R.id.product_name_text)
    lateinit internal var productNameTextView: TextView
    @BindView(R.id.phone_button)
    lateinit internal var phoneButton: CardView
    @BindView(R.id.toolbar)
    lateinit internal var toolbar: Toolbar
    @BindView(R.id.shopping_cart_icon)
    lateinit internal var shopping_cart_icon: AppCompatImageView

    private lateinit var product: Product

    private var isInShoppingCart: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)
        ButterKnife.bind(this)

        if (intent.hasExtra("product")) {
            product = intent.getSerializableExtra("product") as Product

            setDataToViews(product)

            Flowable.just(product)
                    .subscribeOn(Schedulers.io())
                    .map { Db.getProductDao().getShoppingCartProduct(it.id) != null }
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        isInShoppingCart = it
                        initInShoppingCartUi(it)
                    })

        } else {
            throw ExceptionInInitializerError("No data found for displaying")
        }

        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        productImageView.setOnClickListener {
            val params = appBarLayout.layoutParams as CoordinatorLayout.LayoutParams
            val behavior = params.behavior as AppBarLayout.Behavior?

            if (behavior != null) {
                val valueAnimator = ValueAnimator.ofInt()
                valueAnimator.interpolator = DecelerateInterpolator()
                valueAnimator.addUpdateListener { animation ->
                    behavior.topAndBottomOffset = animation.animatedValue as Int
                    appBarLayout.requestLayout()
                }

                valueAnimator.setIntValues(0, -300)
                valueAnimator.duration = 400
                valueAnimator.start()
            }
        }

        appBarLayout.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            internal var scrollRange = -1

            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                val titleTextView = appBarLayout.findViewById<View>(R.id.titleTextView) as TextView
                val backArrow = appBarLayout.findViewById<View>(R.id.arrowBackImage) as ImageView

                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }
                //Check if the view is collapsed
                val range = scrollRange + verticalOffset
                val isCollapsed = range <= 50

                Log.d(TAG, "onOffsetChanged: " + isCollapsed + "Range = " + range)

                if (isCollapsed) {
                    val color = ContextCompat.getColor(this@ProductDetailsActivity, android.R.color.white)
                    DrawableCompat.setTint(backArrow.drawable, color)
                    titleTextView.setTextColor(color)
                } else {
                    val color = ContextCompat.getColor(this@ProductDetailsActivity, android.R.color.black)
                    DrawableCompat.setTint(backArrow.drawable, color)
                    titleTextView.setTextColor(color)
                }

            }
        })

        val fab = findViewById<View>(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { view ->
            if (!isInShoppingCart) {
                view.showSnack("${product.name} Added to shopping cart")
                Observable.just(product)
                        .subscribeOn(Schedulers.io())
                        .doOnNext { Db.getShoppingCartDao().insert(ShoppingCartEntity(0, it.id, 1)) }
                        .map { Db.getProductDao().getShoppingCartProduct(it.id) != null }
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            isInShoppingCart = it
                            initInShoppingCartUi(it)
                        })
            } else {
                view.showSnack("${product.name} Removed from shopping cart")
                Observable.just(product)
                        .subscribeOn(Schedulers.io())
                        .doOnNext { Db.getShoppingCartDao().delete(it.id) }
                        .map { Db.getProductDao().getShoppingCartProduct(it.id) != null }
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            isInShoppingCart = it
                            setResult(ShoppingCartFragment.PRODUCT_REMOVED_RESULT_CODE)
                            initInShoppingCartUi(it)
                        })
            }
        }

        arrowBackImageView.setOnClickListener { finish() }
        toolbar.setOnClickListener { finish() }
    }

    private fun initInShoppingCartUi(isInShoppingCart: Boolean) {
        if (isInShoppingCart) {
            shopping_cart_icon.visibility = View.VISIBLE
        } else {
            shopping_cart_icon.visibility = View.GONE
        }
    }

    private fun setDataToViews(data: Product) {
        Picasso.with(this)
                .load(data.imageUrl)
                .fit()
                .into(productImageView)

        titleTextView.text = data.name

        productNameTextView.text = data.name

        codeTextView.text = data.id.toString()

        if (data.isDiscontinued!!) {
            availableTextView.setTextColor(ContextCompat.getColor(this, android.R.color.holo_red_light))
            availableTextView.text = "Not Available"
        } else {
            availableTextView.setTextColor(ContextCompat.getColor(this, android.R.color.holo_green_light))
            availableTextView.text = "Available"
        }

        val price_text = (data.priceInCents!! / 100).toString() + " $"
        priceTextView.text = price_text

        val producer_text = producerNameTextView.text.toString() + data.producerName.toString()
        producerNameTextView.text = producer_text

        infoTextView.text = data.description?.toString()
    }
}





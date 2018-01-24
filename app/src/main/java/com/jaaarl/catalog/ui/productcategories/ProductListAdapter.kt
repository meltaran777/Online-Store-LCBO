package com.jaaarl.catalog.ui.productcategories

import android.support.annotation.LayoutRes
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.jaaarl.catalog.R
import com.jaaarl.catalog.model.Product
import com.squareup.picasso.Picasso

/**
 * Created by Bohdan on 29.10.2017.
 */


class ProductListAdapter(@param:LayoutRes private val layoutId: Int, items: MutableList<Product>)
    : RecyclerView.Adapter<ProductListAdapter.ProductViewHolder>() {

    lateinit var view: View

    var listener: ProductItemClickListener? = null

    var data: MutableList<Product> = items


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        view = LayoutInflater.from(parent.context)
                .inflate(layoutId, parent, false)

        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = data?.get(position)

        holder.name.text = product?.name

        holder.price.text = String.format("%s$",
                (java.lang.Double.valueOf(product?.priceInCents!!.toDouble())!! / 100).toString())

        Picasso.with(holder.image.context)
                .load(product.imageUrl)
                .fit()
                .centerInside()
                //.centerCrop()
                .into(holder.image)

        holder.cardView.setOnClickListener {
            listener?.onProductItemClick(product, position)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }


    class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        @BindView(R.id.tv_name)
        internal lateinit var name: TextView
        @BindView(R.id.tv_price)
        internal lateinit var price: TextView
        @BindView(R.id.image_view_product)
        internal lateinit var image: ImageView
        @BindView(R.id.card_view_product)
        internal lateinit var cardView: CardView

        init {
            ButterKnife.bind(this, view)
        }
    }
}


interface ProductItemClickListener {

    fun onProductItemClick(product: Product, position: Int)
}
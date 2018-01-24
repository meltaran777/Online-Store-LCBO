package com.jaaarl.catalog.model

import android.arch.persistence.room.*

/**
 * Created by Bohdan on 14.12.2017.
 */
@Entity(tableName = "shopping_cart", foreignKeys = [(ForeignKey(entity = Product::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("product_id"),
        onDelete = ForeignKey.NO_ACTION))])
data class ShoppingCartEntity(

        @PrimaryKey(autoGenerate = true)
        var id: Long = 0,

        @ColumnInfo(name = "product_id")
        var productId: Int = -1,

        @ColumnInfo(name = "product_count")
        var productCount: Int = 1
) {
    @Ignore
    var product: Product? = null
}
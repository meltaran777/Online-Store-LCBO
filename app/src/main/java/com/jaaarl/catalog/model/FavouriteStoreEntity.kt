package com.jaaarl.catalog.model

import android.arch.persistence.room.*

/**
 * Created by Bohdan on 14.12.2017.
 */
@Entity(tableName = "favourite_store", foreignKeys = [(ForeignKey(entity = Store::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("store_id"),
        onDelete = ForeignKey.NO_ACTION))])
data class FavouriteStoreEntity(

/*        @PrimaryKey(autoGenerate = true)
        var id: Long = 0,*/

        @PrimaryKey
        @ColumnInfo(name = "store_id")
        var storeId: Int = -1,

        @ColumnInfo(name = "is_favourite")
        var isFavourite:Boolean = false

)
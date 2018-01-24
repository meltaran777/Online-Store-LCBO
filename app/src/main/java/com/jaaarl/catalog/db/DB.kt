package com.jaaarl.catalog.db

import android.arch.persistence.room.*
import android.content.Context
import com.jaaarl.catalog.model.FavouriteStoreEntity
import com.jaaarl.catalog.model.Product
import com.jaaarl.catalog.model.ShoppingCartEntity
import com.jaaarl.catalog.model.Store
import io.reactivex.Flowable
import io.reactivex.Single


/**
 * Created by Bohdan on 11.12.2017.
 */
@Dao
interface StoreDao {

    @Query("SELECT * FROM store")
    fun getAllStores(): Flowable<List<Store>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(s: Store)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(list: Array<Store>)

    @Query("SELECT * FROM store where id = :id")
    fun getStore(id: Int): Store?

    @Delete
    fun delete(s: Store)

    @Update
    fun update(store: Store)
}

@Dao
interface FavouriteStoreDao {

    @Query("SELECT * FROM favourite_store where favourite_store.store_id = :id")
    fun getFavouriteStore(id: Int): FavouriteStoreEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(fs: FavouriteStoreEntity)
}

@Dao
interface ProductDao {

    @Query("SELECT * FROM product")
    fun getAllProduct(): Flowable<List<Product>>

    @Query("SELECT * FROM product INNER JOIN shopping_cart ON product.id = shopping_cart.product_id")
    fun getShoppingCartProducts(): Flowable<List<Product>>

    @Query("SELECT * FROM product INNER JOIN shopping_cart ON product.id = shopping_cart.product_id" +
            " where product.id = :id")
    fun getShoppingCartProduct(id: Int): Product?

    @Query("SELECT * FROM product where product.id = :id")
    fun getProduct(id: Int): Product

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(p: Product)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(list: Array<Product>)

    @Delete
    fun delete(p: Product)
}

@Dao
interface ShoppingCartDao {

    @Query("SELECT * FROM shopping_cart")
    fun getAllShoppingCartItems(): Single<List<ShoppingCartEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(sh: ShoppingCartEntity)

    @Delete
    fun delete(sh: ShoppingCartEntity)

    @Query("DELETE FROM shopping_cart")
    fun clearAll()

    @Query("DELETE FROM shopping_cart where product_id = :productId")
    fun delete(productId: Int)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(sh: ShoppingCartEntity)
}

@Database(entities = [(Product::class), (Store::class),
(ShoppingCartEntity::class), (FavouriteStoreEntity::class)], version = 2)
abstract class MyDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao

    abstract fun storeDao(): StoreDao

    abstract fun favouriteStoreDao(): FavouriteStoreDao

    abstract fun shoppingCartDao(): ShoppingCartDao
}

object Db {

    private var isInited: Boolean = false

    private lateinit var database: MyDatabase

    fun init(context: Context) {
        if (!isInited)
            database = Room.databaseBuilder(context, MyDatabase::class.java, "catalog-db").build()
        isInited = true
    }

    fun getProductDao(): ProductDao {
        return database.productDao()
    }

    fun getStoreDao(): StoreDao {
        return database.storeDao()
    }

    fun getFavouriteStoreDao(): FavouriteStoreDao {
        return database.favouriteStoreDao()
    }

    fun getShoppingCartDao(): ShoppingCartDao {
        return database.shoppingCartDao()
    }
}


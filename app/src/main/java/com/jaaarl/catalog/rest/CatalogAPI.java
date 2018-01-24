package com.jaaarl.catalog.rest;

import com.jaaarl.catalog.rest.response.ProductResponse;
import com.jaaarl.catalog.rest.response.StoreResponse;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;


/**
 * Created by Bohdan on 22.10.2017.
 */

public interface CatalogAPI {
    String BASE_URL = "http://lcboapi.com";
    String API_KEY  = "MDo4NWZjZjMyMi1mOWQwLTExZTYtODdmYS05NzViMDg0MWQwMWI6QzhlMTNLcTZGZGNtV2ltZW9CcUhRcjd5b1RJZ3pqNFo4bkhS";

    @GET("/stores")
    Flowable<StoreResponse> getStores(@Query("page") int page,
                                      @Query("per_page") int perPage,
                                      @Query("where") String where,
                                      @Query("q") String matchCause);

    @GET("/products")
    Flowable<ProductResponse> getProducts(@Query("page") int page,
                                            @Query("per_page") int perPage,
                                            @Query("where") String where,
                                            @Query("where_not") String whereNot,
                                            @Query("order") String order,
                                            @Query("q") String matchCause);

    @GET("/products")
    Flowable<ProductResponse> getStoreProducts(@Query("page") int page,
                                                 @Query("per_page") int perPage,
                                                 @Query("store_id") int storeId);

/*    @GET("/inventories")
    Observable<ProductResponse> getSimilarProducts(@Query("page") int page,
                                                 @Query("per_page") int perPage,
                                                 @Query("product_id") int productId);*/
}

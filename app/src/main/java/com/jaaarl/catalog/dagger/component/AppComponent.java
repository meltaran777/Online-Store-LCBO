package com.jaaarl.catalog.dagger.component;

import com.jaaarl.catalog.dagger.module.ApiModule;
import com.jaaarl.catalog.rest.CatalogDataLoader;
import com.jaaarl.catalog.ui.productsearch.ProductSearchFragment;
import com.jaaarl.catalog.ui.productcategories.ProductTabFragment;
import com.jaaarl.catalog.ui.storesearch.ProductListDialogFragment;
import com.jaaarl.catalog.ui.storesearch.StoresFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Bohdan on 27.10.2017.
 */

@Component(modules = {ApiModule.class})
@Singleton
public interface AppComponent {

    void inject(StoresFragment storesFragment);

    void inject(ProductTabFragment productTabFragment);

    void inject(CatalogDataLoader catalogDataLoader);

    void inject(ProductSearchFragment productSearchFragment);

    void inject(ProductListDialogFragment productListDialogFragment);
}

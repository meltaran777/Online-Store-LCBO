package com.jaaarl.catalog;

import com.jaaarl.catalog.dagger.component.AppComponent;
import com.jaaarl.catalog.dagger.component.DaggerAppComponent;
import com.jaaarl.catalog.dagger.module.ApiModule;
import com.jaaarl.catalog.db.Db;
import com.jaaarl.catalog.utils.MyPreferences;

/**
 * Created by Bohdan on 11.12.2017.
 */

public class MyApplication extends android.app.Application {

    private static AppComponent component;


    @Override
    public void onCreate() {
        super.onCreate();

        component = buildComponent();

        Db.INSTANCE.init(this);
        MyPreferences.INSTANCE.init(this);
    }

    protected AppComponent buildComponent() {
        return DaggerAppComponent.builder()
                .apiModule(new ApiModule())
                .build();
    }

    public static AppComponent getAppComponent() {
        return component;
    }
}

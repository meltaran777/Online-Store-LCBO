package com.jaaarl.catalog.dagger.module;

import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jaaarl.catalog.rest.CatalogAPI;
import com.jaaarl.catalog.rest.CatalogDataLoader;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;


import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Bohdan on 22.10.2017.
 */
@Module
public class ApiModule {

    private OnConnectionTimeoutListener listener;

    @Provides
    @Singleton
    CatalogAPI getCatalogAPI() {
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.addInterceptor(this::onOnIntercept)
                .addInterceptor(logging)
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS);

        OkHttpClient client = httpClient.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(CatalogAPI.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        return retrofit.create(CatalogAPI.class);
    }

    @Provides
    CatalogDataLoader getLoadingManager() {
        return new CatalogDataLoader();
    }

    private Response onOnIntercept(Interceptor.Chain chain) throws IOException {
        try {
            Request original = chain.request();

            // Request customization: add request headers
            Request.Builder requestBuilder = original.newBuilder()
                    .addHeader("Authorization: Token", "token=" + CatalogAPI.API_KEY)
                    .method(original.method(), original.body());

            Request request = requestBuilder.build();

            return chain.proceed(request);
        } catch (SocketTimeoutException exception) {
            exception.printStackTrace();
            if (listener != null)
                listener.onConnectionTimeout();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return chain.proceed(chain.request());
    }

    public OnConnectionTimeoutListener getListener() {
        return listener;
    }

    public void setListener(OnConnectionTimeoutListener listener) {
        this.listener = listener;
    }


    public interface OnConnectionTimeoutListener {

        void onConnectionTimeout();
    }
}

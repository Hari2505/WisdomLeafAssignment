package com.wisdomleafassignment.singelton;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.multidex.MultiDexApplication;

import com.facebook.stetho.Stetho;
import com.wisdomleafassignment.apipresenter.ApiConstants;
import com.wisdomleafassignment.apipresenter.RestApi;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


@SuppressLint("StaticFieldLeak")
public class AppController extends MultiDexApplication {
    private static AppController mInstance;
    public static Context context;
    public RestApi service;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        context = this;

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        Stetho.initializeWithDefaults(this);
        service = retrofit.create(RestApi.class);
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }


}

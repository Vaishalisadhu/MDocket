package com.m_docket.network;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiHelper {



   public static final String BASE_URL = "http://api.themoviedb.org/3/";
   // public static final String BASE_URL = "https://staging.alessaonline.com/apibridge/services/";
    //public static final String BASE_URL = "https://alessaonline.com/apibridge/services/";


    private OkHttpClient okHttpClient;

    private ApiHelper(Context context) {
        okHttpClient = OkHttpClientHelper.provideOkHttpClient(context);
    }

    public static ApiHelper getInstance(Context context) {
        return new ApiHelper(context);
    }

    private Retrofit provideRestAdapter() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public <S> S getService(Class<S> serviceClass) {
        return provideRestAdapter().create(serviceClass);
    }

    private static Retrofit retrofit = null;


    private static Gson gson = new GsonBuilder()
            .setLenient()
            .create();


    public static synchronized Retrofit getApiHelper() {
        // if (retrofit == null) {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(2, TimeUnit.MINUTES)
                .readTimeout(7, TimeUnit.MINUTES)
                //.readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();

        HttpLoggingInterceptor logging1 = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d("track", "----" + message);
            }
        });
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging1)
                .build();

        try {

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(client)
                    .client(okHttpClient)

                    .build();
        } catch (Exception cc) {
            cc.printStackTrace();
            cc.getMessage();
            cc.getMessage();
        }
        // }
        return retrofit;
    }


}

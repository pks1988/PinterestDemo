package com.pinterest.demo.network;

import android.content.Context;
import android.util.Log;

import com.pinterest.demo.App;
import com.pinterest.demo.Exceptions.NoConnectivityException;
import com.pinterest.demo.interfaces.InternetConnectionListener;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Pravesh Sharma on 04-09-2018.
 */
public class ApiClient {
    private static Retrofit retrofit = null;
    private static int REQUEST_TIMEOUT = 60;
    private static OkHttpClient okHttpClient;
    private static InternetConnectionListener mInternetConnectionListener;

    public static void setInternetConnectionListener(InternetConnectionListener listener) {
        mInternetConnectionListener = listener;
    }

    public static void removeInternetConnectionListener() {
        mInternetConnectionListener = null;
    }

    public static Retrofit getClient(Context context) {

        if (okHttpClient == null)
            initOkHttp(context);

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constant.BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }


    private static void initOkHttp(final Context context) {
        OkHttpClient.Builder httpClient = new OkHttpClient().newBuilder()
                .connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(interceptor);

        try {
            httpClient.addInterceptor(new ConnectivityInterceptor(context) {
                @Override
                public boolean isInternetAvailable() {
//                    mInternetConnectionListener.onInternetAvailable();
                    Log.i("internet :", "internet available");
                    return App.isInternetAvailable(context);
                }

                @Override
                public void onInternetUnavailable() {
                    Log.i("internet :", "internet unavailable");
                    mInternetConnectionListener.onInternetUnavailable();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }


        okHttpClient = httpClient.build();
    }
}

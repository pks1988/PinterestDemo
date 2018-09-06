package com.pinterest.demo.network;

import android.content.Context;
import android.support.annotation.NonNull;

import com.pinterest.demo.App;
import com.pinterest.demo.Exceptions.NoConnectivityException;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Pravesh Sharma on 04-09-2018.
 */
public abstract class ConnectivityInterceptor implements Interceptor {

    public abstract boolean isInternetAvailable();

    public abstract void onInternetUnavailable();

    private Context mContext;

    public ConnectivityInterceptor(Context context) {
        mContext = context;
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        /*if(App.isInternetAvailable(mContext)){
            isInternetAvailable();
        }else {
            onInternetUnavailable();
        }*/

        if(!isInternetAvailable())
            onInternetUnavailable();

        Request.Builder builder = chain.request().newBuilder();
        return chain.proceed(builder.build());
    }
}

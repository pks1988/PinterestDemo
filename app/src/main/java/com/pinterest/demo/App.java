package com.pinterest.demo;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.pinterest.demo.interfaces.InternetConnectionListener;
import com.pinterest.demo.network.ApiClient;
import com.pinterest.demo.network.ApiInterface;

/**
 * Created by Pravesh Sharma on 04-09-2018.
 */
public class App extends Application {

    private static App mApp;
    private static ApiInterface mApiInterface;
    private static Context applicationContext;
    private InternetConnectionListener mInternetConnectionListener;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static App getInstance() {
        if (mApp == null) {
            mApp = new App();
        }
        return mApp;
    }

    public static ApiInterface getApiInterface(Application application) {
        if (mApiInterface == null) {
            mApiInterface = ApiClient.getClient(application).create(ApiInterface.class);
        }
        return mApiInterface;
    }




    public static boolean isInternetAvailable(Context mContext) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


}

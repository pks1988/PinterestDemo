package com.pinterest.demo.controllers;

import android.accounts.NetworkErrorException;
import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.pinterest.demo.App;
import com.pinterest.demo.Exceptions.NoConnectivityException;
import com.pinterest.demo.R;
import com.pinterest.demo.models.User;
import com.pinterest.demo.models.UserDetail;
import com.pinterest.demo.network.ApiClient;
import com.pinterest.demo.network.ApiInterface;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Pravesh Sharma on 31-08-2018.
 */

public class UserController {
    private MutableLiveData<List<UserDetail>> mUserLiveData = new MutableLiveData<>();
    ArrayList<UserDetail> abc = new ArrayList<>();
    private static ApiInterface mApiInterface = null;
    private Call<List<UserDetail>> call;
    private Callback<List<UserDetail>> callback;


    public UserController(Application application) {
        if (mApiInterface == null)
            mApiInterface = App.getApiInterface(application);

        fetchUserDetails();
    }


    public void fetchUserDetails() {
        call = mApiInterface.getUserData();
        callback = new Callback<List<UserDetail>>() {
            @Override
            public void onResponse(Call<List<UserDetail>> call, Response<List<UserDetail>> response) {
                if (response.isSuccessful()) {
                    mUserLiveData.postValue(response.body());
                } else {
                }
            }

            @Override
            public void onFailure(Call<List<UserDetail>> call, Throwable t) {

            }
        };
        call.enqueue(callback);
    }


    public MutableLiveData<List<UserDetail>> getUserLiveData() {
        Log.v("live data size:", String.valueOf(mUserLiveData));
        return mUserLiveData;
    }

    public void retry() {
        if (call != null)
            call.clone().enqueue(callback);
    }
}

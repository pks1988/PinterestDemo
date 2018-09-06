package com.pinterest.demo.network;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.pinterest.demo.models.User;
import com.pinterest.demo.models.UserDetail;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Pravesh Sharma on 04-09-2018.
 */
public interface ApiInterface {

    @GET("raw/wgkJgazE/")
    Call<List<UserDetail>> getUserData();
}

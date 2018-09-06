package com.pinterest.demo;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.pinterest.demo.adapters.UserAdapter;
import com.pinterest.demo.interfaces.InternetConnectionListener;
import com.pinterest.demo.models.User;
import com.pinterest.demo.models.UserDetail;
import com.pinterest.demo.network.ApiClient;
import com.pinterest.demo.viewmodels.UserViewModel;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PinnedImageActivity extends AppCompatActivity implements InternetConnectionListener {

    @BindView(R.id.userRecycler)
    RecyclerView userRecycler;
    @BindView(R.id.refreshUsers)
    SwipeRefreshLayout refreshUsers;
    private String TAG = this.getClass().getSimpleName();
    UserViewModel userViewModel;
    private UserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinned_image);
        ButterKnife.bind(this);
        ApiClient.setInternetConnectionListener(this);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        showUsers();
        registerUserObserver();
    }

    private void showUsers() {
        refreshUsers.setRefreshing(true);
        adapter = new UserAdapter(this);
        userRecycler.setAdapter(adapter);
        userRecycler.setLayoutManager(new LinearLayoutManager(this));
    }

    private void registerUserObserver() {
        userViewModel.getAllUsers().observe(this, new Observer<List<UserDetail>>() {
            @Override
            public void onChanged(@Nullable List<UserDetail> users) {
                Log.v(TAG, Objects.requireNonNull(users).toString());
                adapter.updateUsers(users);
                refreshUsers.setRefreshing(false);

            }
        });
    }

    @Override
    public void onInternetUnavailable() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                refreshUsers.setRefreshing(false);
                Toast.makeText(PinnedImageActivity.this, "internet not available", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onInternetAvailable() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(PinnedImageActivity.this, "internet connected", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        ApiClient.removeInternetConnectionListener();
    }
}

package com.pinterest.demo.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.pinterest.demo.Constants;
import com.pinterest.demo.R;
import com.pinterest.demo.Widgets.RecyclerTouchListener;
import com.pinterest.demo.adapters.UserAdapter;
import com.pinterest.demo.interfaces.InternetConnectionListener;
import com.pinterest.demo.models.UserDetail;
import com.pinterest.demo.network.ApiClient;
import com.pinterest.demo.viewmodels.UserViewModel;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PinnedImageActivity extends AppCompatActivity implements InternetConnectionListener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.userRecycler)
    RecyclerView userRecycler;
    @BindView(R.id.refreshUsers)
    SwipeRefreshLayout refreshUsers;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.btnRetry)
    Button btnRetry;
    @BindView(R.id.noConnectionParent)
    ConstraintLayout noConnectionParent;
    private String TAG = this.getClass().getSimpleName();
    UserViewModel userViewModel;
    private UserAdapter adapter;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinned_image);
        ButterKnife.bind(this);
        ApiClient.setInternetConnectionListener(this);
        refreshUsers.setOnRefreshListener(this);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        showUsers();
        registerUserObserver();
        setRecyclerClicks();
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
                if (users != null && users.isEmpty()) return;
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
                showNoConnectivityUi();
            }
        });
    }

    @Override
    public void onInternetAvailable() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                hideNoConnectivity();
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        ApiClient.removeInternetConnectionListener();
    }


    void setRecyclerClicks() {

        userRecycler.addOnItemTouchListener(new RecyclerTouchListener(this, userRecycler, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                showUserProfile(position);
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));
    }


    private void showUserProfile(int pos) {
        Intent intent = new Intent(PinnedImageActivity.this, ProfileActivity.class);
        Bundle bundleProfile = new Bundle();
        bundleProfile.putParcelable(Constants.PROFILE_DATA, userViewModel.getUserProfileData(pos));
        intent.putExtra(Constants.PROFILE_BUNDLE_DATA, bundleProfile);
        startActivity(intent);
    }

    private void showNoConnectivityUi() {
        refreshUsers.setVisibility(View.GONE);
        noConnectionParent.setVisibility(View.VISIBLE);
        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userViewModel.retry();
                hideNoConnectivity();
            }
        });
    }

    private void hideNoConnectivity() {
        refreshUsers.setVisibility(View.VISIBLE);
        noConnectionParent.setVisibility(View.GONE);
    }

    @Override
    public void onRefresh() {
       /*As the api do not have the support for the pagination and swipe to refresh feature not able to implement that*/
    }
}
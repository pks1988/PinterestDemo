package com.pinterest.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.image.downloader.FileType;
import com.image.downloader.ImageDownloader;
import com.pinterest.demo.models.UserDetail;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();
    @BindView(R.id.imgLarge)
    ImageView imgLarge;
    @BindView(R.id.imgProfile)
    ImageView imgProfile;
    @BindView(R.id.txtName)
    TextView txtName;
    UserDetail mDetail = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        getExtras();
        setupToolbar();
    }

    private void setupToolbar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            if (mDetail != null)
                getSupportActionBar().setTitle(mDetail.getUser().getName());
        }
    }

    private void getExtras() {
        if (getIntent() != null && getIntent().hasExtra(com.pinterest.demo.Constants.PROFILE_BUNDLE_DATA)) {
            mDetail = getIntent().getBundleExtra(com.pinterest.demo.Constants.PROFILE_BUNDLE_DATA).getParcelable(com.pinterest.demo.Constants.PROFILE_DATA);
            if (mDetail != null)
                updateUi();

        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void updateUi() {
        if (mDetail != null) {
            new ImageDownloader.Builder()
                    .setContext(this)
                    .setAnimation(true)
                    .setFileType(FileType.FILE_TYPE_IMAGE)
                    .setSourceUrl(mDetail.getUrls().getRegular())
                    .setView(imgLarge)
                    .errorPlaceHolder(R.drawable.ic_placeholder)
                    .build();

            new ImageDownloader.Builder()
                    .setContext(this)
                    .setAnimation(true)
                    .setFileType(FileType.FILE_TYPE_IMAGE)
                    .setSourceUrl(mDetail.getUser().getProfileImage().getLarge())
                    .setView(imgProfile)
                    .errorPlaceHolder(R.drawable.ic_user_placeholder)
                    .build();

            txtName.setText(mDetail.getUser().getName());
        }
    }
}

package com.image.downloader;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * Created by Pravesh Sharma on 06-09-2018.
 */
public class ConfigureDownload {
    private final String TAG = this.getClass().getSimpleName();
    private ImageView mView;
    private Context mContext;
    private String mSourceUrl;
    private String mFileType;
    //Optional Parameters
    private int mCacheSize = 0;
    private boolean animate;
    private static Animation animFadeOut = null;
    private int mResId;

    public ConfigureDownload(ImageView mView, Context mContext, String mSourceUrl, String mFileType, int mCacheSize, boolean animate, int mResId) {
        this.mView = mView;
        this.mContext = mContext;
        this.mSourceUrl = mSourceUrl;
        this.mCacheSize = mCacheSize;
        this.mFileType = mFileType;
        this.animate = animate;
        this.mResId = mResId;
        startDownload();
    }

    public void startDownload() {
        if (mFileType == FileType.FILE_TYPE_IMAGE) {
            loadBitmap();

        } else {
            //Configuration for other kinds of download can be done here
        }
    }


    private void loadBitmap() {
        String imageKey = mSourceUrl;
        MemoryCache.getInstance().configureCache(mCacheSize);
        final Bitmap bitmap = MemoryCache.getInstance().getBitmapFromMemCache(imageKey);

        if (animate && animFadeOut == null) {
            animFadeOut = AnimationUtils.loadAnimation(mContext, R.anim.fadein);
        }

        if (bitmap != null) {
            mView.setImageBitmap(bitmap);
            mView.clearAnimation();
            Log.v(TAG,"from cache");
        } else {
            GenericDownloaderTask<Bitmap> mDownloaderTask = new GenericDownloaderTask<>(mFileType, mView);
            mDownloaderTask.setCallbackListener(new GenericDownloaderTask.AsyncCallback<Bitmap>() {
                @Override
                public void downloadCompleted(Bitmap args) {
                    mView.setImageBitmap(args);
                    if (animate)
                        mView.startAnimation(animFadeOut);
                    Log.v(TAG,"from task");
                }

                @Override
                public void downloadFailed() {
                    if (mResId != -1) {
                        mView.setImageResource(mResId);
                    }
                }
            });
            mDownloaderTask.execute(mSourceUrl);
        }
    }
}

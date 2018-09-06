package com.image.downloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

/**
 * Created by Pravesh Sharma on 06-09-2018.
 */
public class ConfigureDownload {
    private final String TAG = this.getClass().getSimpleName();
    private ImageView mView;
    private int targetHeight;
    private int targetWidth;
    private Context mContext;
    private String mSourceUrl;
    private String mFileType;
    //Optional Parameters
    private int mCacheSize = 0;

    public ConfigureDownload(ImageView mView, int targetHeight, int targetWidth, Context mContext, String mSourceUrl, String mFileType, int mCacheSize) {
        this.mView = mView;
        this.targetHeight = targetHeight;
        this.targetWidth = targetWidth;
        this.mContext = mContext;
        this.mSourceUrl = mSourceUrl;
        this.mCacheSize = mCacheSize;
        this.mFileType = mFileType;

        startDownload();
    }

    public void startDownload() {
        if (mFileType == Constants.FILE_TYPE_IMAGE) {
            loadBitmap();

        } else {
            //Configuration for other kinds of download can be done here
        }
    }


    private void loadBitmap() {
        String imageKey = mSourceUrl;
        MemoryCache.getInstance().configureCache(mCacheSize);
        final Bitmap bitmap = MemoryCache.getInstance().getBitmapFromMemCache(imageKey);

        if (bitmap != null) {
            mView.setImageBitmap(bitmap);
        } else {
            GenericDownloaderTask<Bitmap> mDownloaderTask = new GenericDownloaderTask<>(mFileType);
            mDownloaderTask.setCallbackListener(new GenericDownloaderTask.AsyncCallback<Bitmap>() {
                @Override
                public void downloadCompleted(Bitmap args) {
                    mView.setImageBitmap(args);
                    Log.v(TAG,"dowmload callback complete");
                }

                @Override
                public void downloadFailed() {

                }
            });
            mDownloaderTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, mSourceUrl);
        }
    }
}

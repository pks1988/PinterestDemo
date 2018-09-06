package com.image.downloader;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

/**
 * Created by Pravesh Sharma on 06-09-2018.
 */
public class ImageDownloader {

    protected ImageView mView;
    protected int targetHeight;
    protected int targetWidth;
    protected Context mContext;
    protected String mSourceUrl;
    protected String mFileType;

    //Optional Parameters
    protected int mCacheSize = 0;

    public ImageDownloader() {
    }

    public ImageDownloader(ImageView mView, int targetHeight, int targetWidth, Context mContext, String mSourceUrl, String mFileType, int mCacheSize) {
        this.mView = mView;
        this.targetHeight = targetHeight;
        this.targetWidth = targetWidth;
        this.mContext = mContext;
        this.mSourceUrl = mSourceUrl;
        this.mCacheSize = mCacheSize;
        this.mFileType = mFileType;

        configureAndStartDownload();
    }

    private void configureAndStartDownload() {
        new ConfigureDownload(mView, targetHeight, targetWidth, mContext, mSourceUrl, mFileType, mCacheSize);
    }


    public static class Builder {
        private ImageView mView;
        private int targetHeight;
        private int targetWidth;
        private Context mContext;
        private String mSourceUrl;
        private String mFileType;

        //Optional Parameters
        private int mCacheSize = 0;

        public Builder setView(ImageView mView) {
            this.mView = mView;
            return this;
        }

        public Builder setTargetHeight(int targetHeight) {
            this.targetHeight = targetHeight;
            return this;
        }

        public Builder setTargetWidth(int targetWidth) {
            this.targetWidth = targetWidth;
            return this;
        }

        public Builder setContext(Context mContext) {
            this.mContext = mContext;
            return this;
        }

        public Builder setSourceUrl(String mSourceUrl) {
            this.mSourceUrl = mSourceUrl;
            return this;
        }

        public Builder setFileType(String mFileType) {
            this.mFileType = mFileType;
            return this;
        }

        public Builder setCacheSize(int mCacheSize) {
            this.mCacheSize = mCacheSize;
            return this;
        }

        public ImageDownloader build() throws IllegalArgumentException {
            if (TextUtils.isEmpty(mSourceUrl)) {
                throw new IllegalArgumentException("Url cannot be null or empty");
            } else if (TextUtils.isEmpty(mFileType)) {
                throw new IllegalArgumentException("File type cannot be null or empty");
            } else if (mFileType.equals(Constants.FILE_TYPE_IMAGE) && mView == null) {
                throw new IllegalArgumentException("The target ImageView cannot be null");
            }
            return new ImageDownloader(mView, targetHeight, targetWidth, mContext, mSourceUrl, mFileType, mCacheSize);
        }
    }
}
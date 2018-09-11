package com.image.downloader;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

/**
 * Created by Pravesh Sharma on 06-09-2018.
 */
public class ImageDownloader {

    private ImageView mView;
    private Context mContext;
    private String mSourceUrl;
    private String mFileType;

    //Optional Parameters
    private int mCacheSize = 0;
    private boolean animate;
    private int mResId=-1;

    private ImageDownloader(ImageView mView, Context mContext, String mSourceUrl, String mFileType, int mCacheSize, boolean animate,int mResId) {
        this.mView = mView;
        this.mContext = mContext;
        this.mSourceUrl = mSourceUrl;
        this.mCacheSize = mCacheSize;
        this.mFileType = mFileType;
        this.animate = animate;
        this.mResId=mResId;

        configureAndStartDownload();
    }

    private void configureAndStartDownload() {
        new ConfigureDownload(mView, mContext, mSourceUrl, mFileType, mCacheSize, animate,mResId);
    }


    public static class Builder {
        private ImageView mView;
        private Context mContext;
        private String mSourceUrl;
        private String mFileType;

        //Optional Parameters
        private int mCacheSize = 0;
        private boolean animate;
        private int mResId=-1;

        public Builder setView(ImageView mView) {
            this.mView = mView;
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

        public Builder setAnimation(boolean animate) {
            this.animate = animate;
            return this;
        }

        public Builder errorPlaceHolder(int mResId) {
            this.mResId = mResId;
            return this;
        }

        public ImageDownloader build() throws IllegalArgumentException {
             ImageDownloader imageDownloader=null;

            if (TextUtils.isEmpty(mSourceUrl)) {
                throw new IllegalArgumentException("Url cannot be null or empty");
            } else if (TextUtils.isEmpty(mFileType)) {
                throw new IllegalArgumentException("File type cannot be null or empty");
            } else if (mFileType.equals(FileType.FILE_TYPE_IMAGE) && mView == null) {
                throw new IllegalArgumentException("The target ImageView cannot be null");
            }
            if(imageDownloader==null) {
                return new ImageDownloader(mView, mContext, mSourceUrl, mFileType, mCacheSize, animate, mResId);
            }else{
                return imageDownloader;
            }
        }

    }
}
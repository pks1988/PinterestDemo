package com.image.downloader;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

/**
 * Created by Pravesh Sharma on 06-09-2018.
 */
public class GenericDownloaderTask<T> extends AsyncTask<String, Void, T> {

    private String TAG = this.getClass().getSimpleName();
    private String mFileType;
    private AsyncCallback callback;
    ImageView mView;


    public interface AsyncCallback<T> {
        void downloadCompleted(T args);

        void downloadFailed();
    }

    public void setCallbackListener(AsyncCallback callback) {
        this.callback = callback;
    }

    public GenericDownloaderTask(String mFileType, ImageView mView) {
        this.mFileType = mFileType;
        this.mView = mView;
    }

    @Override
    protected T doInBackground(String... URL) {
        String data = URL[0];
        T returnVal = null;
        Bitmap bitmap;
        try {
            if (mFileType.equals(FileType.FILE_TYPE_IMAGE)) {
                Log.v(TAG, "url download:" + data);
                bitmap = CompressBitmap.decodeSampledBitmapFromStream(data, mView.getWidth(), mView.getHeight());
                MemoryCache.getInstance().addBitmapToMemoryCache(data, bitmap);
                returnVal = (T) bitmap;

            } else {
                //Code open to be changed for other kind of files that can be downloaded in future
            }

        } catch (Exception e) {
            Log.getStackTraceString(e);
            Log.v(TAG, "image load failed");
        }
        return returnVal;
    }

    @Override
    protected void onPostExecute(T t) {
        if (t == null)
            callback.downloadFailed();
        else
            callback.downloadCompleted(t);

    }


}
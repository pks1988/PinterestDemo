package com.image.downloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * Created by Pravesh Sharma on 06-09-2018.
 */
public class GenericDownloaderTask<T> extends AsyncTask<String, Void, T> {

    private String TAG = this.getClass().getSimpleName();
    private String mFileType;
    private AsyncCallback callback;
    ByteArrayOutputStream bytearrayoutputstream;
    byte[] BYTE;


    public interface AsyncCallback<T> {
        void downloadCompleted(T args);

        void downloadFailed();
    }

    public void setCallbackListener(AsyncCallback callback) {
        this.callback = callback;
    }

    public GenericDownloaderTask(String mFileType) {
        this.mFileType = mFileType;
    }

    @Override
    protected T doInBackground(String... URL) {
        String data = URL[0];
        T returnVal=null;
        try {
            if (mFileType.equals(Constants.FILE_TYPE_IMAGE)) {
                Log.v(TAG, "url download:" + data);
                // Download Image from URL
                InputStream input = new java.net.URL(data).openStream();
                Bitmap bitmap = BitmapFactory.decodeStream(input);
              /*  bitmap.compress(Bitmap.CompressFormat.WEBP, 50, bytearrayoutputstream);
                BYTE = bytearrayoutputstream.toByteArray();
                Bitmap b= BitmapFactory.decodeByteArray(BYTE, 0, BYTE.length);*/
//                memoryCache.addBitmapToMemoryCache(data,bitmap);
                // Decode Bitmap
//                callback.downloadCompleted(BitmapFactory.decodeStream(input));
                MemoryCache.getInstance().addBitmapToMemoryCache(data, bitmap);
                returnVal= (T) bitmap;

            } else {

            }

        } catch (Exception e) {
            Log.getStackTraceString(e);
            callback.downloadFailed();
        }
        return returnVal;
        }

    @Override
    protected void onPostExecute(T t) {
        callback.downloadCompleted(t);

    }
}
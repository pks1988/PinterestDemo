package com.image.downloader;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Created by Pravesh Sharma on 06-09-2018.
 */
public class MemoryCache {


    int maxMemoryAvailable;
    int memoryRequested;
    private static LruCache<String, Bitmap> mMemoryCache = null;
    private static final MemoryCache cacheInstance = new MemoryCache();

    public static MemoryCache getInstance() {
        return cacheInstance;
    }

    private MemoryCache() {
    }

    public void configureCache(int memoryRequested) {
        getMaxAvailableMemory();
        if (memoryRequested > maxMemoryAvailable) {
            throw new OutOfMemoryError("Requested memory is larger then the available memory");
        } else {
            if (memoryRequested > (maxMemoryAvailable / 4)) {
                this.memoryRequested = maxMemoryAvailable / 4;
            } else if (memoryRequested == 0) {
                this.memoryRequested = maxMemoryAvailable / 8;
            }
        }

        createMemoryCache();
    }


    private void getMaxAvailableMemory() {
        maxMemoryAvailable = (int) (Runtime.getRuntime().maxMemory() / 1024);
    }

    public void createMemoryCache() {

        if (mMemoryCache == null) {
            mMemoryCache = new LruCache<String, Bitmap>(memoryRequested) {
                @Override
                protected int sizeOf(String key, Bitmap value) {
                    return value.getByteCount() / 1024;
                }
            };
        }
    }


    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    public Bitmap getBitmapFromMemCache(String key) {
        return mMemoryCache.get(key);
    }
}

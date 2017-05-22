package com.nogi.ochiai.shun.sakamichiblogalert;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.content.AsyncTaskLoader;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by shun on 2017/05/23.
 */

public class HttpAsyncImageLoader extends AsyncTaskLoader<Bitmap> {

    private String mUrl;

    public HttpAsyncImageLoader(Context context, String url) {
        super(context);
        this.mUrl = url;
    }

    @Override
    public Bitmap loadInBackground() {

        try {
            URL url = new URL(mUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            return BitmapFactory.decodeStream(connection.getInputStream());
        } catch (IOException e) {
            // 404はここでキャッチする
            e.printStackTrace();
        }

        return null;
    }
}

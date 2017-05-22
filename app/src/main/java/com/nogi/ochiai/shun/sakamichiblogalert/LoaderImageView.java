package com.nogi.ochiai.shun.sakamichiblogalert;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.support.v4.app.LoaderManager;

/**
 * Created by shun on 2017/05/23.
 */

public class LoaderImageView extends ImageView implements LoaderManager.LoaderCallbacks<Bitmap> {

    private String mUrl;

    public LoaderImageView(Context context) {
        super(context);
    }

    public LoaderImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LoaderImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    // 表示する画像のURLをセット
    public void setUrl(String url){
        this.mUrl = url;
    }

    @Override
    public Loader<Bitmap> onCreateLoader(int id, Bundle args) {
        return new HttpAsyncImageLoader(getContext(), this.mUrl);
    }

    @Override
    public void onLoadFinished(Loader<Bitmap> loader, Bitmap data) {
        setImageBitmap(data);
    }

    @Override
    public void onLoaderReset(Loader<Bitmap> loader) {

    }
}

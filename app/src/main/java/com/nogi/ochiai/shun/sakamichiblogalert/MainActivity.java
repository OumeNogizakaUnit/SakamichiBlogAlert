package com.nogi.ochiai.shun.sakamichiblogalert;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcel;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.io.LineNumberReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private CountDownLatch latch = new CountDownLatch(1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getRequest("SELECT id,auth,title,datestr,url,img FROM entry ORDER BY date DESC LIMIT 30");
        View.OnClickListener searchClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClassName("com.nogi.ochiai.shun.sakamichiblogalert","com.nogi.ochiai.shun.sakamichiblogalert.SearchActivity");

                startActivity(intent);
            }
        };
        this.findViewById(R.id.search).setOnClickListener(searchClickListener);

        View.OnClickListener settingClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClassName("com.nogi.ochiai.shun.sakamichiblogalert","com.nogi.ochiai.shun.sakamichiblogalert.SettingActivity");

                startActivity(intent);
            }
        };
        this.findViewById(R.id.setting).setOnClickListener(settingClickListener);

    }

    public void getRequest(final String query) {

        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String result = "";
                // リクエストボディを作る
                RequestBody requestBody = new FormBody.Builder()
                        .add("query", query)
                        .build();
                // リクエストオブジェクトを作って
                Request request = new Request.Builder()
                        .url("http://terada-ranze.mydns.jp/blogAPI.php")
                        .post(requestBody)
                        .build();

                // クライアントオブジェクトを作って
                OkHttpClient client = new OkHttpClient();

                // リクエストして結果を受け取って
                try {
                    Response response = client.newCall(request).execute();
                    result = response.body().string();
                } catch (IOException e) {
                    result = e.getMessage();
                }

                // 返す
                return result;
            }
            @Override
            protected void onPostExecute(String result) {
                Log.d("Async", result);
                /* 結果をCardViewに追加していく */

                try {
                    JSONArray json = new JSONArray(result);

                    LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                    LinearLayout cardLinear = (LinearLayout)findViewById(R.id.card_list);
                    cardLinear.removeAllViews();

                    for(int i = 0; i < json.length(); i++){
                        final JSONObject item = json.getJSONObject(i);

                        LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.card, null);
                        CardView cardView = (CardView)linearLayout.findViewById(R.id.card_item);
                        TextView card_title = (TextView)linearLayout.findViewById(R.id.TitleInCard);
                        TextView card_auth = (TextView)linearLayout.findViewById(R.id.AuthInCard);
                        TextView card_datestr = (TextView)linearLayout.findViewById(R.id.DateStringInCard);
                        card_title.setTag(i);
                        card_auth.setTag(i);
                        card_datestr.setTag(i);
                        card_title.setText(item.getString("title"));
                        card_auth.setText(item.getString("auth"));
                        card_datestr.setText(item.getString("datestr"));

                        //imageを取得
                        ImageView image = (ImageView) linearLayout.findViewById(R.id.imageView);

                         //画像取得スレッド起動
                        latch = new CountDownLatch(1);
                         ImageGetTask task = new ImageGetTask(image);
                         task.execute(item.getString("img"));
                         Log.d("getRequest",item.getString("img"));
                        try {
                            latch.await();
                        }catch (InterruptedException e) {
                            e.getStackTrace();
                        }
                        cardView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.getString("url")));
                                    startActivityForResult(intent, 0);
                                }catch (JSONException e) {
                                    e.getStackTrace();
                                }
                            }
                        });

                        cardLinear.addView(linearLayout,i);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.execute();
    }

    class ImageGetTask extends AsyncTask<String,Void,Bitmap> {
        private ImageView image;

        public ImageGetTask(ImageView _image) {
            image = _image;
        }
        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap image;
            try {
                URL imageUrl = new URL(params[0]);
                InputStream imageIs;
                imageIs = imageUrl.openStream();
                image = BitmapFactory.decodeStream(imageIs);
                return image;
            } catch (MalformedURLException e) {
                return null;
            } catch (IOException e) {
                return null;
            }finally {
                latch.countDown();
            }
        }
        @Override
        protected void onPostExecute(Bitmap result) {
            // 取得した画像をImageViewに設定します。
            image.setImageBitmap(result);
        }
    }

}

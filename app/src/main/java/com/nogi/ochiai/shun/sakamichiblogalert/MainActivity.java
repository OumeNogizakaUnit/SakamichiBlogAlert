package com.nogi.ochiai.shun.sakamichiblogalert;

import android.content.Context;
import android.content.Intent;
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
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getRequest("SELECT id,auth,title,datestr,url,img FROM entry ORDER BY date DESC LIMIT 100");
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

                    for(int i = 0; i < json.length(); i++) {
                        JSONObject item = json.getJSONObject(i);

                        LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.card_layout, null);
                        TextView card_title = (TextView)linearLayout.findViewById(R.id.card_item_title);
                        TextView card_auth = (TextView)linearLayout.findViewById(R.id.card_item_auth);
                        TextView card_datestr = (TextView)linearLayout.findViewById(R.id.card_item_datestr);
                        card_title.setTag(i);
                        card_auth.setTag(i);
                        card_datestr.setTag(i);
                        card_title.setText(item.getString("title"));
                        card_auth.setText(item.getString("auth"));
                        card_datestr.setText(item.getString("datestr"));

                        cardLinear.addView(linearLayout,i);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.execute();
    }

}

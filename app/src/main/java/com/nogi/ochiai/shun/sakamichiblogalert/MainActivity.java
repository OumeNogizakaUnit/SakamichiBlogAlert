package com.nogi.ochiai.shun.sakamichiblogalert;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;


import java.io.IOException;

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

        getRequest("select id,auth,title,datestr from entry limit 100");
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
                String result = null;
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
                    e.printStackTrace();
                }

                // 返す
                return result;
            }
            @Override
            protected void onPostExecute(String result) {
                Log.d("Async", result);
            }
        }.execute();
    }
}

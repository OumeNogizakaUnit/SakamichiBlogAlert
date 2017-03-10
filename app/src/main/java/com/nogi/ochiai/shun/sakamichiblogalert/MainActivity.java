package com.nogi.ochiai.shun.sakamichiblogalert;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
}

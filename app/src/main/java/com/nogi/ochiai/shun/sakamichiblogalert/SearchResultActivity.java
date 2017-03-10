package com.nogi.ochiai.shun.sakamichiblogalert;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class SearchResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        // 値の受取
        Intent intent = getIntent();
        String searchWord = intent.getStringExtra("com.nogi.ochiai.shun.sakamichiblogalert.searchWords");
        TextView textView = (TextView)this.findViewById(R.id.search_word_result);
        textView.setText(searchWord);

        WebView webView = (WebView) this.findViewById(R.id.webview);
        //リンクをタップしたときに標準ブラウザを起動させない
        webView.setWebViewClient(new WebViewClient());

        //最初にgoogleのページを表示する。
        webView.loadUrl("http://blog.nogizaka46.com/third/2017/03/037256.php");

        //jacascriptを許可する
        webView.getSettings().setJavaScriptEnabled(true);
    }
}

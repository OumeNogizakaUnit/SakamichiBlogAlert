package com.nogi.ochiai.shun.sakamichiblogalert;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        final EditText editText = (EditText)this.findViewById(R.id.search_word);

        View.OnClickListener searchResultClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClassName("com.nogi.ochiai.shun.sakamichiblogalert","com.nogi.ochiai.shun.sakamichiblogalert.SearchResultActivity");

                String searchWord = editText.getText().toString();
                Log.d("SearchActivity.onCreate","searchWord: " + searchWord);
                intent.putExtra("com.nogi.ochiai.shun.sakamichiblogalert.searchWords", searchWord);

                startActivity(intent);
            }
        };
        this.findViewById(R.id.search_result).setOnClickListener(searchResultClickListener);

        View.OnClickListener browserClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://blog.nogizaka46.com/third/2017/03/037256.php"));
                startActivityForResult(intent, 0);
            }
        };
        this.findViewById(R.id.browser).setOnClickListener(browserClickListener);
    }
}

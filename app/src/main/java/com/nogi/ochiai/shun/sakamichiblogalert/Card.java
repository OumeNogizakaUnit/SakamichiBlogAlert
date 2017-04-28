package com.nogi.ochiai.shun.sakamichiblogalert;

import android.graphics.Bitmap;

import java.util.Date;

/**
 * Created by shun on 2017/04/29.
 */

public class Card {
    /**
     * カードに持たせたい情報
     */
    private String Auth;
    private String Title;
    private String URL;
    private Bitmap Image;
    private String DateString;

    /*** コンストラクタ ***/
    public Card(String Auth, String Title, String URL, String DateString) {
        this.Auth = Auth;
        this.Title = Title;
        this.URL = URL;
        this.DateString = DateString;
    }

    /* getter */
    public String getAuth(){
        return Auth;
    }

    public String getTitle() {
        return Title;
    }

    public String getURL() {
        return URL;
    }

    public String getDateString() {
        return DateString;
    }
}

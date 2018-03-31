package com.sustentate.app.models;

import java.util.Date;

/**
 * Created by emzas on 27/3/2018.
 */

public class Tip  {

    private String text;
    private String title;
    private String imageUrl;
    private long date;
    private long id;

    public Tip(String text, String title, String imageUrl, String getUrlToImage, long date, long id) {
        this.text = text;
        this.title = title;
        this.imageUrl = imageUrl;
        this.date = date;
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }


    public long getDate() {
        return date;
    }

    public long getId() {
        return id;
    }
}

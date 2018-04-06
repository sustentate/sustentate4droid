package com.sustentate.app.models;

/**
 * Created by emzas on 27/3/2018.
 */

public class Tip  {

    private String text;
    private String title;
    private String imageUrl;
    private long date;
    private long id;

    public Tip(String text, String title, String imageUrl, long date, long id) {
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

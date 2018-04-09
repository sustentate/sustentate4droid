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

    public Tip() {
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

    public void setText(String text) {
        this.text = text;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public void setId(long id) {
        this.id = id;
    }
}

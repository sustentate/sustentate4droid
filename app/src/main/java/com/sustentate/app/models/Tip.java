package com.sustentate.app.models;

/**
 * Created by emzas on 27/3/2018.
 */

public class Tip  {

    private String text;
    private String title;
    private String imageUrl;
    private String getUrlToImage;
    private Integer date;
    private Integer id;

    public Tip(String text, String title, String imageUrl, String getUrlToImage, Integer date, Integer id) {
        this.text = text;
        this.title = title;
        this.imageUrl = imageUrl;
        this.getUrlToImage = getUrlToImage;
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

    public String getUrlToImage() {
        return getUrlToImage;
    }

    public Integer getDate() {
        return date;
    }

    public Integer getId() {
        return id;
    }
}

package ar.com.sustentate.com.models;

import java.util.Date;

/**
 * Created by emzas on 27/3/2018.
 */

public class Evento {


    private String title;
    private String text;
    private String imageUrl;
    private String place;
    private Date date;
    private Date dateEnd;
    private long id;


    public Evento() {
        this.title = title;
        this.text = text;
        this.imageUrl = imageUrl;
        this.place = place;
        this.date = date;
        this.dateEnd = dateEnd;
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getTitle() {
        return title;
    }

    public Date getDateEnd() { return dateEnd;}

    public String getPlace() {
        return place;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setDate(Date date) {this.date = date;}

    public void setId(long id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}


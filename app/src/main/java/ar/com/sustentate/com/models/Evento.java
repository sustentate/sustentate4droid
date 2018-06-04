package ar.com.sustentate.com.models;

import org.joda.time.DateTime;

/**
 * Created by emzas on 27/3/2018.
 */

public class Evento {


    private String title;
    private String text;
    private String imageUrl;
    private String place;
    private long date;
    private long dateEnd;
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

    public long getDate() {
        return date;
    }

    public DateTime getRealDate(){ return DateTime.parse(String.valueOf(date));}

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

    public long getDateEnd() { return dateEnd;}

    public DateTime getRealDateEnd(){return DateTime.parse(String.valueOf(dateEnd));}

    public String getPlace() {
        return place;
    }

    public void setDateEnd(long dateEnd) {
        this.dateEnd = dateEnd;
    }

    public void setRealDateEnd(DateTime date) {date = DateTime.parse(String.valueOf(dateEnd));}

    public void setPlace(String place) {
        this.place = place;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setDate(long date) {this.date = date;}

    public void setRealDate(DateTime date) {date = DateTime.parse(String.valueOf(date));}

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


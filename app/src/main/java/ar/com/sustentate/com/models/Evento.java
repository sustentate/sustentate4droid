package ar.com.sustentate.com.models;

import org.jetbrains.annotations.NotNull;

import java.util.Date;

/**
 * Created by emzas on 27/3/2018.
 */

public class Evento {
    private String _id;
    private String _rev;

    @NotNull
    private String title;

    @NotNull
    private String description;

    private boolean published;

    private boolean promoted;

    @NotNull
    private String address;

    @NotNull
    private Long price;

    @NotNull
    private String link;

    @NotNull
    private Date startDateTime;

    //@NotNull
    //private EventType type;

    @NotNull
    private Contact contact;

    public Evento(String aTitle, String aDescription, String anAddress, String aLink, Date aDateTime, /*EventType type,*/ Contact aContact, Long price) {
        this.title = aTitle;
        this.description = aDescription;
        this.published = false;
        this.promoted = false;
        this.address = anAddress;
        this.link = aLink;
        this.startDateTime = aDateTime;
        //this.type = type;
        this.contact = aContact;
        this.price = price;
    }

    public Evento() {
    }

    public String getId() {
        return this._id;
    }

    public String getRev() {
        return this._rev;
    }

    public boolean getPromoted() {
        return this.published;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public boolean isPublished() {
        return promoted;
    }

    public String getAddress() {
        return address;
    }

    public String getLink() {
        return link;
    }

    public Date getStartDateTime() {
        return startDateTime;
    }

    public Contact getContact() {
        return contact;
    }

    /*public EventType getType() {
        return type;
    }*/

    public Long getPrice() {
        return price;
    }

    public void set_id(String _id) {this._id = _id;}

    public void set_rev(String _rev) {this._rev = _rev;}

    public void setTitle(@NotNull String title) {this.title = title;}

    public void setDescription(@NotNull String description) {this.description = description;}

    public void setPublished(boolean published) {this.published = published;}

    public void setPromoted(boolean promoted) {this.promoted = promoted;}

    public void setAddress(@NotNull String address) {
        this.address = address;
    }

    public void setPrice(@NotNull Long price) {
        this.price = price;
    }

    public void setLink(@NotNull String link) {
        this.link = link;
    }

    public void setStartDateTime(@NotNull Date startDateTime) {
        this.startDateTime = startDateTime;
    }

    public String getDay(){

        if (this.startDateTime == null) {
            return "3";
        }
        String nd = String.valueOf(this.startDateTime.getDate());
        return nd;
    }

    /*public void setType(@NotNull EventType type) {
        this.type = type;
    }*/

    public void setContact(@NotNull Contact contact) {
        this.contact = contact;
    }

}


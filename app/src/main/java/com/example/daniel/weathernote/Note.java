package com.example.daniel.weathernote;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Daniel on 3/3/2016.
 */
public class Note {

    private UUID mId;
    private String mTitle;
    private Date mDate;
    private String mType;

    public Note() {
        this(UUID.randomUUID());
    }

    public Note(UUID id) {
        mId = id;
        mDate = new Date();
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }
}

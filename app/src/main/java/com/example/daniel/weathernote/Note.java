package com.example.daniel.weathernote;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Daniel on 3/3/2016.
 */
public class Note implements Serializable{

    private UUID mId;
    private String mTitle;
    private String mDescription;
    private Date mDate;
    private int mTypePosition;

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

    public String getDescription() {
        return mDescription;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public int getType() {
        return mTypePosition;
    }

    public void setType(int type) {
        mTypePosition = type;
    }
}

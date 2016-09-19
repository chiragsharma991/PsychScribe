package com.psychscribe.notes.model;

/**
 * Created by ubuntu on 2/8/16.
 */
public class NotesListModel {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String name;
    public String date;

    public int getImage() {
        return images;
    }

    public void setImage(int image) {
        this.images = image;
    }

    public int images;
}

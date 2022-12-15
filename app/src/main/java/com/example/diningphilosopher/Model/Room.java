package com.example.diningphilosopher.Model;

import java.util.concurrent.Semaphore;

public class Room extends Resource {
    String Title;
    String imgURL;


    public Room(Semaphore s, int CategoryType, String title, String imgURL) {
        super(s, CategoryType);
        Title = title;
        this.imgURL = imgURL;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    @Override
    public String toString() {
        return "Room{" +
                "semaphore=" + semaphore +
                ", Title='" + Title + '\'' +
                ", imgURL='" + imgURL + '\'' +
                '}';
    }
}

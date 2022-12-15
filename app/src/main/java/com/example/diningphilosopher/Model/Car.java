package com.example.diningphilosopher.Model;

import java.util.concurrent.Semaphore;

public class Car extends Resource {
    private String Title;
    private String imgURL;


    public Car(int permits, int CategoryType, String type, String imgURL) {
        super(new Semaphore(5), CategoryType);
        this.Title = type;
        this.imgURL = imgURL;
    }


    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        this.Title = title;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    @Override
    public String toString() {
        return "Car{" +
                "Title='" + Title + '\'' +
                ", imgURL='" + imgURL + '\'' +
                ", semaphore=" + semaphore +
                '}';
    }
}

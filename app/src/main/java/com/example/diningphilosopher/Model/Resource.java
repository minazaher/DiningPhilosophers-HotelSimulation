package com.example.diningphilosopher.Model;

import static com.example.diningphilosopher.ApplicationClass.db;
import static com.example.diningphilosopher.CarAdapter.CAR_CURSOR;
import static com.example.diningphilosopher.RoomsAdapter.ROOM_CURSOR;

import com.google.firebase.firestore.FieldValue;

import java.util.concurrent.Semaphore;

public abstract class Resource {
    public Semaphore semaphore;;
    private int CategoryType;


    public Resource(Semaphore semaphore, int categoryType) {
        this.semaphore = semaphore;
        CategoryType = categoryType;
    }

    public static synchronized void reserve() throws InterruptedException {
        db.collection("Resources").document(String.valueOf(ROOM_CURSOR))
                .update("Semaphore", FieldValue.increment(-1));
        db.collection("Resources").document(String.valueOf(CAR_CURSOR))
                .update("Semaphore", FieldValue.increment(-1));
    }

    public static synchronized void leave() {
        db.collection("Resources").document(String.valueOf(ROOM_CURSOR))
                .update("Semaphore", FieldValue.increment(1));
        db.collection("Resources").document(String.valueOf(CAR_CURSOR))
                .update("Semaphore", FieldValue.increment(1));
    }

    public int getCategoryType() {
        return CategoryType;
    }

    public void setCategoryType(int categoryType) {
        CategoryType = categoryType;
    }

    @Override
    public String toString() {
        return "Resource{" +
                "semaphore=" + semaphore +
                ", CategoryType=" + CategoryType +
                '}';
    }
}

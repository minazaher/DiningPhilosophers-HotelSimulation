package com.example.diningphilosopher.Model;

import java.util.concurrent.Semaphore;

public abstract class Resource {
    public Semaphore semaphore;;
    private int CategoryType;


    public Resource(Semaphore semaphore, int categoryType) {
        this.semaphore = semaphore;
        CategoryType = categoryType;
    }

    public synchronized void reserve() throws InterruptedException {
        while (this.semaphore.availablePermits() == 0) {
            try {
                wait(); //The calling thread waits until semaphore becomes free
            } catch (InterruptedException e) {
            }
        }
        this.semaphore.acquire();
    }

    public synchronized void leave(int ID, boolean isRight, String c) {
        this.semaphore.release();
        notify();
        System.out.println("Customer " + ID + " leaves the resource: " + c);
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

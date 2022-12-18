package com.example.diningphilosopher.Model;


import static com.example.diningphilosopher.ApplicationClass.CAR_FLAG;
import static com.example.diningphilosopher.ApplicationClass.ROOM_FLAG;
import static com.example.diningphilosopher.RoomsAdapter.ROOM_CURSOR;
import static com.example.diningphilosopher.ApplicationClass.db;
import static com.example.diningphilosopher.CarAdapter.CAR_CURSOR;

import android.content.Context;
import android.widget.Toast;

import com.example.diningphilosopher.ApplicationClass;
import com.google.firebase.firestore.FieldValue;

public class Customer extends Thread {

    private int ID;
    private int type;
    private String Email;
    private String N;
    private String Password;
    private Resource[] resources;
    private int MatchingRoomType;
    private int MatchingCarType;
    public Context context;

    public Customer (){

    }

    public Customer(int id, String Email, String N, String Password, int type, Resource[] resources, int MatchingRoomType, int MatchingCarType) {
        this.ID = id;
        this.Email = Email;
        this.Password = Password;
        this.N = N;
        this.type = type;
        this.resources = resources;
        this.MatchingRoomType = MatchingRoomType;
        this.MatchingCarType= MatchingCarType;
    }

    public Customer(int id, String Email, String N, String Password, int type, Resource[] resources) {
        this.ID = id;
        this.Email = Email;
        this.Password = Password;
        this.N = N;
        this.type = type;
        this.resources = resources;
    }


    public Customer(int id, int type, Resource[] resources) {
        this.ID = id;
        this.type = type;
        this.resources = resources;
    }

    public String getN() {
        return N;
    }

    public void setN(String n) {
        N = n;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public int getMatchingRoomType() {
        return MatchingRoomType;
    }

    public void setMatchingRoomType(int matchingRoomType) {
        MatchingRoomType = matchingRoomType;
    }

    public int getMatchingCarType() {
        return MatchingCarType;
    }

    public void setMatchingCarType(int matchingCarType) {
        MatchingCarType = matchingCarType;
    }

    @Override
    public void run() {
        if (canReserve()){
            try {
                Resource.reserve();
                Toast.makeText(this.context, "Reserved!", Toast.LENGTH_SHORT).show();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Resource.leave();
            ROOM_FLAG = CAR_FLAG = false;
            ROOM_CURSOR = CAR_CURSOR = -1;
            Toast.makeText(this.context, "Trip Finished!", Toast.LENGTH_SHORT).show();
        }


    }


    public Resource getRight(int id) {
        return resources[id];
    }

    public Resource getLeft(int id) {
        return resources[(id + 1) % ApplicationClass.no_of_customers];
    }

    public boolean canReserve() {
        return (ROOM_FLAG && CAR_FLAG);
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Resource[] getResources() {
        return resources;
    }

    public void setResources(Resource[] resources) {
        this.resources = resources;
    }



}

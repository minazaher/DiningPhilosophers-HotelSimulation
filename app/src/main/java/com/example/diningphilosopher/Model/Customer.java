package com.example.diningphilosopher.Model;


import static com.example.diningphilosopher.RoomsAdapter.ROOM_CURSOR;
import static com.example.diningphilosopher.ApplicationClass.db;
import static com.example.diningphilosopher.CarAdapter.CAR_CURSOR;

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
            db.collection("Resources").document(String.valueOf(ROOM_CURSOR))
                    .update("Semaphore", FieldValue.increment(-1));
            db.collection("Resources").document(String.valueOf(CAR_CURSOR))
                    .update("Semaphore", FieldValue.increment(-1));
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            db.collection("Resources").document(String.valueOf(ROOM_CURSOR))
                    .update("Semaphore", FieldValue.increment(1));
            db.collection("Resources").document(String.valueOf(CAR_CURSOR))
                    .update("Semaphore", FieldValue.increment(1));

    }


    public Resource getRight(int id) {
        return resources[id];
    }

    public Resource getLeft(int id) {
        return resources[(id + 1) % ApplicationClass.no_of_customers];
    }

    public boolean canReserve() {
        return (this.getRight(ID).semaphore.availablePermits() == 1 && this.getLeft(ID).semaphore.availablePermits() == 1);
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



//        System.out.println("Customer " + ID + " entered the website");
//        try {
//            Thread.sleep(new Random().nextInt(100) + 50);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println("Customer " + ID + " wants to reserve.");
//        if (canReserve()) {
//            try {
//                resources[ID].reserve();
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//            System.out.println("Customer " + ID + "  reserved the : " + resources[ID].getClass().getSimpleName());
//            try {
//                resources[(ID + 1) % ApplicationClass.no_of_customers].reserve();
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//            System.out.println("Customer " + ID + "  reserved the resource : " + resources[(ID + 1) % ApplicationClass.no_of_customers].getClass().getSimpleName());
//            System.out.println("Customer " + ID + " reservation done");
//            try {
//                Thread.sleep(new Random().nextInt(100) + 50);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            resources[ID].leave(ID, true, resources[ID].getClass().getSimpleName());// release right chopstick
//            resources[(ID + 1) % ApplicationClass.no_of_customers].leave(ID, false, resources[(ID + 1) % ApplicationClass.no_of_customers].getClass().getSimpleName());//release left chopstick
//            try {
//                Thread.sleep(new Random().nextInt(100) + 50);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        } else
//            System.out.println("Resources for Customer " + ID + " are reserved");
//    }
}

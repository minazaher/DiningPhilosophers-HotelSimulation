package com.example.diningphilosopher;

import android.annotation.SuppressLint;
import android.app.Application;

import com.example.diningphilosopher.Activity.ReservationActivity;
import com.example.diningphilosopher.Model.Car;
import com.example.diningphilosopher.Model.Customer;
import com.example.diningphilosopher.Model.Resource;
import com.example.diningphilosopher.Model.Room;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ApplicationClass extends Application {

    public static int CUSTOMER_TYPE_FAMILY = 0;
    public static int CUSTOMER_TYPE_FRIENDS = 1;
    public static int CUSTOMER_TYPE_SINGLE = 2;
    public static int CUSTOMER_TYPE_COUPLE = 3;

    public static int RESOURCE_TYPE_BIG = 0;
    public static int RESOURCE_TYPE_SMALL = 1;

    public static boolean CAR_FLAG = false;
    public static boolean ROOM_FLAG = false;


    public static int no_of_customers = 4;
    public static ArrayList<Customer> customerArrayList = new ArrayList<>();
    public static Resource[] resourceArrayList = new Resource[4];
    public static Customer currentUser;
    public static Room[] ViewedRooms;
    public static Car[] ViewedCars;
    @SuppressLint("StaticFieldLeak")
    public static FirebaseFirestore db ;

    public static ArrayList<String> RoomIDs = new ArrayList<>();


    @Override
    public void onCreate() {
        ViewedRooms = new Room[4];
        ViewedCars = new Car[4];

        db = FirebaseFirestore.getInstance();

        super.onCreate();
        RoomIDs.add("0");
        RoomIDs.add("1");
        RoomIDs.add("2");
        RoomIDs.add("3");

        ReservationActivity.getData();
        ReservationActivity.getSemaphore(1);

        Customer customer = new Customer();
        customer.setResources(new Resource[5]);

        ApplicationClass.customerArrayList.add(new Customer(0, "family", "family", "family", CUSTOMER_TYPE_FAMILY, resourceArrayList,RESOURCE_TYPE_BIG, RESOURCE_TYPE_BIG));
        ApplicationClass.customerArrayList.add(new Customer(1, "friends", "friends", "friends", CUSTOMER_TYPE_FRIENDS, resourceArrayList,  RESOURCE_TYPE_BIG, RESOURCE_TYPE_SMALL));
        ApplicationClass.customerArrayList.add(new Customer(2, "single", "single", "single", CUSTOMER_TYPE_SINGLE, resourceArrayList, RESOURCE_TYPE_SMALL, RESOURCE_TYPE_SMALL));
        ApplicationClass.customerArrayList.add(new Customer(3, "couple", "couple", "couple", CUSTOMER_TYPE_COUPLE, resourceArrayList, RESOURCE_TYPE_SMALL, RESOURCE_TYPE_BIG));
    }
}


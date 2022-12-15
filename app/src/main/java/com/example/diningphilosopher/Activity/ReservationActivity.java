package com.example.diningphilosopher.Activity;


import static com.example.diningphilosopher.Activity.RoomsAdapter.ROOM_CURSOR;
import static com.example.diningphilosopher.ApplicationClass.CAR_FLAG;
import static com.example.diningphilosopher.ApplicationClass.ROOM_FLAG;
import static com.example.diningphilosopher.ApplicationClass.RoomIDs;
import static com.example.diningphilosopher.ApplicationClass.ViewedCars;
import static com.example.diningphilosopher.ApplicationClass.ViewedRooms;
import static com.example.diningphilosopher.ApplicationClass.currentUser;
import static com.example.diningphilosopher.ApplicationClass.db;
import static com.example.diningphilosopher.ApplicationClass.resourceArrayList;
import static com.example.diningphilosopher.CarAdapter.CAR_CURSOR;
import static java.lang.Integer.parseInt;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diningphilosopher.CarAdapter;
import com.example.diningphilosopher.Model.Car;
import com.example.diningphilosopher.Model.Room;
import com.example.diningphilosopher.R;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class ReservationActivity extends AppCompatActivity {
    LinearLayout btn_profile;
    ConstraintLayout constraintLayout_start;
    TextView tv_Hi, tv_categories, tv_popular;
    RecyclerView Cars, Rooms;
    EditText et_search;
    ImageView delete, user_img;

    @SuppressLint("SuspiciousIndentation")
    public static void getRooms() {
        for (int i = 0; i < 4; i++) {
            if (resourceArrayList[i].getClass().getSimpleName().equals("Room")) {
                if (currentUser.getMatchingRoomType() == resourceArrayList[i].getCategoryType()) {
                    System.out.println("Room Category Type :" + resourceArrayList[i].getCategoryType());
                    System.out.println("Simple Name : " + resourceArrayList[i].getClass().getSimpleName());
                    System.out.println("Rooms : " + resourceArrayList[i].toString());
                    System.out.println("****************************************");
                    if (RoomsAdapter.rooms.size() == 0)
                        RoomsAdapter.rooms.add((Room) resourceArrayList[i]);
                    ViewedRooms[i] = (Room) resourceArrayList[i];
                }
            }
        }
    }

    public static void getCars() {
        for (int i = 0; i < 4; i++) {
            if (resourceArrayList[i].getClass().getSimpleName().toString().equals("Car")) {
                if (currentUser.getMatchingCarType() == resourceArrayList[i].getCategoryType()) {
                    System.out.println("Car Category Type :" + resourceArrayList[i].getCategoryType());
                    System.out.println("Simple Name " + resourceArrayList[i].getClass().getSimpleName());
                    System.out.println("Cars : " + resourceArrayList[i].toString());
                    System.out.println("****************************************");
                    if (CarAdapter.cars.size() == 0)
                        CarAdapter.cars.add((Car) resourceArrayList[i]);
                    ViewedCars[i] = (Car) resourceArrayList[i];
                }
            }
        }
    }

    public static AtomicInteger getSemaphore(int id) {
        AtomicInteger count = new AtomicInteger();
        DocumentReference docRef = db.collection("Resources").document(String.valueOf(id));
        Task<DocumentSnapshot> documentSnapshotTask = docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    count.set(parseInt(document.get("Semaphore").toString()));
                    System.out.println("******* COUNT FROM FIRESTORE : " + count);
                }

            }
        });
        return count;
    }

    public static void getData() {
        for (String s : RoomIDs) {
            DocumentReference docRef = db.collection("Resources").document(s);
            Task<DocumentSnapshot> documentSnapshotTask = docRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        if (parseInt(document.getId()) % 2 == 0)
                            resourceArrayList[parseInt(document.getId())] =
                                    new Room(new Semaphore(parseInt(document.get("Semaphore").toString())),
                                            parseInt(document.get("Type").toString())
                                            , document.get("Title").toString()
                                            , document.get("ImgURL").toString());
                        else
                            resourceArrayList[parseInt(document.getId())] =
                                    new Car(parseInt(document.get("Semaphore").toString()),
                                            parseInt(document.get("Type").toString()),
                                            document.get("Title").toString()
                                            , document.get("ImgURL").toString());
                    }
                }
            });
        }

    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        initializeUI();
        tv_Hi.setText("Hi " + currentUser.getN());

        constraintLayout_start.setOnClickListener(view ->
        {
            if (ROOM_FLAG && CAR_FLAG){
                currentUser.start();
                Toast.makeText(this, "لولولولولي", Toast.LENGTH_SHORT).show();
                try {
                    currentUser.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ROOM_FLAG = CAR_FLAG = false;
                ROOM_CURSOR = CAR_CURSOR = -1;

                Toast.makeText(this, "فداهية!", Toast.LENGTH_SHORT).show();
            }



        });

        recyclerViewCars();
        recyclerViewRooms();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RoomsAdapter.rooms.clear();
        CarAdapter.cars.clear();
    }

    @Override
    protected void onStop() {
        super.onStop();
        RoomsAdapter.rooms.clear();
        CarAdapter.cars.clear();
    }

    @Override
    protected void onPause() {
        super.onPause();
        RoomsAdapter.rooms.clear();
        CarAdapter.cars.clear();

    }

    private void initializeUI() {
        tv_Hi = findViewById(R.id.tv_Hi);
        tv_categories = findViewById(R.id.tv_categories);
        tv_popular = findViewById(R.id.tv_popular);
        constraintLayout_start = findViewById(R.id.constraintLayout_reserve);
        et_search = findViewById(R.id.et_search);
    }

    private void recyclerViewRooms() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        Rooms = findViewById(R.id.recyclerView);
        Rooms.setLayoutManager(linearLayoutManager);
        ReservationActivity.getRooms();
        RoomsAdapter roomsAdapter = new RoomsAdapter(this);
        Rooms.setAdapter(roomsAdapter);
    }

    private void recyclerViewCars() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        Cars = findViewById(R.id.pop_recview);
        Cars.setLayoutManager(linearLayoutManager);
        ReservationActivity.getCars();
        CarAdapter Car = new CarAdapter(this);
        Cars.setAdapter(Car);
    }
}


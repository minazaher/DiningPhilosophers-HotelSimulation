package com.example.diningphilosopher;

import static com.example.diningphilosopher.ApplicationClass.CAR_FLAG;
import static com.example.diningphilosopher.ApplicationClass.currentUser;
import static com.example.diningphilosopher.ApplicationClass.db;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.diningphilosopher.Model.Car;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;

import java.util.ArrayList;


public class CarAdapter extends RecyclerView.Adapter<CarAdapter.viewholder> {
    public static ArrayList<Car> cars = new ArrayList<>();
    public static int CAR_CURSOR;
    Context context;

    public CarAdapter() {
    }

    public CarAdapter(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.car_item, parent, false);
        return new viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, @SuppressLint("RecyclerView") int position) {
        holder.car_type.setText(cars.get(position).getTitle());
        Glide.with(this.context).asBitmap().load(cars.get(position).getImgURL().toString()).into(holder.car_img);
        final int[] count = new int[1];
        holder.car_acquire.setOnClickListener(view -> {
            for (int i = 0; i <4 ; i++) {
                if (cars.get(position).equals(ApplicationClass.resourceArrayList[i])){
                    DocumentReference dc = db.collection("Resources").document(String.valueOf(i));
                    int finalI = i;
                    dc.get().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document != null) {
                                count[0] = Integer.parseInt(document.get("Semaphore").toString());
                                if (count[0] > 0) {
                                    CAR_FLAG = true;
                                    CAR_CURSOR = finalI;
                                    System.out.println(currentUser.getName() + "Clicked ");
                                }
                                else if (count[0] == 0)
                                    Toast.makeText(context, "Not Available!", Toast.LENGTH_SHORT).show();
                            }
                        }
                });
                    System.out.println("Cars.Get() Passed");

                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return cars.size();
    }

    protected static class viewholder extends RecyclerView.ViewHolder {
        TextView car_type;
        ImageView car_img;
        Button car_acquire;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            car_img = itemView.findViewById(R.id.car_img);
            car_type = itemView.findViewById(R.id.car_type);
            car_acquire = itemView.findViewById(R.id.btn_car_acquire);
        }
    }

}

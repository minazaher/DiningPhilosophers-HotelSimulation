package com.example.diningphilosopher.Activity;

import static com.example.diningphilosopher.ApplicationClass.ROOM_FLAG;
import static com.example.diningphilosopher.ApplicationClass.currentUser;
import static com.example.diningphilosopher.ApplicationClass.db;

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
import com.example.diningphilosopher.ApplicationClass;
import com.example.diningphilosopher.Model.Room;
import com.example.diningphilosopher.R;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;

import java.util.ArrayList;


public class RoomsAdapter extends RecyclerView.Adapter<RoomsAdapter.viewholder> {
    public static ArrayList<Room> rooms = new ArrayList<>();
    public static int ROOM_CURSOR;
    Context context;

    public RoomsAdapter(Context context) {
        this.context = context;
    }

    public RoomsAdapter() {

    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RoomsAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.room_item, parent, false);
        return new RoomsAdapter.viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomsAdapter.viewholder holder, int position) {
        holder.room_tv.setText(rooms.get(position).getTitle());
        Glide.with(this.context).asBitmap().load(rooms.get(position).getImgURL().toString()).into(holder.room_img);
        final int[] count = new int[1];
        holder.room_acquire.setOnClickListener(view -> {
            for (int i = 0; i <4 ; i++) {
                if (rooms.get(position).equals(ApplicationClass.resourceArrayList[i])){
                    DocumentReference dc = db.collection("Resources").document(String.valueOf(i));
                    dc.get().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document != null) {
                                count[0] = Integer.parseInt(document.get("Semaphore").toString());
                            }
                        }
                    });
                    System.out.println("Rooms.Get() Passed");
                    if (count[0] > 0){
                        ROOM_FLAG = true;
                        ROOM_CURSOR = i;
//                        db.collection("Resources").document(String.valueOf(i))
//                                .update("Semaphore", FieldValue.increment(-1));
                        System.out.println(currentUser.getName() + "Clicked ");
                    }
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return rooms.size();
    }

    protected static class viewholder extends RecyclerView.ViewHolder {
        TextView room_tv;
        ImageView room_img;
        Button room_acquire;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            room_img = itemView.findViewById(R.id.room_img);
            room_tv = itemView.findViewById(R.id.room_tv);
            room_acquire = itemView.findViewById(R.id.btn_room_acquire);
        }
    }

}

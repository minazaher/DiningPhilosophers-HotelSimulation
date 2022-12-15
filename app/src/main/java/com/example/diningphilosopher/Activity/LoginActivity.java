package com.example.diningphilosopher.Activity;

import static com.example.diningphilosopher.ApplicationClass.resourceArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.diningphilosopher.ApplicationClass;
import com.example.diningphilosopher.Model.Customer;
import com.example.diningphilosopher.Model.Resource;
import com.example.diningphilosopher.R;

public class LoginActivity extends AppCompatActivity {
    TextView tv_signIn;
    EditText et_email, et_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tv_signIn = findViewById(R.id.tv_signIn);
        et_email = findViewById(R.id.et_emailLogin);
        et_password = findViewById(R.id.et_passwordLogin);

        tv_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email = et_email.getText().toString();
                String Password = et_password.getText().toString();
                if (Email.isEmpty() || Password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please Fill Out All Fields!", Toast.LENGTH_SHORT).show();
                } else {
                    for (Customer c : ApplicationClass.customerArrayList)  {
                        if (Email.equals(c.getEmail()) && Password.equals(c.getPassword())) {
                            Intent intent = new Intent(LoginActivity.this, ReservationActivity.class);
                            ApplicationClass.currentUser = c;
                            startActivity(intent);
                            break;
                        }
                    }
                }

            }
        });
    }
}
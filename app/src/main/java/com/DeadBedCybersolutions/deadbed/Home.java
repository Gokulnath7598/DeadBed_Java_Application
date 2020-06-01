package com.DeadBedCybersolutions.deadbed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Home extends AppCompatActivity {
    private Button Courses,Aboutus,Logout;
    private FirebaseAuth Firebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Courses=findViewById(R.id.Courses);
        Aboutus=findViewById(R.id.Aboutus);
        Logout=findViewById(R.id.Logout);
        Firebase = FirebaseAuth.getInstance();


        Aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Home.this, com.DeadBedCybersolutions.deadbed.Aboutus.class);
                startActivity(i);
            }
        });

        Courses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Home.this, com.DeadBedCybersolutions.deadbed.Courses.class);
                startActivity(i);
            }
        });

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Home.this,"Logged Out",Toast.LENGTH_SHORT).show();
                Firebase.signOut();
                Intent i=new Intent(Home.this, SignIn.class);
                startActivity(i);
                finish();
            }
        });
    }
}

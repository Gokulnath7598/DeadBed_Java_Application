package com.DeadBedCybersolutions.deadbed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Forgotpassword extends AppCompatActivity {
    private EditText Email;
    private Button Button;
    FirebaseAuth Firebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);
        Email=findViewById(R.id.Email);
        Button=findViewById(R.id.button);
        Firebase=FirebaseAuth.getInstance();

        Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=Email.getText().toString();
                if (TextUtils.isEmpty(email))
                {
                    Toast.makeText(Forgotpassword.this,"Enter Your Registered Email",Toast.LENGTH_SHORT).show();
                }
                else                {
                    Firebase.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful())
                            {
                                Toast.makeText(Forgotpassword.this,"Email sent Successfully!",Toast.LENGTH_SHORT).show();
                                Intent i=new Intent(Forgotpassword.this,SignIn.class);
                                startActivity(i);
                            }
                            else {
                                String message=task.getException().getMessage();
                                Toast.makeText(Forgotpassword.this,"Error Occured!"+message,Toast.LENGTH_SHORT).show();
                            }
                            }
                    });
                }

            }
        });
    }
}

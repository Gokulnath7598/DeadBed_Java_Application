package com.DeadBedCybersolutions.deadbed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class SignIn extends AppCompatActivity {
    private EditText Password;
    private TextView Toggle;
    private TextView Signup;
    private EditText Phone;
    private Button Signin;
    private TextView Forgotpassword;
    FirebaseAuth Firebase;
    //private FirebaseAuth.AuthStateListener Listener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        Firebase=FirebaseAuth.getInstance();
        Password=findViewById(R.id.Password);
        Toggle=findViewById(R.id.Toggle);
        Signup=findViewById(R.id.Signup);
        Phone=findViewById(R.id.Phone);
        Signin=findViewById(R.id.Signin);
        Forgotpassword=findViewById(R.id.Forgotpassword);


      /*if (Firebase.getCurrentUser() != null)
        {
            Toast.makeText(SignIn.this,"You are Logged in",Toast.LENGTH_SHORT).show();
            Intent i = new Intent(SignIn.this, Home.class);
            startActivity(i);
            finish();
        }*/

      //For Forgot Password Password
        Forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent F=new Intent(SignIn.this, com.DeadBedCybersolutions.deadbed.Forgotpassword.class);
                startActivity(F);
            }
        });



        //For Password Toggle

        Toggle.setVisibility(View.GONE);
        Password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD |InputType.TYPE_CLASS_TEXT);
        Password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (Password.getText().length()>0)
                {
                    Toggle.setVisibility(View.VISIBLE);
                }
                else
                {
                    Toggle.setVisibility(View.GONE);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        Toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Toggle.getText() == "SHOW")
                {
                    Toggle.setText("HIDE");
                    Password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    Password.setSelection(Password.length());
                }
                else
                {
                    Toggle.setText("SHOW");
                    Password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD |InputType.TYPE_CLASS_TEXT);
                    Password.setSelection(Password.length());
                }
            }
        });

        //for Redirecting to signup page

        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent r=new Intent(SignIn.this,MainActivity.class);
                startActivity(r);
            }
        });

        //for user exist
        //Firebase.addAuthStateListener(Listener);
       /*Listener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user=Firebase.getCurrentUser();
                if (user != null)
                {
                    Toast.makeText(SignIn.this,"You are Logged in",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(SignIn.this, Home.class);
                    startActivity(i);
                }
                else
                {
                    Toast.makeText(SignIn.this,"Please Log in",Toast.LENGTH_SHORT).show();
                }
            }
        };*/

        //for Validating Sign up details
        Firebase=FirebaseAuth.getInstance();
        Signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(Phone.getText().toString())) {
                    Toast.makeText(SignIn.this, "Please Enter Your Name!", Toast.LENGTH_SHORT).show();
                } else {
                    if (TextUtils.isEmpty(Password.getText().toString())) {
                        Toast.makeText(SignIn.this, "Please Enter Your Password!", Toast.LENGTH_SHORT).show();
                    }else {
                            String phone=Phone.getText().toString();
                            String passwordToHash = Password.getText().toString();



                           Firebase.signInWithEmailAndPassword(phone, passwordToHash).addOnCompleteListener(SignIn.this, new OnCompleteListener<AuthResult>() {
                               @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                                   if (!task.isSuccessful())
                                   {
                                       Toast.makeText(SignIn.this, "Log in Error!!", Toast.LENGTH_SHORT).show();
                                   }
                                   else {
                                             Toast.makeText(SignIn.this, "Log in Successful!!", Toast.LENGTH_SHORT).show();
                                            Intent g = new Intent(SignIn.this, Home.class);
                                             startActivity(g);
                                             finish();
                                            }
                                         }

                                     });

                        }
                }
            }
       });
    }


}

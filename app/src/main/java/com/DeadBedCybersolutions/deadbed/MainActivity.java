package com.DeadBedCybersolutions.deadbed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {
    private EditText Password;
    private TextView Toggle;
    private TextView Signin;
    private Button Signup;
    private EditText Name;
    private EditText Email;
    private EditText Phone;
    FirebaseAuth Firebaseauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Firebaseauth=FirebaseAuth.getInstance();
        Password=findViewById(R.id.Password);
        Toggle=findViewById(R.id.Toggle);
        Signin=findViewById(R.id.Signup);
        Signup=findViewById(R.id.Signin);
        Name=findViewById(R.id.Name);
        Email=findViewById(R.id.Email);
        Phone=findViewById(R.id.Phone);


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

        //for Redirecting to signin page

        Signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Signinintent=new Intent(MainActivity.this,SignIn.class);
                startActivity(Signinintent);
            }
        });

        //for Validating Sign up details

        Signup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(Name.getText().toString()))
                {
                    Toast.makeText(MainActivity.this,"Please Enter Your Name!",Toast.LENGTH_SHORT).show();
                }
                else {
                    if (TextUtils.isEmpty(Email.getText().toString()))
                    {
                        Toast.makeText(MainActivity.this,"Please Enter Your Email Address!",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        String emailPattern1 = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                        String email=Email.getText().toString().trim();
                        if (!email.matches(emailPattern1))
                        {
                            Toast.makeText(MainActivity.this,"Please Enter a Valid Email Address!",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            if (TextUtils.isEmpty(Phone.getText().toString()))
                            {
                                Toast.makeText(MainActivity.this,"Please Enter Your Mobile Number!",Toast.LENGTH_SHORT).show();
                            }
                            else
                                {
                                    String phonepattern = "^\\s*(?:\\+?(\\d{1,3}))?[-. (]*(\\d{3})[-. )]*(\\d{3})[-. ]*(\\d{4})(?: *x(\\d+))?\\s*$";
                                    String phone=Phone.getText().toString().trim();
                                    if (!phone.matches(phonepattern))
                                    {

                                        Toast.makeText(MainActivity.this,"Please Enter a Valid Phone Number!",Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                        {
                                            if (TextUtils.isEmpty(Password.getText().toString()))
                                            {
                                                Toast.makeText(MainActivity.this,"Please Enter a Password!",Toast.LENGTH_SHORT).show();
                                            }
                                            else {
                                                String passwordpattern = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
                                                String password=Password.getText().toString().trim();
                                                if (!password.matches(passwordpattern))
                                                {

                                                    Toast.makeText(MainActivity.this,"Please Enter a Valid Password!",Toast.LENGTH_SHORT).show();
                                                }
                                                else
                                                    {
                                                        String saveEmail=Email.getText().toString();
                                                        String passwordToHash = Password.getText().toString();
                                                        String generatedPassword = null;
                                                        try {
                                                            // Create MessageDigest instance for MD5
                                                            MessageDigest md = MessageDigest.getInstance("MD5");
                                                            //Add password bytes to digest
                                                            md.update(passwordToHash.getBytes());
                                                            //Get the hash's bytes
                                                            byte[] bytes = md.digest();
                                                            //This bytes[] has bytes in decimal format;
                                                            //Convert it to hexadecimal format
                                                            StringBuilder sb = new StringBuilder();
                                                            for(int i=0; i< bytes.length ;i++)
                                                            {
                                                                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
                                                            }
                                                            //Get complete hashed password in hex format
                                                            generatedPassword = sb.toString();
                                                        }
                                                        catch (NoSuchAlgorithmException e)
                                                        {
                                                            e.printStackTrace();
                                                        }
                                                        String savePassword=generatedPassword;
                                                        Firebaseauth.createUserWithEmailAndPassword(saveEmail,passwordToHash).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                                if (!task.isSuccessful())
                                                                {
                                                                    Toast.makeText(MainActivity.this,"Error occured! Please Try again",Toast.LENGTH_SHORT).show();
                                                                }
                                                                else
                                                                {
                                                                    Toast.makeText(MainActivity.this,"Sign up successful! Log in now",Toast.LENGTH_SHORT).show();
                                                                    Intent Signinintent = new Intent(MainActivity.this, SignIn.class);
                                                                    startActivity(Signinintent);
                                                                }

                                                            }
                                                        });

                                                }
                                            }
                                       }
                                 }
                        }
                    }
                }
            }
        });
    }
}

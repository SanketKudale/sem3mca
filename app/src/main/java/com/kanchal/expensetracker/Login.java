package com.kanchal.expensetracker;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import android.util.Log;

public class Login extends AppCompatActivity {

    TextInputLayout email,password;
    Button btn,login;
    FirebaseAuth FAuth;
    Utils utils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        utils = new Utils();
        btn =(Button) findViewById(R.id.register1);

        email = (TextInputLayout) findViewById(R.id.email);
        password = (TextInputLayout) findViewById(R.id.password);
        login = findViewById(R.id.login);

        FirebaseApp.initializeApp(this);

        FAuth=FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String tex_email = email.getEditText().getText().toString();
                String tex_password = password.getEditText().getText().toString();


                if (TextUtils.isEmpty(tex_email) || TextUtils.isEmpty(tex_password)){
                    Toast.makeText(Login.this, "All Fields Required", Toast.LENGTH_SHORT).show();
                }

                else if (!utils.isValidPassword(tex_password))
                {
                    Toast.makeText(Login.this,"Enter Valid Password 1 digit, 1 small & 1upper case letter, a special char and length > 6", Toast.LENGTH_SHORT).show();

                }
                else {


                    FAuth.signInWithEmailAndPassword(tex_email, tex_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Login successful!", Toast.LENGTH_LONG).show();


                                Intent intent = new Intent(Login.this, MainActivity.class);
                                startActivity(intent);

                                SharedPreferences sp1 = getSharedPreferences("Login_Flag", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sp1.edit();
                                editor.putString("loginSet", "1log");
                                String[] a1 = tex_email.split("@");
                                String[] a2 = a1[1].split("\\.");
                                String id = a1[0] + "" + a2[0] + "" + a2[1];
                                editor.putString("userId", id);
                                editor.apply();
                            } else {
                                // Toast.makeText(getApplicationContext(), "Login failed! Please try again later", Toast.LENGTH_LONG).show();
                                Toast.makeText(Login.this, "MISTAKE", Toast.LENGTH_SHORT).show();
                                Log.d("z", "onComplete: Failed=" + task.getException().getMessage());

                            }

                        }
                    });
                }


            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openregister();
            }


        });
    }

    private void openregister() {
        Intent intent=new Intent( Login.this, Register.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        finish();
        finishAffinity();
    }

}
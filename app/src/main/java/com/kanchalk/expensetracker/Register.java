package com.kanchalk.expensetracker;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    TextInputLayout mFullName,mEmail,mPassword,mPhone;
    Button mRegisterbtn;
    FirebaseAuth FAuth;
    Utils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        utils = new Utils();

        mFullName = (TextInputLayout) findViewById(R.id.fullname);
        mPhone=(TextInputLayout) findViewById(R.id.phone);
        mEmail = (TextInputLayout) findViewById(R.id.email);
        mPassword = (TextInputLayout) findViewById(R.id.password);

        mRegisterbtn=(Button) findViewById(R.id.register);

        FAuth=FirebaseAuth.getInstance();


        mRegisterbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String Name = mFullName.getEditText().getText().toString();
                final String Email = mEmail.getEditText().getText().toString();
                final String Password = mPassword.getEditText().getText().toString();
                final String Phone = mPhone.getEditText().getText().toString();

                if(TextUtils.isEmpty(Email))
                {
                    mEmail.setError("Email is Required");
                    return;

                }
                if(TextUtils.isEmpty(Password))
                {
                    mPassword.setError("Password is Required");
                    return;
                }
                if(!utils.isValidPassword(Password))
                {
                    mPassword.setError("Enter Valid Password 1 digit, 1 small & 1upper case letter, a special char and length > 6");
                    return;
                }

                FAuth.createUserWithEmailAndPassword(Email, Password)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Registration successful!", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(Register.this, Login.class);
                                startActivity(intent);
                            }
                            else {
                                Utils utils = new Utils();
                                utils.appLog("Exception -> "+task.getException()+"\n Result -> "+task.getResult());
                                Toast.makeText(getApplicationContext(), "Registration failed! Please try again later", Toast.LENGTH_LONG).show();
                            }
                        });





            }




        });

    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(Register.this,Login.class));
    }

}
package com.kanchal.expensetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SavingsActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener{
    EditText sdate,sdescription,samount;
    String date,description,amount,type;
    SavingsDataBaseHelper dbSavingsHelper;
    int mYear, mMonth, mDay;
    Spinner savingsType;
    Button addSavings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savings);
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH)+1;
        mDay = c.get(Calendar.DAY_OF_MONTH);

        addSavings = findViewById(R.id.saveSavings);
        addSavings.setOnClickListener(this);
        sdate = findViewById(R.id.savingsDate);
        sdescription = findViewById(R.id.savingsDecription);
        samount = findViewById(R.id.savingsAmount);
        savingsType = findViewById(R.id.spinner1);

        savingsType.setOnItemSelectedListener(this);
        List<String> categories = new ArrayList<String>();
        categories.add("Select Option");
        categories.add("Anniversary");
        categories.add("Birthday");
        categories.add("Festival");
        categories.add("Donation");
        categories.add("Education Fees");
        categories.add("Other");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        savingsType.setAdapter(dataAdapter);

        sdate.setText(mDay+"-"+mMonth+"-"+mYear);
        sdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar_mode(mYear, mMonth-1, mDay);
            }
        });
    }



    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        type = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), "Selected: " + type, Toast.LENGTH_LONG).show();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }


    private void calendar_mode(int mYear, int mMonth, int mDay) {

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        sdate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.saveSavings:

                if( TextUtils.isEmpty(samount.getText())){
                    samount.setError( "Enter Amount" );
                }

                if( TextUtils.isEmpty(sdescription.getText())){
                    String ss="NONE";
                    sdescription.setText(ss);
                }

                if (savingsType.getSelectedItem().toString().equals("Select Option")){
                    Toast.makeText(this, "Specify Savings Type", Toast.LENGTH_SHORT).show();
                }
                description = "none";
                amount = samount.getText().toString();
                description = sdescription.getText().toString();
                date = sdate.getText().toString();
                Log.d("Savings",""+amount+amount+type+description+"$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
                if((samount.getText().toString().isEmpty() )|| (sdescription.getText().toString().isEmpty()))
                {
                    Toast.makeText(SavingsActivity.this, "Enter all details", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    try{
                        Savings savings = new Savings(amount,description,type,date);
                        SharedPreferences sp1 = getSharedPreferences("Login_Flag", Context.MODE_PRIVATE);
                        String id = sp1.getString("userId","0");

                        FirebaseDatabase fd1 = FirebaseDatabase.getInstance();
                        DatabaseReference dr1 = fd1.getReference("Users");

                        SimpleDateFormat s = new SimpleDateFormat("ddMMyyyyhhmmss");
                        String tstamp = s.format(new Date());

                        dr1.child(id).child("Savings").child("Savings_"+tstamp).setValue(savings);


                    }
                    catch(Exception e){
                        SharedPreferences sp2 = getSharedPreferences("Update_Flag", Context.MODE_PRIVATE);
                        String count = String.valueOf(Integer.parseInt(sp2.getString("Savings","0")) + 1);
                        SharedPreferences.Editor editor1 = sp2.edit();
                        editor1.putString("Savings",count);
                    }

                    try {
                    dbSavingsHelper = new SavingsDataBaseHelper(getApplicationContext());
                    Log.d("Savings",""+amount+amount+type+description+"$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
                    Savings savings = new Savings(amount,description,type,date);
                    Log.d("Savings",savings.getSavingsFor());
                    dbSavingsHelper.addSavings(savings);
                    Toast.makeText(SavingsActivity.this, "Inserted Successfully", Toast.LENGTH_SHORT).show();
                    sdate.setText(mDay+"-"+mMonth+"-"+mYear);
                    sdescription.setText("");
                    samount.setText("");


                }
                catch (Exception e)
                {   Log.d("exception",e.getMessage());
                    Toast.makeText(SavingsActivity.this, "Exception"+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                }
                break;
        }


    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(SavingsActivity.this,MainActivity.class));
    }

}
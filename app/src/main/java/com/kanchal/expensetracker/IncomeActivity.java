package com.kanchal.expensetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;


import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class IncomeActivity extends AppCompatActivity implements View.OnClickListener {
    EditText incomeAmount, fromIncome, descriptionIncome,incomeDate;
    RadioGroup incomeSource, incomeType;
    RadioButton business, salary, loan, cash, netBanking, debitCard, creditCard;
    Button saveIncome;
    IncomeDataBaseHelper dbHelper;
    int mYear, mMonth, mDay;
    String amt,frm = "none",dte,desc="none",srcType,src;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income);

        final Calendar c = Calendar.getInstance();

        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH)+1;
        mDay = c.get(Calendar.DAY_OF_MONTH);
        incomeAmount = findViewById(R.id.IncomeAmount);
        incomeSource = findViewById(R.id.IncomeSourceGroup);
        incomeType = findViewById(R.id.IncomeTypeGroup);
        business = findViewById(R.id.RadioBusiness);
        salary = findViewById(R.id.RadioSalary);
        loan = findViewById(R.id.RadioLoan);
        netBanking = findViewById(R.id.RadioNetBanking);
        cash = findViewById(R.id.RadioCash);
        debitCard = findViewById(R.id.RadioDebitCard);
        creditCard = findViewById(R.id.RadioCreditCard);
        saveIncome = findViewById(R.id.IncomeSaveButton);
        incomeDate = findViewById(R.id.IncomeDate);
        fromIncome = findViewById(R.id.IncomeFrom);
        descriptionIncome = findViewById(R.id.IncomeDescription);



        if( TextUtils.isEmpty(incomeAmount.getText())){
            incomeAmount.setError( "Enter Amount" );
        }

        business.setOnClickListener(this);
        salary.setOnClickListener(this);
        loan.setOnClickListener(this);
        creditCard.setOnClickListener(this);
        netBanking.setOnClickListener(this);
        debitCard.setOnClickListener(this);
        cash.setOnClickListener(this);

        incomeDate.setText(mDay+"-"+mMonth+"-"+mYear);
        incomeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar_mode(mYear, mMonth-1, mDay);
            }
        });

        saveIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                amt = incomeAmount.getText().toString();
                frm = fromIncome.getText().toString();
                dte = incomeDate.getText().toString();
                desc = descriptionIncome.getText().toString();



                if( TextUtils.isEmpty(incomeAmount.getText())){
                    incomeAmount.setError( "Enter Amount" );
                }

                if (incomeSource.getCheckedRadioButtonId() == -1){
                    Toast.makeText(IncomeActivity.this, "Please specify income source", Toast.LENGTH_SHORT).show();
                }

                if (incomeType.getCheckedRadioButtonId() == -1){
                    Toast.makeText(IncomeActivity.this, "Please specify income type", Toast.LENGTH_SHORT).show();
                }

                if( TextUtils.isEmpty(fromIncome.getText())){
                    String ss="NONE";
                    fromIncome.setText(ss);
                }

                if( TextUtils.isEmpty(descriptionIncome.getText())){
                    String ss="NONE";
                    descriptionIncome.setText(ss);
                }

                Log.d("amt",""+amt+frm+dte+desc+"$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
                if((incomeAmount.getText().toString().isEmpty() )|| (src.toString().isEmpty()) || (srcType.isEmpty()))
                {
                    Toast.makeText(IncomeActivity.this, "Enter all details", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    try{
                        Income income = new Income(amt,dte,frm,desc,src,srcType);
                        SharedPreferences sp1 = getSharedPreferences("Login_Flag", Context.MODE_PRIVATE);
                        String id = sp1.getString("userId","0");

                        FirebaseDatabase fd1 = FirebaseDatabase.getInstance();
                        DatabaseReference dr1 = fd1.getReference("Users");

                        SimpleDateFormat s = new SimpleDateFormat("ddMMyyyyhhmmss");
                        String tstamp = s.format(new Date());

                        dr1.child(id).child("Income").child("Income_"+tstamp).setValue(income);

                    }
                    catch(Exception e){
                        SharedPreferences sp2 = getSharedPreferences("Update_Flag", Context.MODE_PRIVATE);
                        String count = String.valueOf(Integer.parseInt(sp2.getString("Income","0")) + 1);
                        SharedPreferences.Editor editor1 = sp2.edit();
                        editor1.putString("Income",count);
                    }

                    try {
                    dbHelper = new IncomeDataBaseHelper(getApplicationContext());
                    Log.d("amt",""+amt+frm+dte+desc+src+srcType+"$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
                    Income income = new Income(amt,dte,frm,desc,src,srcType);
                    Log.d("income",income.getAmount());
                    dbHelper.addIncome(income);
                    Toast.makeText(IncomeActivity.this, "Inserted Successfully", Toast.LENGTH_SHORT).show();
                    fromIncome.setText("");
                    descriptionIncome.setText("");
                    incomeDate.setText("");
                    incomeAmount.setText("");
                    incomeSource.clearCheck();
                    incomeType.clearCheck();

                }
                catch (Exception e)
                {   Log.d("exception",e.getMessage());
                    Toast.makeText(IncomeActivity.this, "Exception"+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                }
            }
        });


    }
    private void calendar_mode(int mYear, int mMonth, int mDay) {



        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        incomeDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.RadioCash:
                srcType = "Cash";
                Log.d("srcTpe","cash");
                break;
            case R.id.RadioNetBanking:
                srcType = "Net Banking";
                break;
            case R.id.RadioCreditCard:
                srcType = "Credit Card";
                break;
            case R.id.RadioDebitCard:
                srcType = "Debit Card";
                break;
            case R.id.RadioLoan:
                src = "loan";
                Log.d("src","loan");
                break;
            case R.id.RadioSalary:
                src = "Salary";
                break;
            case R.id.RadioBusiness:
                src = "Business";
                break;


        }
    }


    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(IncomeActivity.this, MainActivity.class));
    }

}
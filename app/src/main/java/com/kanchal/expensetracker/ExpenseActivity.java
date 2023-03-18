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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ExpenseActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    EditText expenseamt, expensedescription, expensedate;
    String expense_mode = "cash",expensedesc = "none",expenseammt,expensetype,expensedt;
    Button saveExpense;
    ExpenseDataBaseHelper dbExpenseHelper;
    int mYear, mMonth, mDay;
    Spinner expenseType;
    RadioGroup Radioexpensemode;
    RadioButton cash,netbanking,debitCard,creditCard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH)+1;
        mDay = c.get(Calendar.DAY_OF_MONTH);
        Radioexpensemode =  findViewById(R.id.expenseTypeGroup);
        expensedescription = findViewById(R.id.expenseDescription);
        cash = findViewById(R.id.RadioCashExpense);
        netbanking =  findViewById(R.id.RadioNetBankingExpense);
        debitCard  =  findViewById(R.id.RadioDebitCardExpense);
        creditCard =  findViewById(R.id.RadioCreditCardExpense);
        expenseamt = findViewById(R.id.expenseAmount);
        expensedate = findViewById(R.id.ExpenseDate);
        expenseType = (Spinner) findViewById(R.id.expenseType);

        
        expenseType.setOnItemSelectedListener(this);
        List<String> categories = new ArrayList<String>();
        categories.add("Select Option");
        categories.add("Food");
        categories.add("Education");
        categories.add("Entertainment");
        categories.add("Loan");
        categories.add("Travelling");
        categories.add("Healthcare");
        categories.add("Housing");
        categories.add("Bills");
        categories.add("Others");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        expenseType.setAdapter(dataAdapter);
        expensedesc = expensedescription.getText().toString();
        expenseammt = expenseamt.getText().toString();
        expensedt = expensedate.getText().toString();

        expensedate.setText(mDay+"-"+mMonth+"-"+mYear);
        expensedate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar_mode(mYear, mMonth-1, mDay);
            }
        });

        saveExpense= findViewById(R.id.ExpenseSaveButton);






        saveExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expenseammt = expenseamt.getText().toString();
                expensedesc = expensedescription.getText().toString();
                expensedt = expensedate.getText().toString();


                if( TextUtils.isEmpty(expenseamt.getText())){
                    expenseamt.setError( "Enter Amount" );
                }
                if (expenseType.getSelectedItem().toString().equals("Select Option")){
                    Toast.makeText(ExpenseActivity.this, "Please Select Expense Type", Toast.LENGTH_SHORT).show();
                }
                if (Radioexpensemode.getCheckedRadioButtonId() == -1){
                    Toast.makeText(ExpenseActivity.this, "Please Specify Expense Type", Toast.LENGTH_SHORT).show();
                }

                if( TextUtils.isEmpty(expensedescription.getText())){
                    String ss="NONE";
                    expensedescription.setText(ss);
                }

                Log.d("Expenset",""+expenseammt+expensedesc+expensedt+expense_mode+expensetype.toString()+"$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
                if((expenseamt.getText().toString().isEmpty() )|| (expense_mode.isEmpty()) || (expensetype.isEmpty()))
                {
                    Toast.makeText(ExpenseActivity.this, "Enter all details", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    try{
                        Expense expense = new Expense(expenseammt,expensedt,expense_mode,expensedesc,expensetype);
                        SharedPreferences sp1 = getSharedPreferences("Login_Flag", Context.MODE_PRIVATE);
                        String id = sp1.getString("userId","0");

                        FirebaseDatabase fd1 = FirebaseDatabase.getInstance();
                        DatabaseReference dr1 = fd1.getReference("Users");

                        SimpleDateFormat s = new SimpleDateFormat("ddMMyyyyhhmmss");
                        String tstamp = s.format(new Date());

                        dr1.child(id).child("Expenses").child("Expenses_"+tstamp).setValue(expense);

                    }
                    catch(Exception e){
                        SharedPreferences sp2 = getSharedPreferences("Update_Flag", Context.MODE_PRIVATE);
                        String count = String.valueOf(Integer.parseInt(sp2.getString("Expense","0")) + 1);
                        SharedPreferences.Editor editor1 = sp2.edit();
                        editor1.putString("Expense",count);
                    }
                    try {
                    dbExpenseHelper = new ExpenseDataBaseHelper(getApplicationContext());
                    Log.d("Expenset",""+expenseammt+expensedesc+expensedt+expense_mode+expensetype.toString()+"$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
                    Expense expense = new Expense(expenseammt,expensedt,expense_mode,expensedesc,expensetype);
                    Log.d("income",expense.getExpenseAmount());
                    dbExpenseHelper.addExpense(expense);
                    Toast.makeText(ExpenseActivity.this, "Inserted Successfully", Toast.LENGTH_SHORT).show();
                    expensedate.setText("");
                    expensedescription.setText("");
                    Radioexpensemode.clearCheck();


                }
                catch (Exception e)
                {   Log.d("exception",e.getMessage());
                    Toast.makeText(ExpenseActivity.this, "Exception"+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                }
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.RadioCashExpense:
                expense_mode = "Cash";
                Log.d("srcTpe","cash");
                break;
            case R.id.RadioNetBankingExpense:
                expense_mode = "Net Banking";
                break;
            case R.id.RadioCreditCardExpense:
                expense_mode = "Credit Card";
                break;
            case R.id.RadioDebitCardExpense:
                expense_mode = "Debit Card";
                break;
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        expensetype = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), "Selected: " + expensetype, Toast.LENGTH_LONG).show();
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
                        expensedate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(ExpenseActivity.this,MainActivity.class));
    }
}
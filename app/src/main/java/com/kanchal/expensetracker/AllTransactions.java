package com.kanchal.expensetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class AllTransactions extends AppCompatActivity {


    ArrayList<Income> incomeList;
    ArrayList<Expense> expenseList;

    ArrayList<AllTrans> finalList;

    IncomeDataBaseHelper dbIncome;
    ExpenseDataBaseHelper dbExpense;

    ListView transList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_transactions);

        incomeList = new ArrayList<>();
        expenseList = new ArrayList<>();
        finalList = new ArrayList<>();
        transList = findViewById(R.id.trans_list);

        dbIncome = new IncomeDataBaseHelper(getApplicationContext());
        dbExpense = new ExpenseDataBaseHelper(getApplicationContext());


            incomeList = dbIncome.getAllIncome();
            expenseList = dbExpense.getAllExpense();
            try {
                Date date1 = null;

                @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

                for (int i=0; i<incomeList.size(); i++){
                    Income income = incomeList.get(i);
                    date1 = format.parse(income.getDate());

                    for(int j=0; j<expenseList.size(); j++){
                        Expense expense = expenseList.get(j);
                        Date date2 = format.parse(expense.getExpenseDate());
                        int diff = dateDiff(date1,date2);

                        if (diff > 0){
                            String title = expense.getExpenseType();
                            String amt = expense.getExpenseAmount();
                            String type = "debit";

                            AllTrans allTrans1 = new AllTrans(title,amt,type,date2);
                            finalList.add(allTrans1);
                        }
                    }
                    String title = income.getIncomeSource();
                    String amt = income.getAmount();
                    String type = "credit";

                    AllTrans allTrans2 = new AllTrans(title,amt,type,date1);
                    finalList.add(allTrans2);
                }
                
                Date date_new = date1;

                for(int j=0; j<expenseList.size(); j++){
                    Expense expense = expenseList.get(j);
                    Date date2 = format.parse(expense.getExpenseDate());
                    int diff = dateDiff(date2,date_new);

                    if (diff >= 0){
                        String title = expense.getExpenseType();
                        String amt = expense.getExpenseAmount();
                        String type = "debit";

                        AllTrans allTrans1 = new AllTrans(title,amt,type,date2);
                        finalList.add(allTrans1);
                    }
                }

                TransAdapter transAdapter = new TransAdapter(finalList,getApplicationContext());
                transList.setAdapter(transAdapter);

            } catch (ParseException e) {
                e.printStackTrace();
            }

    }

    private int dateDiff(Date date1, Date date2) {
        long difference = Math.abs(date1.getTime() - date2.getTime());
        long differenceDates = difference / (24 * 60 * 60 * 1000);

        return Integer.parseInt(Long.toString(differenceDates));
    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(AllTransactions.this,MainActivity.class));
    }

}
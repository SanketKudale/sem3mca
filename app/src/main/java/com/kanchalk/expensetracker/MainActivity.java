package com.kanchalk.expensetracker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView currentLimit;
    CardView addIncome,addExpenses,category,showAllTransactions,Graphs,addSavings;
    TextView todaytLimit,previous_savings,you_spent;
    static int flag = 0;
    float limit = 0;
    float expense_sum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        you_spent = findViewById(R.id.you_spent);
        todaytLimit = findViewById(R.id.currentLimit);
        previous_savings= findViewById(R.id.previous_savings);
        addIncome = findViewById(R.id.addIncome);
        addExpenses = findViewById(R.id.addExpense);
        category= findViewById(R.id.category);
        showAllTransactions = findViewById(R.id.allTransactions);
        addSavings = findViewById(R.id.addSavings);
        Graphs = findViewById(R.id.Report);

        PieChart pieChart = (PieChart) findViewById(R.id.PieChart);
        float amt[] = { getTotalIncome(),getTotalExpense(),getTotalSavings()};
        String[] type = {"Income","Expenses","Savings"};

        limit = showTodayLimit();
        makePieChart(amt, type, pieChart);

        showSavings();
        showTodayExpense();
        updatedb();

        addIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,IncomeActivity.class));
            }
        });

        addExpenses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ExpenseActivity.class));
            }
        });

        category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Category.class));
            }
        });

        Graphs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Analysis.class));
            }
        });

        showAllTransactions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,AllTransactions.class));
            }
        });
        addSavings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SavingsActivity.class));
            }
        });

    }

    private void makePieChart(float[] amount, String[] type, PieChart pieChart) {
        List<PieEntry> pieEntries = new ArrayList<>();
        for (int i = 0; i < type.length; i++) {
            pieEntries.add(new PieEntry(amount[i], type[i]));
        }

        PieDataSet pieDataSet = new PieDataSet(pieEntries, "My Expenses");
        pieDataSet.setSliceSpace(20f);
        pieDataSet.setSelectionShift(5f);
        PieData pieData = new PieData(pieDataSet);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieData.setValueTextSize(20f);

        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.getDescription().setEnabled(false);
        pieChart.setTransparentCircleRadius(55f);
        pieChart.setData(pieData);
        pieChart.animateY(1000);
        pieChart.invalidate();
    }



    float savings =0;
    @SuppressLint("SetTextI18n")
    public float showTodayLimit() {

        float new_limit = 0;
        float income = getTotalIncome();
        float avl_money = income;
        todaytLimit.setText("Income - "+avl_money);
        todaytLimit.setTextColor(Color.rgb(193, 37, 82));

        /*if(flag==0) {

            todaytLimit.setText("" + avl_money);
            todaytLimit.setTextColor(this.getResources().getColor(R.color.Blue));
            flag=1;
        }
        if(flag!=0) {


            float ex = getTodayExpense();
            if (avl_money >= ex) {
                savings = avl_money - ex;
            } else if (avl_money < ex) {
                int mDay;
                float diff = ex - avl_money;
                final Calendar c = Calendar.getInstance();
                mDay = c.get(Calendar.DAY_OF_MONTH);
                float temp = (diff / (30 - mDay));
                new_limit = avl_money - temp;
                todaytLimit.setText("" + new_limit);
                todaytLimit.setTextColor(this.getResources().getColor(R.color.Blue));
            }

        }*/
        return avl_money;
    }


    public float getTotalIncome()
    {
        IncomeDataBaseHelper in= new IncomeDataBaseHelper(getApplicationContext());

        ArrayList<Income> incomeList = in.getAllIncome();
        float income_sum=0;
        for(int i= 0 ;i<incomeList.size();i++)
        {
            float temp= Float.parseFloat(String.valueOf(incomeList.get(i).getAmount()));
            income_sum = income_sum + temp;
        }
        return income_sum;
    }

    public float getTotalExpense()
    {

        ExpenseDataBaseHelper ex= new ExpenseDataBaseHelper(getApplicationContext());
        ArrayList<Expense> expenseList = ex.getAllExpense();
        float expense_sum=0;
        for(int i= 0 ;i<expenseList.size();i++)
        {
            float temp1= Float.parseFloat(String.valueOf(expenseList.get(i).getExpenseAmount()));
            expense_sum = expense_sum + temp1;
        }

        return expense_sum;
    }

    public void updatedb(){
        SharedPreferences sp1 = getSharedPreferences("Update_Flag", Context.MODE_PRIVATE);
        int expenseCount = Integer.parseInt(sp1.getString("Expense","0"));
        int incomeCount = Integer.parseInt(sp1.getString("Income","0"));
        int savingsCount = Integer.parseInt(sp1.getString("Savings","0"));



        if (expenseCount > 0) {
            ExpenseDataBaseHelper dbExpenseHelper = new ExpenseDataBaseHelper(getApplicationContext());
            ArrayList<Expense> expenseList = new ArrayList<>();
            expenseList = dbExpenseHelper.getAllExpense();

            int count = 0;
            for (int i = expenseList.size() - 1; i >= 0; i--) {
                SharedPreferences sp2 = getSharedPreferences("Login_Flag", Context.MODE_PRIVATE);
                String id = sp2.getString("userId", "0");

                FirebaseDatabase fd1 = FirebaseDatabase.getInstance();
                DatabaseReference dr1 = fd1.getReference("Users");

                @SuppressLint("SimpleDateFormat") SimpleDateFormat s = new SimpleDateFormat("ddMMyyyyhhmmss");
                String tstamp = s.format(new Date());

                Expense expense = expenseList.get(i);
                assert id != null;
                dr1.child(id).child("Expenses").child("Expenses_" + tstamp).setValue(expense);

                count++;
                if (count == expenseCount)
                    break;
            }

            if (incomeCount > 0) {
                IncomeDataBaseHelper dbIncomeHelper = new IncomeDataBaseHelper(getApplicationContext());

                ArrayList<Income> incomeList = dbIncomeHelper.getAllIncome();

                int count1 = 0;
                for (int i = incomeList.size() - 1; i >= 0; i--) {
                    SharedPreferences sp2 = getSharedPreferences("Login_Flag", Context.MODE_PRIVATE);
                    String id = sp2.getString("userId", "0");

                    FirebaseDatabase fd1 = FirebaseDatabase.getInstance();
                    DatabaseReference dr1 = fd1.getReference("Users");

                    @SuppressLint("SimpleDateFormat") SimpleDateFormat s = new SimpleDateFormat("ddMMyyyyhhmmss");
                    String tstamp = s.format(new Date());

                    Income income = incomeList.get(i);
                    assert id != null;
                    dr1.child(id).child("Income").child("Income_" + tstamp).setValue(income);

                    count++;
                    if (count == incomeCount)
                        break;
                }

                if (savingsCount > 0) {
                    SavingsDataBaseHelper dbSavingsHelper = new SavingsDataBaseHelper(getApplicationContext());
                    ArrayList<Savings> savingsList = new ArrayList<>();
                    savingsList = dbSavingsHelper.getAllSavings();

                    int count2 = 0;
                    for (int i = savingsList.size() - 1; i >= 0; i--) {
                        SharedPreferences sp2 = getSharedPreferences("Login_Flag", Context.MODE_PRIVATE);
                        String id = sp2.getString("userId", "0");

                        FirebaseDatabase fd1 = FirebaseDatabase.getInstance();
                        DatabaseReference dr1 = fd1.getReference("Users");

                        SimpleDateFormat s = new SimpleDateFormat("ddMMyyyyhhmmss");
                        String tstamp = s.format(new Date());

                        Savings savings = savingsList.get(i);
                        assert id != null;
                        dr1.child(id).child("Savings").child("Savings_" + tstamp).setValue(savings);

                        count++;
                        if (count == savingsCount)
                            break;
                    }
                }
            }
        }
    }

    public float getTotalSavings() {

        SavingsDataBaseHelper ex = new SavingsDataBaseHelper(getApplicationContext());

        ArrayList<Savings> savingsList = ex.getAllSavings();
        float expense_sum = 0;

        for (int i = 0; i < savingsList.size(); i++) {
            float temp1 = Float.parseFloat(savingsList.get(i).getSavingsAmount());
            expense_sum = expense_sum + temp1;
        }

        return expense_sum;
    }

    @SuppressLint("SetTextI18n")
    private void showTodayExpense() {
        float expense = getTodayExpense();
        you_spent.setText("Expenses - " + expense);
        you_spent.setTextColor(Color.rgb(255, 102, 0));
    }


    @SuppressLint("SetTextI18n")
    private void showSavings() {
        SavingsDataBaseHelper save= new SavingsDataBaseHelper(getApplicationContext());

        ArrayList<Savings> saveList = save.getAllSavings();
        float save_sum=0;
        for(int i= 0 ;i<saveList.size();i++)
        {
            float temp= Float.parseFloat(String.valueOf(saveList.get(i).getSavingsAmount()));
            save_sum = save_sum + temp;
        }
        previous_savings.setText("Savings - " + save_sum);
        previous_savings.setTextColor(Color.rgb(245, 199, 0));

    }



    public float getTodayExpense()
    {
        int mYear, mMonth, mDay;
        ExpenseDataBaseHelper ex = new ExpenseDataBaseHelper(getApplicationContext());
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH) + 1;
        mDay = c.get(Calendar.DAY_OF_MONTH);
        String date = mDay + "-" + mMonth + "-" + mYear;
        ArrayList<Expense> expenseList = new ArrayList<>();
        expenseList = ex.getAllExpense();
        float temp1=0,expense_sum=0;
        for (int i = 0; i < expenseList.size(); i++) {
            if (expenseList.get(i).getExpenseDate().equalsIgnoreCase(date)) {

                temp1 = Float.parseFloat(String.valueOf(expenseList.get(i).getExpenseAmount()));
                expense_sum = expense_sum + temp1;

            }

        }
        return expense_sum;
    }

    public void addSavings() {
        int mYear, mMonth, mDay;
        ExpenseDataBaseHelper ex = new ExpenseDataBaseHelper(getApplicationContext());
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH) + 1;
        mDay = c.get(Calendar.DAY_OF_MONTH)-1;
        String date = mDay + "-" + mMonth + "-" + mYear;
        ArrayList<Expense> expenseList = new ArrayList<>();
        expenseList = ex.getAllExpense();
        float temp1=0;
        for (int i = 0; i < expenseList.size(); i++) {
            if (expenseList.get(i).getExpenseDate().equalsIgnoreCase(date)) {
                Log.d("ExpenseAmount", "---------------------------" + expenseList.get(i).expenseAmount +expenseList.get(i).expenseDate+ "true-------------------------");
                temp1 = Float.parseFloat(String.valueOf(expenseList.get(i).getExpenseAmount()));
                expense_sum = expense_sum + temp1;
                Log.d("expenseSum", "--------------------------------" + expense_sum + "---------------------" + limit);
            }
            else {
                Log.d("ExpenseAmount","---------------------------"+expenseList.get(i).expenseAmount+expenseList.get(i).expenseDate+"-------------------------");
            }

        }
        if(expense_sum <= limit)
        {
            Log.d("Alert",expense_sum+"--"+date+"--"+limit+"No Savings--------------------------------------------------------------------------------------------------------------------------------");

            Toast.makeText(this, "no saving", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Log.d("Alert","Savings---------------------------------------------------------------------------------------------------------------------------------------");
            Toast.makeText(this, "Savings", Toast.LENGTH_SHORT).show();
            Savings savings = new Savings(Float.toString(limit- expense_sum),"None","Spending Less",date);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        finishAffinity();
    }

}
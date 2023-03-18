package com.kanchal.expensetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CategoryReport extends AppCompatActivity {

    String category;
    PieChart pieChartCategory;
    ArrayList<Income> incomeList;
    ArrayList<Expense> expenseList;

    ArrayList<AllTrans> finalList;

    IncomeDataBaseHelper dbIncome;
    ExpenseDataBaseHelper dbExpense;

    ListView transList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_report);
        Intent intent = getIntent();
        category = intent.getStringExtra("categoryName");

        PieChart pieChartCategory = (PieChart) findViewById(R.id.categoryPieChart);
        makePieChart("My" + category, category, pieChartCategory);

        incomeList = new ArrayList<>();
        expenseList = new ArrayList<>();
        finalList = new ArrayList<>();
        transList = findViewById(R.id.trans_list1);

        dbIncome = new IncomeDataBaseHelper(getApplicationContext());
        dbExpense = new ExpenseDataBaseHelper(getApplicationContext());


        incomeList = dbIncome.getAllIncome();
        expenseList = dbExpense.getAllExpense();
        try {
            Date date1 = null;

            @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");


            for (int j = 0; j < expenseList.size(); j++) {
                Expense expense = expenseList.get(j);

                String title = expense.getExpenseType();

                if (title.equals(category)) {
                    Date date = format.parse(expense.getExpenseDate());


                    String amt = expense.getExpenseAmount();
                    String type = "debit";
                    AllTrans allTrans1 = new AllTrans(title, amt, type, date);
                    finalList.add(allTrans1);
                }
            }

            TransAdapter transAdapter = new TransAdapter(finalList,getApplicationContext());
            transList.setAdapter(transAdapter);



        } catch(Exception e) {

        }

    }

    private int dateDiff(Date date1, Date date2) {
        long difference = Math.abs(date1.getTime() - date2.getTime());
        long differenceDates = difference / (24 * 60 * 60 * 1000);

        return Integer.parseInt(Long.toString(differenceDates));
    }

    private void makePieChart(String label,String category,PieChart pieChart ) {
        List<PieEntry> pieEntries = new ArrayList<>();
        float categoryExpense = 0,temp=0;

        ExpenseDataBaseHelper ex= new ExpenseDataBaseHelper(getApplicationContext());
        ArrayList<Expense> expenseList = new ArrayList<>();
        expenseList = ex.getAllExpense();
        float expense_sum=0;
        for(int i= 0 ;i<expenseList.size();i++)
        {   temp = Float.valueOf(String.valueOf(expenseList.get(i).getExpenseAmount()));
            Log.d("expenseType",expenseList.get(i).getExpenseType()+"+++++++++++++++++++++++++++++++++++++++++++");
            if(expenseList.get(i).getExpenseType().equalsIgnoreCase(category)){
            float temp1= Float.valueOf(String.valueOf(expenseList.get(i).getExpenseAmount()));
            categoryExpense = categoryExpense + temp1;
            }
            expense_sum = expense_sum + temp;
        }



            pieEntries.add(new PieEntry(expense_sum,"Total Expenses"));
            pieEntries.add(new PieEntry(categoryExpense,category));

        PieDataSet pieDataSet = new PieDataSet(pieEntries,label);
        pieDataSet.setSliceSpace(3f);
        pieDataSet.setSelectionShift(5f);
        PieData pieData = new PieData(pieDataSet);
        pieDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        pieData.setValueTextSize(12f);

        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.getDescription().setEnabled(false);
        pieChart.setTransparentCircleRadius(61f);
        pieChart.setData(pieData);
        pieChart.animateY(1000);
        pieChart.invalidate();
    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(CategoryReport.this,Category.class));
    }

}
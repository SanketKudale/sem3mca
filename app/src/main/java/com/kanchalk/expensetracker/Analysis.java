package com.kanchalk.expensetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class Analysis extends AppCompatActivity {
    PieChart pieChart1,pieChart2,pieChart3,pieChart4,pieChart5;
    BarData barData1,barData2,barData3,barData4,barData5;
    BarDataSet barDataSet1,barDataSet2,barDataSet3,barDataSet4,barDataSet5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis);

        pieChart1 = findViewById(R.id.PieChart1);
        pieChart2 = findViewById(R.id.PieChart2);
        pieChart3 = findViewById(R.id.PieChart3);
        pieChart4 = findViewById(R.id.PieChart4);
        pieChart5 = findViewById(R.id.PieChart5);
        makePieChart2(pieChart2);
        makePieChart3(pieChart3);
        makePieChart1(pieChart1);
        makePieChart4(pieChart4);
        makePieChart5(pieChart5);
    }



    private void makePieChart1(PieChart pieChart) {
        float food = 0,travel=0 ,education=0,entertainment=0,loans=0,bills=0,others=0, healthcare=0,house=0;
        String type = "Others";

        ArrayList<PieEntry> entries = new ArrayList<>();
        ExpenseDataBaseHelper ex = new ExpenseDataBaseHelper(getApplicationContext());
        ArrayList<Expense> expenseList = new ArrayList<>();
        expenseList = ex.getAllExpense();
        for(int i=0;i<expenseList.size();i++)
        {   type = expenseList.get(i).getExpenseType();
            switch (type){
                case "Food":
                    food = food +  Float.valueOf(expenseList.get(i).getExpenseAmount());
                    break;
                case "Travel":
                    travel = travel +  Float.valueOf(expenseList.get(i).getExpenseAmount());
                    break;
                case "Education":
                    education = education +  Float.valueOf(expenseList.get(i).getExpenseAmount());
                    break;
                case "Entertainment":
                    entertainment = entertainment +  Float.valueOf(expenseList.get(i).getExpenseAmount());
                    break;
                case "Loans":
                    loans = loans +  Float.valueOf(expenseList.get(i).getExpenseAmount());
                    break;
                case "Health Care":
                    healthcare = healthcare +  Float.valueOf(expenseList.get(i).getExpenseAmount());
                    break;
                case "House":
                    house = house+  Float.valueOf(expenseList.get(i).getExpenseAmount());
                    break;
                case "Bills":
                    bills = bills +  Float.valueOf(expenseList.get(i).getExpenseAmount());
                    break;
                case "Others":
                    others = others +  Float.valueOf(expenseList.get(i).getExpenseAmount());
                    break;
            }
        }
       if(food > 0)
        entries.add(new PieEntry(food,"Food"));
       if(travel>0)
        entries.add(new PieEntry(travel,"Travel"));
        if(education>0)
        entries.add(new PieEntry(education,"Education"));
        if(entertainment>0)
        entries.add(new PieEntry(entertainment,"Education"));
        if(loans>0)
        entries.add(new PieEntry(loans,"Loans"));
        if(healthcare>0)
        entries.add(new PieEntry(healthcare,"Health Care"));
        if(house>0)
        entries.add(new PieEntry(house,"House"));
        if(bills>0)
        entries.add(new PieEntry(bills,"Bills"));
        if(others>0)
        entries.add(new PieEntry(others,"Others"));


        PieDataSet pieDataSet = new PieDataSet(entries,"");
        pieDataSet.setSliceSpace(10f);
        pieDataSet.setSelectionShift(10f);
        PieData pieData = new PieData(pieDataSet);
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieData.setValueTextSize(12f);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.getDescription().setEnabled(false);
        pieChart.setTransparentCircleRadius(61f);
        pieChart.setData(pieData);
        pieChart.animateY(1000);
        pieChart.invalidate();
    }

    private void makePieChart2(PieChart pieChart) {
        float jan=0,feb=0,march=0,april=0,may=0,june=0,july=0,aug=0,sept=0,oct=0,nov=0,dec=0;
        String type = "1";

        ArrayList<PieEntry> entries = new ArrayList<>();
        ExpenseDataBaseHelper ex = new ExpenseDataBaseHelper(getApplicationContext());
        ArrayList<Expense> expenseList = new ArrayList<>();
        expenseList = ex.getAllExpense();
        for(int i=0;i<expenseList.size();i++)
        {    String date = expenseList.get(i).getExpenseDate();
             String[] month = date.split("-");
             type = month[1];
            switch (type){
                case "1":
                    jan = jan +  Float.parseFloat(expenseList.get(i).getExpenseAmount());
                    break;
                case "2":
                    feb = feb +  Float.parseFloat(expenseList.get(i).getExpenseAmount());
                    break;
                case "3":
                    march = march +  Float.parseFloat(expenseList.get(i).getExpenseAmount());
                    break;
                case "4":
                    april = april +  Float.parseFloat(expenseList.get(i).getExpenseAmount());
                    break;
                case "5":
                    may = may +  Float.parseFloat(expenseList.get(i).getExpenseAmount());
                    break;
                case "6":
                    june = june +  Float.parseFloat(expenseList.get(i).getExpenseAmount());
                    break;
                case "7":
                    july = july+  Float.parseFloat(expenseList.get(i).getExpenseAmount());
                    break;
                case "8":
                    aug = aug +  Float.parseFloat(expenseList.get(i).getExpenseAmount());
                    break;
                case "9":
                    sept = sept +  Float.parseFloat(expenseList.get(i).getExpenseAmount());
                    break;
                case "10":
                    oct = oct +  Float.parseFloat(expenseList.get(i).getExpenseAmount());
                    break;
                case "11":
                    nov = nov +  Float.parseFloat(expenseList.get(i).getExpenseAmount());
                    break;
                case "12":
                    dec = dec +  Float.parseFloat(expenseList.get(i).getExpenseAmount());
                    break;
            }
        }
if(jan>0)
        entries.add(new PieEntry(jan,"Jan"));
        if(feb>0)
        entries.add(new PieEntry(feb,"Feb"));
        if(march>0)
        entries.add(new PieEntry(march,"March"));
        if(april>0)
        entries.add(new PieEntry(april,"April"));
        if(may>0)
        entries.add(new PieEntry(may,"May"));
        if(june>0)
        entries.add(new PieEntry(june,"June"));
        if(july>0)
        entries.add(new PieEntry(july,"July"));
        if(aug>0)
        entries.add(new PieEntry(aug,"Aug"));
        if(sept>0)
        entries.add(new PieEntry(sept,"Sept"));
        if(oct>0)
        entries.add(new PieEntry(oct,"Oct"));
        if(nov>0)
        entries.add(new PieEntry(nov,"Nov"));
        if(dec>0)
        entries.add(new PieEntry(dec,"Dec"));


        PieDataSet pieDataSet = new PieDataSet(entries,"");
        pieDataSet.setSliceSpace(3f);
        pieDataSet.setSelectionShift(5f);
        PieData pieData = new PieData(pieDataSet);
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieData.setValueTextSize(12f);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.getDescription().setEnabled(false);
        pieChart.setTransparentCircleRadius(61f);
        pieChart.setData(pieData);
        pieChart.animateY(1000);
        pieChart.invalidate();
    }
    private void makePieChart3(PieChart pieChart) {
        float jan=0,feb=0,march=0,april=0,may=0,june=0,july=0,aug=0,sept=0,oct=0,nov=0,dec=0;
        String type = "1";

        ArrayList<PieEntry> entries = new ArrayList<>();
        IncomeDataBaseHelper ex = new IncomeDataBaseHelper(getApplicationContext());
        ArrayList<Income> incomeList = new ArrayList<>();
        incomeList = ex.getAllIncome();
        for(int i=0;i<incomeList.size();i++)
        {    String date = incomeList.get(i).getDate();
            String[] month = date.split("-");
            type = month[1];
            switch (type){
                case "1":
                    jan = jan +  Float.parseFloat(incomeList.get(i).getAmount());
                    break;
                case "2":
                    feb = feb +  Float.parseFloat(incomeList.get(i).getAmount());
                    break;
                case "3":
                    march = march +  Float.parseFloat(incomeList.get(i).getAmount());
                    break;
                case "4":
                    april = april +  Float.parseFloat(incomeList.get(i).getAmount());
                    break;
                case "5":
                    may = may +  Float.parseFloat(incomeList.get(i).getAmount());
                    break;
                case "6":
                    june = june +  Float.parseFloat(incomeList.get(i).getAmount());
                    break;
                case "7":
                    july = july+  Float.parseFloat(incomeList.get(i).getAmount());
                    break;
                case "8":
                    aug = aug +  Float.parseFloat(incomeList.get(i).getAmount());
                    break;
                case "9":
                    sept = sept +  Float.parseFloat(incomeList.get(i).getAmount());
                    break;
                case "10":
                    oct = oct +  Float.parseFloat(incomeList.get(i).getAmount());
                    break;
                case "11":
                    nov = nov +  Float.parseFloat(incomeList.get(i).getAmount());
                    break;
                case "12":
                    dec = dec +  Float.parseFloat(incomeList.get(i).getAmount());
                    break;
            }
        }
        if(jan>0)
            entries.add(new PieEntry(jan,"Jan"));
        if(feb>0)
            entries.add(new PieEntry(feb,"Feb"));
        if(march>0)
            entries.add(new PieEntry(march,"March"));
        if(april>0)
            entries.add(new PieEntry(april,"April"));
        if(may>0)
            entries.add(new PieEntry(may,"May"));
        if(june>0)
            entries.add(new PieEntry(june,"June"));
        if(july>0)
            entries.add(new PieEntry(july,"July"));
        if(aug>0)
            entries.add(new PieEntry(aug,"Aug"));
        if(sept>0)
            entries.add(new PieEntry(sept,"Sept"));
        if(oct>0)
            entries.add(new PieEntry(oct,"Oct"));
        if(nov>0)
            entries.add(new PieEntry(nov,"Nov"));
        if(dec>0)
            entries.add(new PieEntry(dec,"Dec"));


        PieDataSet pieDataSet = new PieDataSet(entries,"");
        pieDataSet.setSliceSpace(3f);
        pieDataSet.setSelectionShift(5f);
        PieData pieData = new PieData(pieDataSet);
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieData.setValueTextSize(12f);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.getDescription().setEnabled(false);
        pieChart.setTransparentCircleRadius(61f);
        pieChart.setData(pieData);
        pieChart.animateY(1000);
        pieChart.invalidate();
    }

    private void makePieChart4(PieChart pieChart) {
        float jan=0,feb=0,march=0,april=0,may=0,june=0,july=0,aug=0,sept=0,oct=0,nov=0,dec=0;
        String type = "1";

        ArrayList<PieEntry> entries = new ArrayList<>();
        SavingsDataBaseHelper ex = new SavingsDataBaseHelper(getApplicationContext());
        ArrayList<Savings> savingsList = new ArrayList<>();
        savingsList = ex.getAllSavings();
        for(int i=0;i<savingsList.size();i++)
        {    String date = savingsList.get(i).getSavingsDate();
            String[] month = date.split("-");
            type = month[1];
            switch (type){
                case "1":
                    jan = jan +  Float.valueOf(savingsList.get(i).getSavingsAmount());
                    break;
                case "2":
                    feb = feb +  Float.valueOf(savingsList.get(i).getSavingsAmount());
                    break;
                case "3":
                    march = march +  Float.valueOf(savingsList.get(i).getSavingsAmount());
                    break;
                case "4":
                    april = april +  Float.valueOf(savingsList.get(i).getSavingsAmount());
                    break;
                case "5":
                    may = may +  Float.valueOf(savingsList.get(i).getSavingsAmount());
                    break;
                case "6":
                    june = june +  Float.valueOf(savingsList.get(i).getSavingsAmount());
                    break;
                case "7":
                    july = july+  Float.valueOf(savingsList.get(i).getSavingsAmount());
                    break;
                case "8":
                    aug = aug +  Float.valueOf(savingsList.get(i).getSavingsAmount());
                    break;
                case "9":
                    sept = sept +  Float.valueOf(savingsList.get(i).getSavingsAmount());
                    break;
                case "10":
                    oct = oct +  Float.valueOf(savingsList.get(i).getSavingsAmount());
                    break;
                case "11":
                    nov = nov +  Float.valueOf(savingsList.get(i).getSavingsAmount());
                    break;
                case "12":
                    dec = dec +  Float.valueOf(savingsList.get(i).getSavingsAmount());
                    break;
            }
        }
        if(jan>0)
            entries.add(new PieEntry(jan,"Jan"));
        if(feb>0)
            entries.add(new PieEntry(feb,"Feb"));
        if(march>0)
            entries.add(new PieEntry(march,"March"));
        if(april>0)
            entries.add(new PieEntry(april,"April"));
        if(may>0)
            entries.add(new PieEntry(may,"May"));
        if(june>0)
            entries.add(new PieEntry(june,"June"));
        if(july>0)
            entries.add(new PieEntry(july,"July"));
        if(aug>0)
            entries.add(new PieEntry(aug,"Aug"));
        if(sept>0)
            entries.add(new PieEntry(sept,"Sept"));
        if(oct>0)
            entries.add(new PieEntry(oct,"Oct"));
        if(nov>0)
            entries.add(new PieEntry(nov,"Nov"));
        if(dec>0)
            entries.add(new PieEntry(dec,"Dec"));


        PieDataSet pieDataSet = new PieDataSet(entries,"");
        pieDataSet.setSliceSpace(10f);
        pieDataSet.setSelectionShift(10f);
        PieData pieData = new PieData(pieDataSet);
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieData.setValueTextSize(12f);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.getDescription().setEnabled(false);
        pieChart.setTransparentCircleRadius(61f);
        pieChart.setData(pieData);
        pieChart.animateY(1000);
        pieChart.invalidate();
    }

    private void makePieChart5(PieChart pieChart) {
        float anniversary = 0,birthday=0 ,education=0,festival=0,Others=0,donations=0;
        String type = "Others";

        ArrayList<PieEntry> entries = new ArrayList<>();
        SavingsDataBaseHelper ex = new SavingsDataBaseHelper(getApplicationContext());
        ArrayList<Savings> expenseList = new ArrayList<>();
        expenseList = ex.getAllSavings();
        for(int i=0;i<expenseList.size();i++)
        {   type = expenseList.get(i).getSavingsFor();
            switch (type){
                case "Anniversary":
                    anniversary = anniversary +  Float.valueOf(expenseList.get(i).getSavingsAmount());
                    break;
                case "Birthday":
                    birthday = birthday +  Float.valueOf(expenseList.get(i).getSavingsAmount());
                    break;
                case "Education Fees":
                    education = education +  Float.valueOf(expenseList.get(i).getSavingsAmount());
                    break;
                case "Festival":
                    festival = festival +  Float.valueOf(expenseList.get(i).getSavingsAmount());
                    break;
                case "Donation":
                    donations = donations +  Float.valueOf(expenseList.get(i).getSavingsAmount());
                    break;
                case "Others":
                    Others = Others +  Float.valueOf(expenseList.get(i).getSavingsAmount());
                    break;
            }
        }
        if(anniversary > 0)
            entries.add(new PieEntry(anniversary,"Anniversary"));
        if(birthday>0)
            entries.add(new PieEntry(birthday,"Birthday"));
        if(education>0)
            entries.add(new PieEntry(education,"Education Fees"));
        if(festival>0)
            entries.add(new PieEntry(festival,"Festivals"));
        if(donations>0)
            entries.add(new PieEntry(donations,"Donations"));
        if(Others>0)
            entries.add(new PieEntry(Others,"Others"));


        PieDataSet pieDataSet = new PieDataSet(entries,"");
        pieDataSet.setSliceSpace(10f);
        pieDataSet.setSelectionShift(10f);
        PieData pieData = new PieData(pieDataSet);
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieData.setValueTextSize(12f);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.getDescription().setEnabled(false);
        pieChart.setTransparentCircleRadius(61f);
        pieChart.setData(pieData);
        pieChart.animateY(1000);
        pieChart.invalidate();
    }

}
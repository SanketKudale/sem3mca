package com.kanchalk.expensetracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Category extends AppCompatActivity {
CardView food,entertainment,bills,house,travel,eductaion,other,loans,healthCare;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        food = findViewById(R.id.Food);
        bills = findViewById(R.id.Bills);
        entertainment = findViewById(R.id.Entertainment);
        other = findViewById(R.id.Others);
        travel = findViewById(R.id.Travelling);
        house = findViewById(R.id.Housing);
        eductaion = findViewById(R.id.Education);
        loans = findViewById(R.id.Loan);
        healthCare = findViewById(R.id.Healthcare);

        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Category.this,CategoryReport.class);
                intent.putExtra("categoryName", "Food");
                startActivity(intent);

            }
        });

        travel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Category.this,CategoryReport.class);
                intent.putExtra("categoryName", "Travelling");
                startActivity(intent);

            }
        });

        house.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Category.this,CategoryReport.class);
                intent.putExtra("categoryName", "Housing");
                startActivity(intent);

            }
        });

        healthCare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Category.this,CategoryReport.class);
                intent.putExtra("categoryName", "Healthcare");
                startActivity(intent);

            }
        });

        eductaion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Category.this,CategoryReport.class);
                intent.putExtra("categoryName", "Education");
                startActivity(intent);

            }
        });

        bills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Category.this,CategoryReport.class);
                intent.putExtra("categoryName", "Bills");
                startActivity(intent);

            }
        });

        entertainment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Category.this,CategoryReport.class);
                intent.putExtra("categoryName", "Entertainment");
                startActivity(intent);

            }
        });

        loans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Category.this,CategoryReport.class);
                intent.putExtra("categoryName", "Loan");
                startActivity(intent);

            }
        });

        other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Category.this,CategoryReport.class);
                intent.putExtra("categoryName", "Others");
                startActivity(intent);

            }
        });

    }
    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(Category.this,MainActivity.class));
    }
}
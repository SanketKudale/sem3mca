package com.kanchalk.expensetracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class IncomeDataBaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MyIncomes.db";
    private static final int DATABASE_VERSION = 4;
    private SQLiteDatabase db;
    public IncomeDataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                IncomeContainer.IncomeTable.TABLE_NAME + " ( " +
                IncomeContainer.IncomeTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                IncomeContainer.IncomeTable.COLUMN_AMOUNT + " TEXT," +
                IncomeContainer.IncomeTable.COLUMN_DATE + " TEXT," +
                IncomeContainer.IncomeTable.COLUMN_FRM + " TEXT," +
                IncomeContainer.IncomeTable.COLUMN_DESCRIPTION + " TEXT," +
                IncomeContainer.IncomeTable.COLUMN_INCOMESOURCE + " TEXT," +
                IncomeContainer.IncomeTable.COLUMN_INCOMETYPE + " TEXT" +
                ")";
        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        Log.d("CreateQuery","Query created successfuly");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + IncomeContainer.IncomeTable.TABLE_NAME);
        onCreate(db);
    }

    public void addIncome(Income income) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(IncomeContainer.IncomeTable.COLUMN_AMOUNT, income.getAmount());
        cv.put(IncomeContainer.IncomeTable.COLUMN_DATE, income.getDate());
        cv.put(IncomeContainer.IncomeTable.COLUMN_FRM, income.getFrom());
        cv.put(IncomeContainer.IncomeTable.COLUMN_DESCRIPTION, income.getDescription());
        cv.put(IncomeContainer.IncomeTable.COLUMN_INCOMESOURCE, income.getIncomeSource());
        cv.put(IncomeContainer.IncomeTable.COLUMN_INCOMETYPE, income.getIncomeType());
        db.insert(IncomeContainer.IncomeTable.TABLE_NAME, null, cv);
    }
    public ArrayList<Income> getAllIncome() {
        ArrayList<Income> incomeList = new ArrayList<>();
        db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + IncomeContainer.IncomeTable.TABLE_NAME, null);
        if (c.moveToFirst()) {
            do {
                Income income = new Income();
                income.setAmount(c.getString(c.getColumnIndex(IncomeContainer.IncomeTable.COLUMN_AMOUNT)));
                income.setDate(c.getString(c.getColumnIndex(IncomeContainer.IncomeTable.COLUMN_DATE)));
                income.setFrom(c.getString(c.getColumnIndex(IncomeContainer.IncomeTable.COLUMN_FRM)));
                income.setDescription(c.getString(c.getColumnIndex(IncomeContainer.IncomeTable.COLUMN_DESCRIPTION)));
                income.setIncomeSource(c.getString(c.getColumnIndex(IncomeContainer.IncomeTable.COLUMN_INCOMESOURCE)));
                income.setIncomeType(c.getString(c.getColumnIndex(IncomeContainer.IncomeTable.COLUMN_INCOMETYPE)));
                incomeList.add(income);
            } while (c.moveToNext());
        }
        c.close();
        return incomeList;
    }


}
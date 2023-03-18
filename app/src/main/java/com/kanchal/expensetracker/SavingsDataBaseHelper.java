package com.kanchal.expensetracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class SavingsDataBaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MySavings.db";
    private static final int DATABASE_VERSION = 4;
    private SQLiteDatabase db;
    public SavingsDataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                SavingsContainer.SavingsTable.TABLE_NAME + " ( " +
                SavingsContainer.SavingsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                SavingsContainer.SavingsTable.COLUMN_AMOUNT + " TEXT," +
                SavingsContainer.SavingsTable.COLUMN_DATE + " TEXT," +
                SavingsContainer.SavingsTable.COLUMN_FOR + " TEXT," +
                SavingsContainer.SavingsTable.COLUMN_DESCRIPTION + " TEXT" +
                ")";
        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        Log.d("CreateQuery","Query created successfuly");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + SavingsContainer.SavingsTable.TABLE_NAME);
        onCreate(db);
    }

    public void addSavings(Savings savings) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(SavingsContainer.SavingsTable.COLUMN_AMOUNT, savings.getSavingsAmount());
        cv.put(SavingsContainer.SavingsTable.COLUMN_DATE, savings.getSavingsDate());
        cv.put(SavingsContainer.SavingsTable.COLUMN_FOR, savings.getSavingsFor());
        cv.put(SavingsContainer.SavingsTable.COLUMN_DESCRIPTION, savings.getSavingsDescription());
        db.insert(SavingsContainer.SavingsTable.TABLE_NAME, null, cv);
    }
    public ArrayList<Savings> getAllSavings() {
        ArrayList<Savings> savingsList = new ArrayList<>();
        db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + SavingsContainer.SavingsTable.TABLE_NAME, null);
        if (c.moveToFirst()) {
            do {
                Savings savings = new Savings();
                savings.setSavingsAmount(c.getString(c.getColumnIndex(SavingsContainer.SavingsTable.COLUMN_AMOUNT)));
                savings.setSavingsDate(c.getString(c.getColumnIndex(SavingsContainer.SavingsTable.COLUMN_DATE)));
                savings.setSavingsFor(c.getString(c.getColumnIndex(SavingsContainer.SavingsTable.COLUMN_FOR)));
                savings.setSavingsDescription(c.getString(c.getColumnIndex(SavingsContainer.SavingsTable.COLUMN_DESCRIPTION)));
                savingsList.add(savings);
            } while (c.moveToNext());
        }
        c.close();
        return savingsList;
    }


}
package com.kanchalk.expensetracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class ExpenseDataBaseHelper  extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MyExpenses.db";
    private static final int DATABASE_VERSION = 4;
    private SQLiteDatabase db;
    public ExpenseDataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                ExpenseContainer.ExpenseTable.TABLE_NAME + " ( " +
                ExpenseContainer.ExpenseTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ExpenseContainer.ExpenseTable.COLUMN_AMOUNT + " TEXT," +
                ExpenseContainer.ExpenseTable.COLUMN_DATE + " TEXT," +
                ExpenseContainer.ExpenseTable.COLUMN_DESCRIPTION + " TEXT," +
                ExpenseContainer.ExpenseTable.COLUMN_EXPENSEMODE + " TEXT," +
                ExpenseContainer.ExpenseTable.COLUMN_EXPENSETYPE + " TEXT" +
                ")";
        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        Log.d("CreateQuery","Query created successfuly");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " +  ExpenseContainer.ExpenseTable.TABLE_NAME);
        onCreate(db);
    }

    public void addExpense(Expense expense) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ExpenseContainer.ExpenseTable.COLUMN_AMOUNT, expense.getExpenseAmount());
        cv.put(ExpenseContainer.ExpenseTable.COLUMN_DATE, expense.getExpenseDate());
        cv.put(ExpenseContainer.ExpenseTable.COLUMN_DESCRIPTION, expense.getDescription());
        cv.put(ExpenseContainer.ExpenseTable.COLUMN_EXPENSEMODE, expense.getExpenseMode());
        cv.put(ExpenseContainer.ExpenseTable.COLUMN_EXPENSETYPE, expense.getExpenseType());
        db.insert(ExpenseContainer.ExpenseTable.TABLE_NAME, null, cv);
    }
    public ArrayList<Expense> getAllExpense() {
        ArrayList<Expense> expenseList = new ArrayList<>();
        db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " +  ExpenseContainer.ExpenseTable.TABLE_NAME, null);
        if (c.moveToFirst()) {
            do {
                Expense expense = new Expense();
                expense.setExpenseAmount(c.getString(c.getColumnIndex( ExpenseContainer.ExpenseTable.COLUMN_AMOUNT)));
                expense.setExpenseDate(c.getString(c.getColumnIndex( ExpenseContainer.ExpenseTable.COLUMN_DATE)));
                expense.setDescription(c.getString(c.getColumnIndex( ExpenseContainer.ExpenseTable.COLUMN_DESCRIPTION)));
                expense.setExpenseMode(c.getString(c.getColumnIndex( ExpenseContainer.ExpenseTable.COLUMN_EXPENSEMODE)));
                expense.setExpenseType(c.getString(c.getColumnIndex( ExpenseContainer.ExpenseTable.COLUMN_EXPENSETYPE)));
                expenseList.add(expense);
            } while (c.moveToNext());
        }
        c.close();
        return expenseList;
    }


}
